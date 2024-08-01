package vn.jobhunter.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.jobhunter.jobhunter.domain.Skill;
import vn.jobhunter.jobhunter.service.SkillService;
import vn.jobhunter.jobhunter.util.error.IdInvalidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
@RestController
public class SkillController {

    final private SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/skill")
    public ResponseEntity<Object> createSkill(@Valid @RequestBody Skill skill) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleCreateSkill(skill));
    }

    @PutMapping("/skill")
    public ResponseEntity<Object> updateSkill(@Valid @RequestBody Skill skill) throws IdInvalidException {
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

}
