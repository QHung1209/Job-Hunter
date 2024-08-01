package vn.jobhunter.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.jobhunter.jobhunter.domain.Skill;
import vn.jobhunter.jobhunter.domain.response.ResultPaginationDTO;
import vn.jobhunter.jobhunter.service.SkillService;
import vn.jobhunter.jobhunter.util.error.IdInvalidException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
@RestController
public class SkillController {

    final private SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/skills")
    public ResponseEntity<Object> createSkill(@Valid @RequestBody Skill skill) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleCreateSkill(skill));
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable("id") long id) throws IdInvalidException
    {
        Skill skill = this.skillService.handleGetSkill(id);
        if (skill == null) {
            throw new IdInvalidException("Skill Id khong ton tai");
        }
        return ResponseEntity.ok(skill);
    }

    @GetMapping("/skills")
    public ResponseEntity<ResultPaginationDTO> getAllSkills(@Filter Specification<Skill> specification, Pageable pageable )
    {
        return ResponseEntity.ok(this.skillService.handleGetAllSkill(specification, pageable));
    }

    @PutMapping("/skills")
    public ResponseEntity<Object> updateSkill(@RequestBody Skill skill) throws IdInvalidException {
        Skill currentSkill = this.skillService.handleGetSkill(skill.getId());
        if (currentSkill == null) {
            throw new IdInvalidException("Skill Id khong ton tai");
        }

        if (skill.getName() != null && this.skillService.isSkillExist(skill.getName())) {
            throw new IdInvalidException("Skill da ton tai");
        }
        currentSkill.setName(skill.getName());
        currentSkill.setJobs(skill.getJobs());
        return ResponseEntity.ok().body(this.skillService.handleUpdateSkill(currentSkill));
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable("id") long id) throws IdInvalidException
    {   
        if(this.skillService.handleGetSkill(id) == null)
        {
            throw new IdInvalidException("Skill id khong ton tai");
        }  
        this.skillService.handleDeleteSkill(id);

        return ResponseEntity.ok(null);
    }

}
