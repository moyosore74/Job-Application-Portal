package storage;

import model.Job;
import model.Onsite;
import model.Remote;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JobFileHandler {
    private static final String filePath = "jobs.txt";

    public void saveJobs(List<Job> jobs) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(Job job :jobs)
            {
                if (job instanceof Remote remoteJob) {
                    writer.write(
                            remoteJob.getJobId() + "," +
                                    remoteJob.getCompanyId() + "," +
                                    remoteJob.getCompanyName() + "," +
                                    remoteJob.getJobTitle() + "," +
                                    remoteJob.getLocation() + "," +
                                    remoteJob.getJobType() + "," +
                                    remoteJob.getSalary() + "," +
                                    remoteJob.isRequiresInternet() + "," +
                            remoteJob.getWorkHours() + "," +
                            remoteJob.getDescription() + "," +
                            remoteJob.getRequiredSkills() + "," +
                            remoteJob.getExperienceLevel() + "," +
                            remoteJob.getUploadedDate()
                    );
                    writer.newLine();

                } else if (job instanceof Onsite onsiteJob) {
                    writer.write(
                            onsiteJob.getJobId() + "," +
                                    onsiteJob.getCompanyId() + "," +
                                    onsiteJob.getCompanyName() + "," +
                                    onsiteJob.getJobTitle() + "," +
                                    onsiteJob.getLocation() + "," +
                                    onsiteJob.getJobType() + "," +
                                    onsiteJob.getSalary() + "," +
                                    onsiteJob.getOfficeAddress() + "," +
                                    onsiteJob.isProvidesHousing() + "," +
                                    onsiteJob.getDescription() + "," +
                                    onsiteJob.getRequiredSkills() + "," +
                                    onsiteJob.getExperienceLevel() + "," +
                                    onsiteJob.getUploadedDate()
                    );
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error writing to file");
        }

    }

    public List<Job> loadJobs() {
        List<Job> jobs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 9) {
                    continue;
                }

                String jobId = parts[0];
                String companyId = parts[1];
                String companyName = parts[2];
                String jobTitle = parts[3];
                String location = parts[4];
                String jobType = parts[5];
                double salary = Double.parseDouble(parts[6]);

                Job job;

                if (jobType.equalsIgnoreCase("Remote")) {
                    boolean requiresInternet = Boolean.parseBoolean(parts[7]);
                    String workHours = parts[8];
                    job = new Remote(
                            jobId,
                            companyId,
                            companyName,
                            jobTitle,
                            location,
                            jobType,
                            salary,
                            requiresInternet,
                            workHours
                    );
                } else {
                    String officeAddress = parts[7];
                    boolean providesHousing = Boolean.parseBoolean(parts[8]);
                    job = new Onsite(
                            jobId,
                            companyId,
                            companyName,
                            jobTitle,
                            location,
                            jobType,
                            salary,
                            officeAddress,
                            providesHousing
                    );
                }

                if (parts.length >= 12) {
                    job.setDescription(parts[9]);
                    job.setRequiredSkills(parts[10]);
                    job.setExperienceLevel(parts[11]);
                }
                if (parts.length >= 13) {
                    job.setUploadedDate(parts[12]);
                }
                jobs.add(job);
            }
        } catch (FileNotFoundException e) {
            return jobs;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Something went wrong");
        }

        return jobs;
    }

}

