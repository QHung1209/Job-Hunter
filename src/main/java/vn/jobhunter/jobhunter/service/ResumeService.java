package vn.jobhunter.jobhunter.service;

import vn.jobhunter.jobhunter.domain.Resume;
import vn.jobhunter.jobhunter.domain.response.ResultPaginationDTO;
import vn.jobhunter.jobhunter.domain.response.resume.ResCreateResumeDTO;
import vn.jobhunter.jobhunter.domain.response.resume.ResGetResumeDTO;
import vn.jobhunter.jobhunter.domain.response.resume.ResUpdateResumeDTO;
import vn.jobhunter.jobhunter.repository.JobRepository;
import vn.jobhunter.jobhunter.repository.ResumeRepository;
import vn.jobhunter.jobhunter.repository.UserRepository;
import vn.jobhunter.jobhunter.util.SecurityUtil;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.turkraft.springfilter.converter.FilterSpecification;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import com.turkraft.springfilter.parser.node.FilterNode;

@Service
public class ResumeService {

    @Autowired
    private FilterParser filterParser;

    @Autowired
    private FilterSpecificationConverter filterSpecificationConverter;

    final private ResumeRepository resumeRepository;
    final private UserRepository userRepository;
    final private JobRepository jobRepository;

    public ResumeService(ResumeRepository resumeRepository, JobRepository jobRepository,
            UserRepository userRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public Resume handleCreateResume(Resume resume) {
        return this.resumeRepository.save(resume);
    }

    public ResCreateResumeDTO convertToCreateResumeDTO(Resume resume) {
        ResCreateResumeDTO res = new ResCreateResumeDTO();
        res.setId(resume.getId());
        res.setCreateAt(resume.getCreatedAt());
        res.setCreatedBy(resume.getCreatedBy());
        return res;
    }

    public Resume handleGetResumeById(long id) {
        Optional<Resume> resumeOptional = this.resumeRepository.findById(id);
        return resumeOptional.isPresent() ? resumeOptional.get() : null;
    }

    public ResGetResumeDTO convertToResGetResumeDTO(Resume resume) {
        ResGetResumeDTO res = new ResGetResumeDTO();

        res.setCreatedAt(resume.getCreatedAt());
        res.setCreatedBy(resume.getCreatedBy());
        res.setEmail(resume.getEmail());
        res.setId(resume.getId());
        res.setStatus(resume.getStatus());
        res.setUrl(resume.getUrl());

        ResGetResumeDTO.UserResume user = new ResGetResumeDTO.UserResume();
        user.setId(resume.getUser().getId());
        user.setName(resume.getUser().getName());
        res.setUser(user);

        ResGetResumeDTO.JobResume job = new ResGetResumeDTO.JobResume();
        job.setId(resume.getJob().getId());
        job.setName(resume.getJob().getName());
        res.setJob(job);

        return res;
    }

    public List<ResGetResumeDTO> convertToListResGetResumeDTOs(List<Resume> listResumes) {
        List<ResGetResumeDTO> listResDTO = new ArrayList<>();
        for (Resume res : listResumes) {
            listResDTO.add(this.convertToResGetResumeDTO(res));
        }

        return listResDTO;
    }

    public ResultPaginationDTO handleGetAllResume(Specification<Resume> specification, Pageable pageable) {
        Page<Resume> pageResume = this.resumeRepository.findAll(pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageResume.getTotalPages());
        mt.setTotal(pageResume.getTotalPages());

        rs.setMeta(mt);
        List<Resume> s = pageResume.getContent();
        rs.setResult(this.convertToListResGetResumeDTOs(s));
        return rs;
    }

    public Resume handleUpdateResume(Resume resume) {
        return this.resumeRepository.save(resume);
    }

    public ResUpdateResumeDTO convertToUpdateResumeDTO(Resume resume) {
        ResUpdateResumeDTO res = new ResUpdateResumeDTO();
        res.setUpdateBy(resume.getUpdatedBy());
        res.setUpdatedAt(resume.getUpdatedAt());
        return res;
    }

    public boolean checkUserAndJobExists(Resume resume) {
        if (resume.getUser() == null)
            return false;
        if (this.userRepository.findById(resume.getUser().getId()).isEmpty())
            return false;

        if (resume.getJob() == null)
            return false;
        if (this.jobRepository.findById(resume.getJob().getId()).isEmpty())
            return false;
        return true;
    }

    public void handleDeleteResume(long id) {
        this.resumeRepository.deleteById(id);
    }

    public ResultPaginationDTO getResumeByUser(Pageable pageable) {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null;
        FilterNode node = filterParser.parse("email='" + email + "'");
        FilterSpecification<Resume> specification = filterSpecificationConverter.convert(node);

        Page<Resume> pageResume = this.resumeRepository.findAll(specification,pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageResume.getTotalPages());
        mt.setTotal(pageResume.getTotalPages());

        rs.setMeta(mt);
        List<Resume> s = pageResume.getContent();
        rs.setResult(this.convertToListResGetResumeDTOs(s));
        return rs;
    }
}
