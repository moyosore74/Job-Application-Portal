package service;

import exception.JobNotFoundException;
import model.Job;
import model.Onsite;
import model.Remote;
import storage.JobFileHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JobManager {
    private final ArrayList<Job> jobs = new ArrayList<>();
    private final JobFileHandler fileHandler = new JobFileHandler();

    private int jobCounter = 1;

    public JobManager() {
        loadJobsFromFile();
        updateJobCounter();
    }

    private String generateJobId() {
        String jobId = String.format("JOB%03d", jobCounter);
        jobCounter++;
        return jobId;
    }

    private void updateJobCounter() {
        int maxId = 0;

        for (Job job : jobs) {
            String jobId = job.getJobId();
            if (jobId == null || !jobId.startsWith("JOB")) {
                continue;
            }

            String id = jobId.replace("JOB", "");
            int number;

            try {
                number = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                continue;
            }

            if (number > maxId) {
                maxId = number;
            }
        }

        jobCounter = maxId + 1;
    }

    public void addRemoteJob(String companyId, String companyName, String jobTitle,
                             String location, String jobType, double salary,
                             String description, String requiredSkills, String experienceLevel,
                             boolean requiresInternet, String workHours) {

        String jobId = generateJobId();

        Remote remote = new Remote(
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
        remote.setDescription(description);
        remote.setRequiredSkills(requiredSkills);
        remote.setExperienceLevel(experienceLevel);
        remote.setUploadedDate(LocalDate.now().toString());

        jobs.add(remote);
        fileHandler.saveJobs(jobs);
        System.out.println("Job added successfully");
    }

    public void addOnsiteJob(String companyId, String companyName, String jobTitle,
                             String location, String jobType, double salary,
                             String description, String requiredSkills, String experienceLevel,
                             String officeAddress, boolean providesHousing) {

        String jobId = generateJobId();

        Onsite onsite = new Onsite(
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
        onsite.setDescription(description);
        onsite.setRequiredSkills(requiredSkills);
        onsite.setExperienceLevel(experienceLevel);
        onsite.setUploadedDate(LocalDate.now().toString());

        jobs.add(onsite);
        fileHandler.saveJobs(jobs);
        System.out.println("Job added successfully");
    }

    public void removeJob(String jobTitle) throws JobNotFoundException {
        boolean removed = jobs.removeIf(job -> job.getJobTitle().equalsIgnoreCase(jobTitle));

        if (!removed) {
            throw new JobNotFoundException("Job not found");
        } else {
            System.out.println("Job removed successfully");
            fileHandler.saveJobs(jobs);
        }
    }

    public void removeJobForCompany(String searchValue, String companyId) throws JobNotFoundException {
        boolean removed = jobs.removeIf(job ->
                job.getCompanyId().equalsIgnoreCase(companyId) &&
                        (job.getJobTitle().equalsIgnoreCase(searchValue) ||
                                job.getJobId().equalsIgnoreCase(searchValue)));

        if (!removed) {
            throw new JobNotFoundException("Job not found for your company");
        }

        System.out.println("Job removed successfully");
        fileHandler.saveJobs(jobs);
    }

    public void displayAllJobs() {
        if (jobs.isEmpty()) {
            System.out.println("No jobs available");
        } else {
            System.out.println("******** All Jobs *******");
            for (Job job : getJobsSortedByNewest()) {
                job.displayDetails();
            }
            System.out.println("**************************");
        }
    }

    public void displayJobsForCompany(String companyId) {
        boolean found = false;

        System.out.println("***** Your Company's Jobs *****");
        for (Job job : getJobsSortedByNewest()) {
            if (job.getCompanyId().equalsIgnoreCase(companyId)) {
                job.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No jobs found for your company.");
        }

        System.out.println("*******************************");
    }

    public Job findJob(String searchValue) throws JobNotFoundException {
        for (Job job : jobs) {
            if (job.getJobTitle().equalsIgnoreCase(searchValue) ||
                    job.getJobId().equalsIgnoreCase(searchValue)) {
                return job;
            }
        }
        throw new JobNotFoundException("Job not found");
    }

    public Job findJobForCompany(String searchValue, String companyId) throws JobNotFoundException {
        for (Job job : jobs) {
            if (job.getCompanyId().equalsIgnoreCase(companyId) &&
                    (job.getJobTitle().equalsIgnoreCase(searchValue) ||
                            job.getJobId().equalsIgnoreCase(searchValue))) {
                return job;
            }
        }

        throw new JobNotFoundException("Job not found for your company");
    }

    public boolean jobBelongsToCompany(String jobId, String companyId) {
        for (Job job : jobs) {
            if (job.getJobId().equalsIgnoreCase(jobId) &&
                    job.getCompanyId().equalsIgnoreCase(companyId)) {
                return true;
            }
        }

        return false;
    }

    public String getJobTitleById(String jobId) {
        for (Job job : jobs) {
            if (job.getJobId().equalsIgnoreCase(jobId)) {
                return job.getJobTitle();
            }
        }

        return "";
    }

    private List<Job> getJobsSortedByNewest() {
        List<Job> sortedJobs = new ArrayList<>(jobs);
        sortedJobs.sort(Comparator.comparing(this::parseJobDate).reversed());
        return sortedJobs;
    }

    private LocalDate parseJobDate(Job job) {
        String uploadedDate = job.getUploadedDate();
        if (uploadedDate == null || uploadedDate.isBlank()) {
            return LocalDate.MIN;
        }

        try {
            return LocalDate.parse(uploadedDate);
        } catch (Exception e) {
            return LocalDate.MIN;
        }
    }

    public void loadJobsFromFile() {
        jobs.clear();
        List<Job> loadedJobs = fileHandler.loadJobs();
        jobs.addAll(loadedJobs);
        updateJobCounter();
    }

    public void findJobById(String jobId) {

    }
}
