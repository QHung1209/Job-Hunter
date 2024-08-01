package vn.jobhunter.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.jobhunter.jobhunter.domain.Company;
import vn.jobhunter.jobhunter.domain.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>{
    User findByEmail(String email);
    User findByName(String username);
    User findByRefreshTokenAndEmail(String token, String email);
    boolean existsByEmail(String email);
    List<User> findByCompany(Company company);
} 