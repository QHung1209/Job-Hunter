package vn.jobhunter.jobhunter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.jobhunter.jobhunter.domain.Job;
import vn.jobhunter.jobhunter.domain.Skill;
import vn.jobhunter.jobhunter.domain.response.ResultPaginationDTO;
import vn.jobhunter.jobhunter.domain.response.job.ResCreateJobDTO;
import vn.jobhunter.jobhunter.domain.response.job.ResUpdateJobDTO;
import vn.jobhunter.jobhunter.repository.JobRepository;
import vn.jobhunter.jobhunter.repository.SkillRepository;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;

    public JobService(JobRepository jobRepository, SkillRepository skillRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
    }

    public ResCreateJobDTO handlResCreateJobDTO(Job job) {
        if (job.getSkills() != null) {
            List<Long> reqSkills = new ArrayList<>();
            for (Skill s : job.getSkills()) {
                reqSkills.add(s.getId());
            }
            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            job.setSkills(dbSkills);
        }

        Job current = this.jobRepository.save(job);

        ResCreateJobDTO res = new ResCreateJobDTO();
        res.setName(current.getName());
        res.setLevel(current.getLevel());
        res.setLocation(current.getlocation());
        res.setEndDate(current.getEndDate());
        res.setQuantity(current.getQuantity());
        res.setSalary(current.getQuantity());
        res.setStartDate(current.getStartDate());

        List<String> skillDTO = new ArrayList<>();
        for (Skill s : current.getSkills()) {
            skillDTO.add(s.getName());
        }
        res.setSkills(skillDTO);
        return res;
    }

    public Job handleGetJob(long id) {
        Optional<Job> job = this.jobRepository.findById(id);
        return job.isPresent() ? job.get() : null;
    }

    public ResultPaginationDTO handleGetAllJob(Specification<Job> specification, Pageable pageable) {

        Page<Job> jobPage = this.jobRepository.findAll(pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(jobPage.getTotalPages());
        mt.setTotal(jobPage.getTotalPages());

        rs.setMeta(mt);
        List<Job> j = jobPage.getContent();
        rs.setResult(j);
        return rs;
    }

    public ResUpdateJobDTO handlResUpdateJob(Job job) {
        if (job.getSkills() != null) {
            List<Long> reqSkills = new ArrayList<>();
            for (Skill s : job.getSkills()) {
                reqSkills.add(s.getId());
            }
            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            job.setSkills(dbSkills);
        }

        Job current = this.jobRepository.save(job);

        ResUpdateJobDTO res = new ResUpdateJobDTO();
        res.setName(current.getName());
        res.setLevel(current.getLevel());
        res.setLocation(current.getlocation());
        res.setEndDate(current.getEndDate());
        res.setQuantity(current.getQuantity());
        res.setSalary(current.getQuantity());
        res.setStartDate(current.getStartDate());

        List<String> skillDTO = new ArrayList<>();
        for (Skill s : current.getSkills()) {
            skillDTO.add(s.getName());
        }
        res.setSkills(skillDTO);
        return res;

    }

    public void handleDeleteJob(long id) {
        this.jobRepository.deleteById(id);
    }
}
