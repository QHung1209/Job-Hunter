package vn.jobhunter.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.jobhunter.jobhunter.domain.Permission;
import vn.jobhunter.jobhunter.domain.response.ResultPaginationDTO;
import vn.jobhunter.jobhunter.repository.PermissionRepository;
import java.util.Optional;
import java.util.List;

@Service
public class PermissionService {
    final private PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission handleCreatePermission(Permission permission) {
        return this.permissionRepository.save(permission);
    }

    public boolean isPermissionExists(Permission permission) {
        return this.permissionRepository.existsByApiPathAndMethodAndModule(permission.getApiPath(),
                permission.getMethod(), permission.getModule());
    }

    public Permission handleGetPermissionById(long id) {
        Optional<Permission> permissionOptional = this.permissionRepository.findById(id);
        return permissionOptional.isPresent() ? permissionOptional.get() : null;
    }

    public Permission handleUpdatePermission(Permission permission) {
        Permission updatePermission = this.handleGetPermissionById(permission.getId());

        if (updatePermission != null) {
            updatePermission.setApiPath(permission.getApiPath());
            updatePermission.setMethod(permission.getMethod());
            updatePermission.setModule(permission.getModule());
            updatePermission.setName(permission.getName());
            return this.permissionRepository.save(permission);
        }
        return null;
    }

    public ResultPaginationDTO handleGetAllPermission(Specification<Permission> specification, Pageable pageable) {

        Page<Permission> pagePermission = this.permissionRepository.findAll(specification, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pagePermission.getTotalPages());
        mt.setTotal(pagePermission.getTotalPages());

        rs.setMeta(mt);
        List<Permission> s = pagePermission.getContent();
        rs.setResult(s);
        return rs;

    }

    public void handleDeletePermission(long id)
    {
        Permission current = this.handleGetPermissionById(id);
        current.getRoles().forEach(role->role.getPermissions().remove(current));

        this.permissionRepository.delete(current);
    }
}
