package vn.jobhunter.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.jobhunter.jobhunter.domain.Job;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    
}
