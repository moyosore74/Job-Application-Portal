package storage;

import model.Role;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileHandler {
    private static final String FILE_NAME = "users.txt";

    public void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 6) {
                    String userId = parts[0];
                    String fullName = parts[1];
                    String email = parts[2];
                    String password = parts[3];
                    Role role = Role.valueOf(parts[4]);
                    String companyId = parts[5];

                    users.add(new User(userId, fullName, email, password, role, companyId));
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        return users;
    }
}
