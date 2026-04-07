package com.jobproj.jobportal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JobPortalApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void applicantRegistrationAndLoginWorks() throws Exception {
        String email = uniqueEmail("applicant");

        ResponseEntity<String> registerResponse = post("/api/auth/register", Map.of(
                "fullName", "Applicant User",
                "email", email,
                "password", "Password123!",
                "role", "APPLICANT"
        ));

        assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
        JsonNode registeredUser = objectMapper.readTree(registerResponse.getBody());
        assertEquals(email, registeredUser.get("email").asText());
        assertEquals("APPLICANT", registeredUser.get("role").asText());

        ResponseEntity<String> loginResponse = post("/api/auth/login", Map.of(
                "email", email,
                "password", "Password123!"
        ));

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        JsonNode authResponse = objectMapper.readTree(loginResponse.getBody());
        assertTrue(authResponse.get("token").asText().startsWith("eyJ"));
        assertEquals(email, authResponse.get("user").get("email").asText());
    }

    @Test
    void duplicateRegistrationReturnsStructuredConflict() throws Exception {
        String email = uniqueEmail("duplicate");
        Map<String, Object> payload = Map.of(
                "fullName", "Duplicate User",
                "email", email,
                "password", "Password123!",
                "role", "ADMIN"
        );

        assertEquals(HttpStatus.CREATED, post("/api/auth/register", payload).getStatusCode());

        ResponseEntity<String> duplicateResponse = post("/api/auth/register", payload);

        assertEquals(HttpStatus.CONFLICT, duplicateResponse.getStatusCode());
        JsonNode error = objectMapper.readTree(duplicateResponse.getBody());
        assertEquals("Conflict", error.get("error").asText());
        assertEquals("Email already exists", error.get("message").asText());
        assertEquals("/api/auth/register", error.get("path").asText());
    }

    @Test
    void employerCreateJobAndApplicantApplyFlowWorks() throws Exception {
        String employerEmail = uniqueEmail("employer");
        String companyId = "COMP" + System.nanoTime();

        assertEquals(HttpStatus.CREATED, post("/api/auth/register", Map.of(
                "fullName", "Employer User",
                "email", employerEmail,
                "password", "Password123!",
                "role", "EMPLOYER",
                "companyId", companyId,
                "companyName", "Deploy Ready Co"
        )).getStatusCode());

        String employerToken = loginAndGetToken(employerEmail);

        Map<String, Object> createJobPayload = new LinkedHashMap<>();
        createJobPayload.put("jobTitle", "Backend Engineer");
        createJobPayload.put("description", "Build APIs");
        createJobPayload.put("location", "Lagos");
        createJobPayload.put("jobType", "Remote");
        createJobPayload.put("requiredSkills", "Java,Spring Boot");
        createJobPayload.put("experienceLevel", "Mid Level");
        createJobPayload.put("salary", 450000);
        createJobPayload.put("requiresInternet", true);
        createJobPayload.put("workHours", "9am-5pm");
        createJobPayload.put("officeAddress", "12 Marina Road");
        createJobPayload.put("providesHousing", false);

        ResponseEntity<String> createJobResponse = postAuthorized("/api/jobs", createJobPayload, employerToken);

        assertEquals(HttpStatus.CREATED, createJobResponse.getStatusCode());
        String jobId = objectMapper.readTree(createJobResponse.getBody()).get("jobId").asText();
        assertTrue(jobId.startsWith("JOB"));

        String applicantEmail = uniqueEmail("applicant-flow");
        assertEquals(HttpStatus.CREATED, post("/api/auth/register", Map.of(
                "fullName", "Applicant Flow",
                "email", applicantEmail,
                "password", "Password123!",
                "role", "APPLICANT"
        )).getStatusCode());

        String applicantToken = loginAndGetToken(applicantEmail);

        ResponseEntity<String> applyResponse = postAuthorized("/api/applications", Map.of(
                "jobId", jobId,
                "phoneNumber", "+2348012345678"
        ), applicantToken);

        assertEquals(HttpStatus.CREATED, applyResponse.getStatusCode());
        JsonNode application = objectMapper.readTree(applyResponse.getBody());
        assertEquals(jobId, application.get("jobId").asText());

        ResponseEntity<String> duplicateApplyResponse = postAuthorized("/api/applications", Map.of(
                "jobId", jobId,
                "phoneNumber", "+2348012345678"
        ), applicantToken);

        assertEquals(HttpStatus.CONFLICT, duplicateApplyResponse.getStatusCode());
    }

    @Test
    void protectedEndpointReturnsJsonUnauthorizedResponse() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(url("/api/applications"), String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        JsonNode error = objectMapper.readTree(response.getBody());
        assertEquals("Authentication required", error.get("message").asText());
        assertEquals("/api/applications", error.get("path").asText());
    }

    private String loginAndGetToken(String email) throws Exception {
        ResponseEntity<String> loginResponse = post("/api/auth/login", Map.of(
                "email", email,
                "password", "Password123!"
        ));
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        return objectMapper.readTree(loginResponse.getBody()).get("token").asText();
    }

    private ResponseEntity<String> post(String path, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.postForEntity(url(path), new HttpEntity<>(body, headers), String.class);
    }

    private ResponseEntity<String> postAuthorized(String path, Map<String, Object> body, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(new LinkedHashMap<>(body), headers);
        return restTemplate.exchange(url(path), HttpMethod.POST, entity, String.class);
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    private String uniqueEmail(String prefix) {
        return prefix + "." + System.nanoTime() + "@example.com";
    }
}
