package com.jobproj.jobportal.repository;

import com.jobproj.jobportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, String> {
    List<Application> findAllByOrderByApplicationDateDesc();
    List<Application> findByApplicantEmailIgnoreCaseOrderByApplicationDateDesc(String email);
    List<Application> findByJobCompanyCompanyIdOrderByApplicationDateDesc(String companyId);
    boolean existsByApplicantUserIdAndJobJobId(String applicantUserId, String jobId);
    Optional<Application> findByApplicationIdIgnoreCase(String applicationId);
    Optional<Application> findByApplicationIdIgnoreCaseAndJobCompanyCompanyId(String applicationId, String companyId);
}
