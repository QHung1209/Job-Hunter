package vn.jobhunter.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.jobhunter.jobhunter.domain.Job;
import vn.jobhunter.jobhunter.domain.response.ResultPaginationDTO;
import vn.jobhunter.jobhunter.domain.response.job.ResCreateJobDTO;
import vn.jobhunter.jobhunter.domain.response.job.ResUpdateJobDTO;
import vn.jobhunter.jobhunter.service.JobService;
import vn.jobhunter.jobhunter.util.error.IdInvalidException;

@RestController
public class JobController {
    final private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/jobs")
    public ResponseEntity<ResCreateJobDTO> createJob(@Valid @RequestBody Job job)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.jobService.handlResCreateJobDTO(job));
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") long id) throws IdInvalidException
    {   Job job = this.jobService.handleGetJob(id);
        if(job == null)
        {
            throw new IdInvalidException("Job id khong ton tai");
        }
        
        return ResponseEntity.ok().body(job);
    }

    @GetMapping("/jobs")
    public ResponseEntity<ResultPaginationDTO> getAllJob(@Filter Specification<Job> specification, Pageable pageable)
    {
        return ResponseEntity.ok().body(this.jobService.handleGetAllJob(specification, pageable));
    }

    @PutMapping("/job")
    public ResponseEntity<ResUpdateJobDTO> updateJob(@RequestBody Job job) throws IdInvalidException
    {   
        if(this.jobService.handleGetJob(job.getId()) == null)
        {
            throw new IdInvalidException("Job id khong ton tai");
        }
        return ResponseEntity.ok().body(this.jobService.handlResUpdateJob(job));
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") long id) throws IdInvalidException
    {
        if(this.jobService.handleGetJob(id) == null)
        {
            throw new IdInvalidException("Job id khong ton tai");
        }
        this.jobService.handleDeleteJob(id);
        return ResponseEntity.ok().body(null);
    }
}
