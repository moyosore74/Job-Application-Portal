package service;

import model.Role;
import model.User;
import storage.UserFileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private final ArrayList<User> users;
    private final UserFileHandler fileHandler = new UserFileHandler();
    private final Scanner scanner = new Scanner(System.in);

    private int userCounter = 1;

    public UserManager() {
        users = new ArrayList<>(fileHandler.loadUsers());
        updateUserCounter();
    }

    private String generateUserId() {
        String id = String.format("USER%03d", userCounter);
        userCounter++;
        return id;
    }

    private void updateUserCounter() {
        int maxId = 0;

        for (User user : users) {
            String id = user.getUserId().replace("USER", "");
            int number = Integer.parseInt(id);

            if (number > maxId) {
                maxId = number;
            }
        }

        userCounter = maxId + 1;
    }

    public void loadUsersFromFile() {
        users.clear();
        List<User> loadedUsers = fileHandler.loadUsers();
        users.addAll(loadedUsers);
        updateUserCounter();
    }

    public boolean emailExists(String email){
        for (User user : users){
            if(user.getEmail().equalsIgnoreCase(email.trim())) {
                return true;
            }
        }

        return false;
    }

    public void registerUser() {
        registerUser(scanner);
    }

    public void registerUser(Scanner scanner) {
        System.out.println("\n=== Register User ===");

        String userId = generateUserId();

        String fullName;
        while (true) {
            System.out.print("Enter full name: ");
            fullName = scanner.nextLine().trim();

            if (fullName.isEmpty()) {
                System.out.println("Name cannot be empty");
            } else {
                break;
            }
        }

        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine().trim();

            if (email.isEmpty()){
                System.out.println("Email cannot be empty");
            } else if (emailExists(email)) {
                System.out.println("Email already exists. Try another email.");
            } else {
                break;
            }
        }

        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();

            if (password.isEmpty()) {
                System.out.println("Password cannot be empty");
            }else {
                break;
            }
        }

        Role role = null;

        while (true) {
            System.out.println("Select role:");
            System.out.println("1. Admin");
            System.out.println("2. Employer");
            System.out.println("3. Applicant");
            System.out.print("Enter role number: ");

            if (scanner.hasNextInt()) {
                int roleNum = scanner.nextInt();
                scanner.nextLine();

                switch (roleNum) {
                    case 1:
                        role = Role.ADMIN;
                        break;
                    case 2:
                        role = Role.EMPLOYER;
                        break;
                    case 3:
                        role = Role.APPLICANT;
                        break;
                    default:
                        System.out.println("Invalid choice. Enter 1, 2, or 3.");
                        continue;
                }
                break;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }

        String companyId = "";
        if (role == Role.EMPLOYER) {
            while (true) {
                System.out.print("Enter company ID: ");
                companyId = scanner.nextLine().trim();

                if (companyId.isEmpty()) {
                    System.out.println("Company Id cannot be empty");
                } else {
                    break;
                }
            }
        }

        User user = new User(userId, fullName, email, password, role, companyId);
        users.add(user);
        fileHandler.saveUsers(users);

        System.out.println("User registered successfully.");
        System.out.println("Generated User ID: " + userId);
    }

    public User loginUser() {
        return loginUser(scanner);
    }

    public User loginUser(Scanner scanner) {
        System.out.println("\n=== User Login ===");

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) &&
                    user.getPassword().equals(password)) {
                System.out.println("Login successful. Welcome, " + user.getFullName());
                return user;
            }
        }

        System.out.println("Invalid email or password.");
        return null;
    }

    public void displayAllUsers() {
        System.out.println("\n=== All Users ===");

        if (users.isEmpty()) {
            System.out.println("No users available.");
            return;
        }

        for (User user : users) {
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Name: " + user.getFullName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Role: " + user.getRole());
            System.out.println("----------------------------");
        }
    }

    public User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equalsIgnoreCase(userId)) {
                return user;
            }
        }
        return null;
    }

    public void deleteUser(String userId) {
        User user = findUserById(userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        users.remove(user);
        fileHandler.saveUsers(users);
        System.out.println("User deleted successfully.");
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
