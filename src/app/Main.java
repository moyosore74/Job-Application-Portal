package app;

import exception.InvalidJobTypeException;
import exception.InvalidSalaryException;
import exception.JobNotFoundException;
import model.Job;
import model.Role;
import model.User;
import service.ApplicationManager;
import service.JobManager;
import service.UserManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JobManager manager = new JobManager();
        manager.loadJobsFromFile();
        ApplicationManager applicationManager = new ApplicationManager();
        applicationManager.loadApplicationsFromFile();
        UserManager userManager = new UserManager();
        userManager.loadUsersFromFile();

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("============ Job Portal ============");
            System.out.println("Display Menu: ");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("===================================");

            System.out.print("Enter your choice(1-3): ");
            int choice;

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    userManager.registerUser(scanner);
                    break;
                case 2:
                    User loggedInUser = userManager.loginUser(scanner);
                    if (loggedInUser == null) {
                        break;
                    }

                    if (loggedInUser.getRole() == Role.ADMIN) {
                        runAdminMenu(scanner, manager, applicationManager);
                    } else if (loggedInUser.getRole() == Role.EMPLOYER) {
                        runEmployerMenu(scanner, manager, applicationManager, loggedInUser);
                    } else if (loggedInUser.getRole() == Role.APPLICANT) {
                        runApplicantMenu(scanner, manager, applicationManager, loggedInUser);
                    }
                    break;
                case 3:
                    System.out.println("Thank you for using Job Portal!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 3.");
                    break;
            }
        }
        scanner.close();
    }

    private static void runAdminMenu(Scanner scanner, JobManager manager, ApplicationManager applicationManager) {
        boolean adminRunning = true;

        while (adminRunning) {
            System.out.println("1. Add Job");
            System.out.println("2. Remove Job");
            System.out.println("3. Display All Jobs");
            System.out.println("4. Search Job");
            System.out.println("5. Display all Applications");
            System.out.println("6. Update Application Status");
            System.out.println("7. Logout");

            System.out.print("Enter your choice(1-7): ");
            int adminChoice;

            try {
                adminChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (adminChoice) {
                case 1:
                    addJobFlow(scanner, manager, null, null);
                    continue;
                case 2:
                    System.out.print("Enter job title or ID to remove: ");
                    String jobTitleToRemove = scanner.nextLine();

                    try {
                        manager.removeJob(jobTitleToRemove);
                    } catch (JobNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    continue;
                case 3:
                    System.out.println("All Jobs available: ");
                    manager.displayAllJobs();
                    continue;
                case 4:
                    searchJobFlow(scanner, manager);
                    continue;
                case 5:
                    System.out.println("All applications");
                    applicationManager.listApplications(manager);
                    continue;
                case 6:
                    updateApplicationStatusFlow(scanner, applicationManager);
                    continue;
                case 7:
                    System.out.println("Log out");
                    adminRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 7.");
            }
        }
    }

    private static void runEmployerMenu(
            Scanner scanner,
            JobManager manager,
            ApplicationManager applicationManager,
            User loggedInUser
    ) {
        boolean employerRunning = true;

        while (employerRunning) {
            System.out.println("1. Add Job");
            System.out.println("2. Remove Job");
            System.out.println("3. Display Your Company's Jobs");
            System.out.println("4. Search Job");
            System.out.println("5. View Applications To Your Jobs");
            System.out.println("6. Update Application Status");
            System.out.println("7. Logout");

            System.out.print("Enter your choice(1-7): ");
            int employerChoice;

            try {
                employerChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (employerChoice) {
                case 1:
                    addJobFlow(scanner, manager, loggedInUser.getCompanyId(), loggedInUser.getFullName());
                    continue;
                case 2:
                    System.out.print("Enter job title or ID to remove: ");
                    String jobTitleToRemove = scanner.nextLine();

                    try {
                        manager.removeJobForCompany(jobTitleToRemove, loggedInUser.getCompanyId());
                    } catch (JobNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    continue;
                case 3:
                    manager.displayJobsForCompany(loggedInUser.getCompanyId());
                    continue;
                case 4:
                    searchEmployerJobFlow(scanner, manager, loggedInUser.getCompanyId());
                    continue;
                case 5:
                    applicationManager.listApplicationsForCompany(loggedInUser.getCompanyId(), manager);
                    continue;
                case 6:
                    updateApplicationStatusFlow(scanner, applicationManager, manager, loggedInUser.getCompanyId());
                    continue;
                case 7:
                    System.out.println("Log out");
                    employerRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 7.");
            }
        }
    }

    private static void runApplicantMenu(
            Scanner scanner,
            JobManager manager,
            ApplicationManager applicationManager,
            User loggedInUser
    ) {
        boolean applicantRunning = true;

        while (applicantRunning) {
            System.out.println("1. Display All Jobs");
            System.out.println("2. Search Job");
            System.out.println("3. Apply for Job");
            System.out.println("4. Your Applications");
            System.out.println("5. Log out");

            System.out.print("Enter your choice(1-5): ");
            int userChoice;

            try {
                userChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (userChoice) {
                case 1:
                    System.out.println("All Jobs available across all employers: ");
                    manager.displayAllJobs();
                    continue;
                case 2:
                    searchJobFlow(scanner, manager);
                    continue;
                case 3:
                    System.out.println("Enter job ID to apply: ");
                    String jobId = scanner.nextLine();

                    System.out.println("Enter your phone number: ");
                    String phoneNumber = scanner.nextLine();

                    try {
                        Job job = manager.findJob(jobId);

                        System.out.println("You are applying for:");
                        job.displayDetails();

                        applicationManager.submitApplications(
                                loggedInUser.getFullName(),
                                loggedInUser.getEmail(),
                                jobId,
                                phoneNumber
                        );
                    } catch (JobNotFoundException e) {
                        System.out.println("Job not found. Application can not be submitted");
                    }
                    continue;
                case 4:
                    applicationManager.listApplicationsByEmail(loggedInUser.getEmail(), manager);
                    continue;
                case 5:
                    System.out.println("Log out");
                    applicantRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }
    }

    private static void addJobFlow(
            Scanner scanner,
            JobManager manager,
            String defaultCompanyId,
            String defaultCompanyName
    ) {
        String jobType;
        double salary;

        try {
            System.out.print("Enter job type(Remote/Onsite): ");
            jobType = scanner.nextLine();

            if (!jobType.equalsIgnoreCase("Remote") && !jobType.equalsIgnoreCase("Onsite")) {
                throw new InvalidJobTypeException("Invalid job input. Please input either Remote or Onsite");
            }
        } catch (InvalidJobTypeException e) {
            System.out.println(e.getMessage());
            return;
        }

        String companyId = defaultCompanyId;
        if (companyId == null || companyId.isBlank()) {
            System.out.print("Enter company ID: ");
            companyId = scanner.nextLine();
        } else {
            System.out.println("Company ID: " + companyId);
        }

        String companyName = defaultCompanyName;
        if (companyName == null || companyName.isBlank()) {
            System.out.print("Enter name of Company/Organization: ");
            companyName = scanner.nextLine();
        } else {
            System.out.println("Company/Organization: " + companyName);
        }

        System.out.print("Enter Job Title: ");
        String jobTitle = scanner.nextLine();

        System.out.print("Enter job description: ");
        String description = scanner.nextLine();

        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        System.out.print("Enter required skills: ");
        String requiredSkills = scanner.nextLine();

        System.out.print("Enter experience level: ");
        String experienceLevel = scanner.nextLine();

        try {
            System.out.print("Enter Salary($): ");
            salary = scanner.nextDouble();

            if (salary <= 0) {
                throw new InvalidSalaryException("Salary cannot be less than 0");
            }
            scanner.nextLine();
        } catch (InvalidSalaryException | InputMismatchException e) {
            System.out.println(e.getMessage());
            scanner.nextLine();
            return;
        }

        if (jobType.equalsIgnoreCase("Remote")) {
            System.out.print("Requires internet(true/false): ");
            boolean requiresInternet = scanner.nextBoolean();
            scanner.nextLine();

            System.out.print("Work Hours: ");
            String workHours = scanner.nextLine();

            manager.addRemoteJob(
                    companyId,
                    companyName,
                    jobTitle,
                    location,
                    jobType,
                    salary,
                    description,
                    requiredSkills,
                    experienceLevel,
                    requiresInternet,
                    workHours
            );
        } else {
            System.out.print("Provides Housing(true/false): ");
            boolean providesHousing = scanner.nextBoolean();
            scanner.nextLine();

            System.out.print("Office Address: ");
            String officeAddress = scanner.nextLine();

            manager.addOnsiteJob(
                    companyId,
                    companyName,
                    jobTitle,
                    location,
                    jobType,
                    salary,
                    description,
                    requiredSkills,
                    experienceLevel,
                    officeAddress,
                    providesHousing
            );
        }
    }

    private static void searchJobFlow(Scanner scanner, JobManager manager) {
        System.out.print("Enter job title or Id to search: ");
        String searchValue = scanner.nextLine();

        try {
            Job found = manager.findJob(searchValue);
            found.displayDetails();
        } catch (JobNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchEmployerJobFlow(Scanner scanner, JobManager manager, String companyId) {
        System.out.print("Enter your company's job title or Id to search: ");
        String searchValue = scanner.nextLine();

        try {
            Job found = manager.findJobForCompany(searchValue, companyId);
            found.displayDetails();
        } catch (JobNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateApplicationStatusFlow(
            Scanner scanner,
            ApplicationManager applicationManager
    ) {
        System.out.println("Valid statuses: Pending, Reviewed, Accepted, Rejected");
        System.out.print("Enter application ID: ");
        String applicationId = scanner.nextLine();

        System.out.print("Enter new status: ");
        String newStatus = scanner.nextLine();

        applicationManager.updateApplicationStatus(applicationId, newStatus);
    }

    private static void updateApplicationStatusFlow(
            Scanner scanner,
            ApplicationManager applicationManager,
            JobManager manager,
            String companyId
    ) {
        System.out.println("Valid statuses: Pending, Reviewed, Accepted, Rejected");
        System.out.print("Enter application ID: ");
        String applicationId = scanner.nextLine();

        System.out.print("Enter new status: ");
        String newStatus = scanner.nextLine();

        applicationManager.updateApplicationStatusForCompany(applicationId, companyId, newStatus, manager);
    }
}
