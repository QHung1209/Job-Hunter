package vn.jobhunter.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import vn.jobhunter.jobhunter.domain.Resume;
import vn.jobhunter.jobhunter.domain.response.ResultPaginationDTO;
import vn.jobhunter.jobhunter.domain.response.resume.ResCreateResumeDTO;
import vn.jobhunter.jobhunter.domain.response.resume.ResGetResumeDTO;
import vn.jobhunter.jobhunter.domain.response.resume.ResUpdateResumeDTO;
import vn.jobhunter.jobhunter.service.ResumeService;
import vn.jobhunter.jobhunter.util.error.IdInvalidException;

@RestController
public class ResumeController {
    final private ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("resumes")
    public ResponseEntity<ResCreateResumeDTO> createResume(@Valid @RequestBody Resume resume)
            throws IdInvalidException {
        if (this.resumeService.checkUserAndJobExists(resume) == false) {
            throw new IdInvalidException("Job/User khong ton tai");
        }

        Resume createResume = this.resumeService.handleCreateResume(resume);

        return ResponseEntity.ok(this.resumeService.convertToCreateResumeDTO(createResume));
    }

    @GetMapping("resumes/{id}")
    public ResponseEntity<ResGetResumeDTO> getResume(@PathVariable("id") long id) throws IdInvalidException {
        Resume current = this.resumeService.handleGetResumeById(id);
        if (current == null) {
            throw new IdInvalidException("Resume id khong ton tai");
        }
        return ResponseEntity.ok(this.resumeService.convertToResGetResumeDTO(current));
    }

    @GetMapping("resumes")
    public ResponseEntity<ResultPaginationDTO> getAllResume(@Filter Specification<Resume> specification,
            Pageable pageable) {
        return ResponseEntity.ok(this.resumeService.handleGetAllResume(specification, pageable));
    }

    @PutMapping("resumes")
    public ResponseEntity<ResUpdateResumeDTO> updateResume(@RequestBody Resume resume) throws IdInvalidException {
        Resume current = this.resumeService.handleGetResumeById(resume.getId());
        if (current == null) {
            throw new IdInvalidException("Resume id khong ton tai");
        }
        current.setStatus(resume.getStatus());
        current = this.resumeService.handleUpdateResume(current);
        return ResponseEntity.ok(this.resumeService.convertToUpdateResumeDTO(current));
    }

    @DeleteMapping("resumes/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable("id") long id) throws IdInvalidException {
        if (this.resumeService.handleGetResumeById(id) == null) {
            throw new IdInvalidException("Resume id khong ton tai");
        }
        this.resumeService.handleDeleteResume(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("resume/by-user")
    public ResponseEntity<ResultPaginationDTO> getResumeByUser(Pageable pageable)
    {
        return ResponseEntity.ok().body(this.resumeService.getResumeByUser(pageable));
    }
}
