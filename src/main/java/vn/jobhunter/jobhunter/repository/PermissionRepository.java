package vn.jobhunter.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import vn.jobhunter.jobhunter.domain.Permission;
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission>{
    boolean existsByApiPathAndMethodAndModule(String apiPath, String method, String module);

    List<Permission> findByIdIn(List<Long> id);
}
