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
import vn.jobhunter.jobhunter.domain.Role;
import vn.jobhunter.jobhunter.domain.response.ResultPaginationDTO;
import vn.jobhunter.jobhunter.service.RoleService;
import vn.jobhunter.jobhunter.util.error.IdInvalidException;

@RestController
public class RoleController {
    final private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    
    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) throws IdInvalidException
    {
        if(this.roleService.isExistName(role.getName()))
        {
            throw new IdInvalidException("Ten da ton tai");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.handleCreateRole(role));
    }

    @PutMapping("/roles")
    public ResponseEntity<Role> updateRole(@Valid @RequestBody Role role) throws IdInvalidException
    {
        if(this.roleService.handleGetRole(role.getId()) == null)
        {
            throw new IdInvalidException("Role id khong ton tai");
        }

        if(this.roleService.isExistName(role.getName()))
        {
            throw new IdInvalidException("Ten da ton tai");
        }
        return ResponseEntity.ok(this.roleService.handleUpdateRole(role));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") long id) throws IdInvalidException
    {
        if(this.roleService.handleGetRole(id) == null)
        {
            throw new IdInvalidException("Role id khong ton tai");
        }
        this.roleService.handleDeleteRole(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/roles")
    public ResponseEntity<ResultPaginationDTO> getAllRole(@Filter Specification<Role> specification, Pageable pageable)
    {
        return ResponseEntity.ok(this.roleService.handleGetAllRole(specification, pageable));
    }

    @GetMapping("roles/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") long id) throws IdInvalidException
    {
        Role role = this.roleService.handleGetRole(id);
        if(role == null) {
            throw new IdInvalidException("Id khong ton tai");
        }
        return ResponseEntity.ok(role);
    }
}
