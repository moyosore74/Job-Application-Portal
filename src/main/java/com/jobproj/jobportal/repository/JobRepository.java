package com.jobproj.jobportal.repository;

import com.jobproj.jobportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, String> {
    List<Job> findAllByOrderByUploadedDateDesc();
    List<Job> findByCompanyCompanyIdOrderByUploadedDateDesc(String companyId);
    Optional<Job> findByJobIdIgnoreCaseOrJobTitleIgnoreCase(String jobId, String jobTitle);
    Optional<Job> findByCompanyCompanyIdAndJobIdIgnoreCase(String companyId, String jobId);
    Optional<Job> findByCompanyCompanyIdAndJobTitleIgnoreCase(String companyId, String jobTitle);
}
