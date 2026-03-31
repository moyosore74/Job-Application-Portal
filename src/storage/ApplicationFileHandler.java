package storage;

import model.Application;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationFileHandler {

    private static final String filePath = "applications.txt";

    public void saveApplications(List<Application> applications){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(Application application : applications)
            {
                writer.write(application.getApplicationId() + ","+
                        application.getApplicantName() + ","+
                        application.getApplicantEmail() + ","+
                        application.getJobId() + "," +
                        application.getPhoneNumber() + "," +
                        application.getApplicationDate()+","+
                        application.getStatus());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving applications:" + e.getMessage());
        }
    }
    public List<Application> loadApplications(){
        List<Application> applications = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine())!= null){
                String[] parts = line.split(",");

                String id = parts[0];
                String applicantName = parts[1];
                String applicantEmail = parts[2];
                String jobId = parts[3];
                String phoneNumber = parts[4];
                String applicationDate = parts[5];
                String status = parts[6];


                Application application = new Application(
                        id,
                        applicantName,
                        applicantEmail,
                        jobId,
                        phoneNumber,
                        applicationDate,
                        status
                );

                applications.add(application);
            }
        }
        catch(FileNotFoundException e){
            return applications;
        }
        catch(IOException e){
            System.out.println("Something went wrong: "+ e.getMessage());
        }
        return applications;
    }
}
