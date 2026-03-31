package service;

import model.Application;
import storage.ApplicationFileHandler;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class ApplicationManager {
     private final ArrayList<Application> applications;
     private final ApplicationFileHandler fileHandler = new ApplicationFileHandler();

     public ApplicationManager() {
          applications = new ArrayList<>(fileHandler.loadApplications());
          updateApplicationCounter();
     }

     private int applicationCounter = 1;

     private String generateApplicationId() {
          String id = String.format("APP%03d", applicationCounter);
          applicationCounter++;
          return id;
     }

     private void updateApplicationCounter() {

          int maxId = 0;

          for (Application application : applications) {

               String id = application.getApplicationId().replace("APP", "");
               int number = Integer.parseInt(id);

               if (number > maxId) {
                    maxId = number;
               }
          }

          applicationCounter = maxId + 1;
     }

     private String generateApplicationDate(){
          return LocalDate.now().toString();
     }

     public void submitApplications(String applicantName,
                                    String applicantEmail,
                                    String jobId,
                                    String phoneNumber) {

          for (Application application : applications) {

               if (application.getApplicantEmail().equalsIgnoreCase(applicantEmail)
                       && application.getJobId().equalsIgnoreCase(jobId)) {

                    System.out.println("You have already applied for this job.");
                    return;
               }
          }

          String id = generateApplicationId();
          String applicationDate = generateApplicationDate();

          Application newApplication = new Application(
                  id,
                  applicantName,
                  applicantEmail,
                  jobId,
                  phoneNumber,
                  applicationDate,
                  "Pending"
          );

          applications.add(newApplication);
          saveApplications();
          System.out.println("Application submitted successfully");
     }


     public void saveApplications() {
          fileHandler.saveApplications(applications);
     }

     public void listApplications(JobManager jobManager) {
          if (applications.isEmpty()) {
               System.out.println("No applications at the moment");
          } else {
               System.out.println("**********All Applications********");
               for (Application application : getApplicationsSortedByNewest()) {
                    application.displayDetails(jobManager.getJobTitleById(application.getJobId()));
               }
               System.out.println("**********************************");

          }
     }

     public void loadApplicationsFromFile(){
          applications.clear();
          List<Application> loadedApplications = fileHandler.loadApplications();
          applications.addAll(loadedApplications);
          updateApplicationCounter();
     }

     public void listApplicationsByEmail(String applicantEmail, JobManager jobManager) {
          boolean found = false;

          System.out.println("********** Your Applications ********");

          for (Application application : getApplicationsSortedByNewest()) {
               if (application.getApplicantEmail().equalsIgnoreCase(applicantEmail)) {
                    application.displayDetails(jobManager.getJobTitleById(application.getJobId()));
                    found = true;
               }
          }

          if (!found) {
               System.out.println("You have no applications at the moment.");
          }

          System.out.println("*************************************");
     }

     public void listApplicationsForCompany(String companyId, JobManager jobManager) {
          boolean found = false;

          System.out.println("***** Applications To Your Company's Jobs *****");

          for (Application application : getApplicationsSortedByNewest()) {
               if (jobManager.jobBelongsToCompany(application.getJobId(), companyId)) {
                    application.displayDetails(jobManager.getJobTitleById(application.getJobId()));
                    found = true;
               }
          }

          if (!found) {
               System.out.println("No applications found for your company's jobs.");
          }

          System.out.println("***********************************************");
     }

     public void updateApplicationStatusForCompany(
             String applicationId,
             String companyId,
             String newStatus,
             JobManager jobManager
     ) {
          if (!isValidStatus(newStatus)) {
               System.out.println("Invalid status. Use Pending, Reviewed, Accepted, or Rejected.");
               return;
          }

          for (Application application : applications) {
               if (application.getApplicationId().equalsIgnoreCase(applicationId) &&
                       jobManager.jobBelongsToCompany(application.getJobId(), companyId)) {
                    application.setStatus(capitalizeStatus(newStatus));
                    saveApplications();
                    System.out.println("Application status updated successfully.");
                    return;
               }
          }

          System.out.println("Application not found for your company's jobs.");
     }

     public void updateApplicationStatus(String applicationId, String newStatus) {
          if (!isValidStatus(newStatus)) {
               System.out.println("Invalid status. Use Pending, Reviewed, Accepted, or Rejected.");
               return;
          }

          for (Application application : applications) {
               if (application.getApplicationId().equalsIgnoreCase(applicationId)) {
                    application.setStatus(capitalizeStatus(newStatus));
                    saveApplications();
                    System.out.println("Application status updated successfully.");
                    return;
               }
          }

          System.out.println("Application not found.");
     }

     private boolean isValidStatus(String status) {
          return status.equalsIgnoreCase("Pending") ||
                  status.equalsIgnoreCase("Reviewed") ||
                  status.equalsIgnoreCase("Accepted") ||
                  status.equalsIgnoreCase("Rejected");
     }

     private String capitalizeStatus(String status) {
          String lower = status.toLowerCase();
          return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
     }

     private List<Application> getApplicationsSortedByNewest() {
          List<Application> sortedApplications = new ArrayList<>(applications);
          sortedApplications.sort(Comparator.comparing(this::parseApplicationDate).reversed());
          return sortedApplications;
     }

     private LocalDate parseApplicationDate(Application application) {
          String applicationDate = application.getApplicationDate();
          if (applicationDate == null || applicationDate.isBlank()) {
               return LocalDate.MIN;
          }

          try {
               return LocalDate.parse(applicationDate);
          } catch (Exception e) {
               return LocalDate.MIN;
          }
     }

}
