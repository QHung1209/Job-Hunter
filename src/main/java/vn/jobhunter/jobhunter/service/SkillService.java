package vn.jobhunter.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import vn.jobhunter.jobhunter.domain.Skill;
import vn.jobhunter.jobhunter.domain.response.ResultPaginationDTO;
import vn.jobhunter.jobhunter.repository.SkillRepository;

@Service
public class SkillService {
    final private SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill handleCreateSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public Skill handleGetSkill(Long id) {
        Optional<Skill> skill = this.skillRepository.findById(id);
        return skill.isPresent() ? skill.get() : null;
    }

    public boolean isSkillExist(String name) {
        return this.skillRepository.existsByName(name);
    }

    public Skill handleUpdateSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public void handleDeleteSkill(long id)
    {
        Skill current = this.handleGetSkill(id);
        current.getJobs().forEach(job -> job.getSkills().remove(current));
        this.skillRepository.delete(current);
    }

    public ResultPaginationDTO handleGetAllSkill( Specification<Skill> specification, Pageable pageable)
    {
        Page<Skill> pageSkill = this.skillRepository.findAll(specification,pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageSkill.getTotalPages());
        mt.setTotal(pageSkill.getTotalPages());

        rs.setMeta(mt);
        List<Skill> s = pageSkill.getContent();
        rs.setResult(s);
        return rs;
    }
}
