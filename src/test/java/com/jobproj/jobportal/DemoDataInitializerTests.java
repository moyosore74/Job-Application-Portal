package com.jobproj.jobportal;

import com.jobproj.jobportal.config.DemoDataInitializer;
import com.jobproj.jobportal.entity.Job;
import com.jobproj.jobportal.repository.CompanyRepository;
import com.jobproj.jobportal.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("local")
class DemoDataInitializerTests {

    @Autowired
    private DemoDataInitializer demoDataInitializer;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    @Test
    void localProfileSeedsDemoJobsAndCompanies() {
        assertTrue(companyRepository.existsById("COMP-DEMO-001"));
        assertTrue(companyRepository.existsById("COMP-DEMO-002"));
        assertTrue(companyRepository.existsById("COMP-DEMO-003"));

        Job frontendJob = jobRepository.findById("JOB-DEMO-001").orElseThrow();
        assertEquals("Frontend Developer", frontendJob.getJobTitle());
        assertEquals("COMP-DEMO-001", frontendJob.getCompany().getCompanyId());
        assertEquals("Remote", frontendJob.getLocation());
<<<<<<< HEAD
=======
        assertEquals("NGN", frontendJob.getCurrency());
>>>>>>> a270d61 (Update API and documentation)
    }

    @Test
    void localProfileSeedingIsIdempotent() throws Exception {
        long companyCount = companyRepository.count();
        long jobCount = jobRepository.count();

        demoDataInitializer.run(null);

        assertEquals(companyCount, companyRepository.count());
        assertEquals(jobCount, jobRepository.count());
    }
}
