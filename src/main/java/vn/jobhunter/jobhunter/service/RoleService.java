package vn.jobhunter.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.jobhunter.jobhunter.domain.Permission;
import vn.jobhunter.jobhunter.domain.Role;
import vn.jobhunter.jobhunter.domain.response.ResultPaginationDTO;
import vn.jobhunter.jobhunter.repository.PermissionRepository;
import vn.jobhunter.jobhunter.repository.RoleRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    final private RoleRepository roleRepository;
    final private PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public boolean isExistName(String name) {
        return this.roleRepository.existsByName(name);
    }

    public Role handleCreateRole(Role role) {
        if (role.getPermissions() != null) {
            List<Long> reqPermissions = role.getPermissions().stream()
                    .map(x -> x.getId()).collect(Collectors.toList());

            List<Permission> listPermissions = permissionRepository.findByIdIn(reqPermissions);
            role.setPermissions(listPermissions);
        }

        return this.roleRepository.save(role);
    }

    public Role handleGetRole(long id) {
        Optional<Role> role = this.roleRepository.findById(null);
        return role.isPresent() ? role.get() : null;
    }

    public Role handleUpdateRole(Role role) {
        Role roleUpdate = this.handleGetRole(role.getId());
        if (roleUpdate.getPermissions() != null) {
            List<Long> reqPermissions = role.getPermissions().stream()
                    .map(x -> x.getId()).collect(Collectors.toList());

            List<Permission> listPermissions = permissionRepository.findByIdIn(reqPermissions);
            role.setPermissions(listPermissions);
        }
        roleUpdate.setDescription(role.getDescription());
        roleUpdate.setName(role.getName());
        roleUpdate.setActive(role.isActive());
        roleUpdate.setPermissions(role.getPermissions());
        return roleUpdate;
    }

    public void handleDeleteRole(long id) {
        this.roleRepository.deleteById(id);
    }

    public ResultPaginationDTO handleGetAllRole(Specification<Role> specification, Pageable pageable) {
        Page<Role> pageRole = this.roleRepository.findAll(specification, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageRole.getTotalPages());
        mt.setTotal(pageRole.getTotalPages());
        
        rs.setMeta(mt);
        rs.setResult(pageRole.getContent());
        return rs;
    }
}
