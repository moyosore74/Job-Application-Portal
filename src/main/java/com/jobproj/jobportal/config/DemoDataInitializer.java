package com.jobproj.jobportal.config;

import com.jobproj.jobportal.entity.Company;
import com.jobproj.jobportal.entity.Job;
import com.jobproj.jobportal.repository.CompanyRepository;
import com.jobproj.jobportal.repository.JobRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@Profile("local")
public class DemoDataInitializer implements ApplicationRunner {

    private static final LocalDate DEMO_UPLOAD_DATE = LocalDate.of(2026, 6, 1);

    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;

    public DemoDataInitializer(CompanyRepository companyRepository, JobRepository jobRepository) {
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<Company> companies = List.of(
                new Company("COMP-DEMO-001", "BrightPath Technologies"),
                new Company("COMP-DEMO-002", "Lagoon Health Systems"),
                new Company("COMP-DEMO-003", "CivicPay Africa")
        );

        for (Company company : companies) {
            if (!companyRepository.existsById(company.getCompanyId())) {
                companyRepository.save(company);
            }
        }

        List<Job> jobs = List.of(
                buildJob(
                        "JOB-DEMO-001",
                        "COMP-DEMO-001",
                        "Frontend Developer",
                        "Build responsive React interfaces for a growing recruitment platform.",
                        "Remote",
                        "Remote",
                        "React, JavaScript, Tailwind CSS, REST APIs",
                        "Mid Level",
                        450000,
                        "NGN",
                        true,
                        "Flexible core hours",
                        null,
                        false
                ),
                buildJob(
                        "JOB-DEMO-002",
                        "COMP-DEMO-001",
                        "Backend Engineer",
                        "Design and maintain secure Spring Boot APIs for business workflow tools.",
                        "Lagos, Nigeria",
                        "Hybrid",
                        "Java, Spring Boot, MySQL, JWT",
                        "Mid Level",
                        650000,
<<<<<<< HEAD
=======
                        "NGN",
>>>>>>> a270d61 (Update API and documentation)
                        true,
                        "9am-5pm",
                        "24 Admiralty Way, Lekki Phase 1",
                        false
                ),
                buildJob(
                        "JOB-DEMO-003",
                        "COMP-DEMO-002",
                        "Data Analyst",
                        "Turn health operations data into dashboards and weekly decision reports.",
                        "Abuja, Nigeria",
                        "Onsite",
                        "SQL, Excel, Power BI, Data Cleaning",
                        "Entry Level",
                        350000,
<<<<<<< HEAD
=======
                        "NGN",
>>>>>>> a270d61 (Update API and documentation)
                        false,
                        "8am-4pm",
                        "12 Hospital Road, Garki",
                        false
                ),
                buildJob(
                        "JOB-DEMO-004",
                        "COMP-DEMO-003",
                        "Product Manager",
                        "Lead payment product discovery, roadmap planning, and cross-functional delivery.",
                        "Remote",
                        "Remote",
                        "Product Strategy, Agile, User Research, Analytics",
                        "Senior Level",
                        900000,
<<<<<<< HEAD
=======
                        "NGN",
>>>>>>> a270d61 (Update API and documentation)
                        true,
                        "Flexible core hours",
                        null,
                        false
                ),
                buildJob(
                        "JOB-DEMO-005",
                        "COMP-DEMO-003",
                        "Customer Support Specialist",
                        "Support merchants, resolve account issues, and document recurring product feedback.",
                        "Port Harcourt, Nigeria",
                        "Onsite",
                        "Customer Support, CRM, Communication, Troubleshooting",
                        "Entry Level",
                        280000,
<<<<<<< HEAD
=======
                        "NGN",
>>>>>>> a270d61 (Update API and documentation)
                        false,
                        "Shift based",
                        "8 Aba Road, Port Harcourt",
                        true
                )
        );

        for (Job job : jobs) {
            if (!jobRepository.existsById(job.getJobId())) {
                jobRepository.save(job);
            }
        }
    }

    private Job buildJob(
            String jobId,
            String companyId,
            String jobTitle,
            String description,
            String location,
            String jobType,
            String requiredSkills,
            String experienceLevel,
            double salary,
<<<<<<< HEAD
=======
            String currency,
>>>>>>> a270d61 (Update API and documentation)
            Boolean requiresInternet,
            String workHours,
            String officeAddress,
            Boolean providesHousing
    ) {
        Company company = companyRepository.getReferenceById(companyId);

        Job job = new Job();
        job.setJobId(jobId);
        job.setCompany(company);
        job.setJobTitle(jobTitle);
        job.setDescription(description);
        job.setLocation(location);
        job.setJobType(jobType);
        job.setRequiredSkills(requiredSkills);
        job.setExperienceLevel(experienceLevel);
        job.setSalary(salary);
<<<<<<< HEAD
=======
        job.setCurrency(currency);
>>>>>>> a270d61 (Update API and documentation)
        job.setRequiresInternet(requiresInternet);
        job.setWorkHours(workHours);
        job.setOfficeAddress(officeAddress);
        job.setProvidesHousing(providesHousing);
        job.setUploadedDate(DEMO_UPLOAD_DATE);
        return job;
    }
}
