package com.jobproj.jobportal.service;

import com.jobproj.jobportal.dto.AuthResponse;
import com.jobproj.jobportal.dto.LoginRequest;
import com.jobproj.jobportal.dto.RegisterUserRequest;
import com.jobproj.jobportal.dto.UserResponse;
import com.jobproj.jobportal.entity.Company;
import com.jobproj.jobportal.entity.Role;
import com.jobproj.jobportal.entity.User;
import com.jobproj.jobportal.repository.CompanyRepository;
import com.jobproj.jobportal.repository.UserRepository;
import com.jobproj.jobportal.security.JwtService;
import com.jobproj.jobportal.security.UserPrincipal;
import com.jobproj.jobportal.util.IdGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(
            UserRepository userRepository,
            CompanyRepository companyRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserResponse register(RegisterUserRequest request) {
        String normalizedEmail = request.getEmail().trim();

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        User user = new User();
        user.setUserId(IdGenerator.next("USER"));
        user.setFullName(request.getFullName());
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        if (request.getRole() == Role.EMPLOYER) {
            if (isBlank(request.getCompanyId()) || isBlank(request.getCompanyName())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Employer registration requires companyId and companyName"
                );
            }

            String companyId = request.getCompanyId().trim();
            String companyName = request.getCompanyName().trim();

            Company company = companyRepository.findById(companyId)
                    .orElseGet(() -> companyRepository.save(
                            new Company(companyId, companyName)
                    ));
            user.setCompany(company);
        }

        return toResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.getEmail().trim();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizedEmail, request.getPassword())
        );

        User user = userRepository.findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        AuthResponse response = new AuthResponse();
        response.setToken(jwtService.generateToken(new UserPrincipal(user)));
        response.setUser(toResponse(user));
        return response;
    }

    @Transactional(readOnly = true)
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        if (user.getCompany() != null) {
            response.setCompanyId(user.getCompany().getCompanyId());
            response.setCompanyName(user.getCompany().getCompanyName());
        }
        return response;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
