package vn.jobhunter.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import vn.jobhunter.jobhunter.domain.Company;
import vn.jobhunter.jobhunter.service.CompanyService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.Optional;

@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Object> createNewCompany(@Valid @RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(company));
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Object> getCompany(@PathVariable("id") long id) {
        return ResponseEntity.ok(this.companyService.handleGetCompany(id));
    }

    @GetMapping("/companies")
    public ResponseEntity<Object> getAllCompany(@RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {
        String sCurrent = currentOptional.isPresent() ? currentOptional.get() : "";
        String sPageSise = pageSizeOptional.isPresent() ? pageSizeOptional.get() : "";

        Pageable pageable = PageRequest.of(Integer.parseInt(sCurrent)-1,Integer.parseInt(sPageSise));
        return ResponseEntity.ok(this.companyService.handleGetAllCompany(pageable));
    }

    @PutMapping("/companies")
    public ResponseEntity<Object> updateCompany(@Valid @RequestBody Company company) {
        return ResponseEntity.ok(this.companyService.handleUpdateCompany(company));
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") long id) {
        this.companyService.handleDeleteCompany(id);
        return ResponseEntity.ok().body(null);
    }
}
