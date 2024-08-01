package vn.jobhunter.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.jobhunter.jobhunter.domain.Company;
import vn.jobhunter.jobhunter.domain.User;
import vn.jobhunter.jobhunter.domain.response.ResultPaginationDTO;
import vn.jobhunter.jobhunter.repository.CompanyRepository;
import vn.jobhunter.jobhunter.repository.UserRepository;

import java.util.Optional;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public Company handleGetCompany(long id) {
        Optional<Company> company = this.companyRepository.findById(id);

        return company.isPresent() == true ? company.get() : null;
    }

    public ResultPaginationDTO handleGetAllCompany(Specification<Company> specification, Pageable pageable) {
        Page<Company> pCompany = this.companyRepository.findAll(specification, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pCompany.getTotalPages());
        mt.setTotal(pCompany.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pCompany.getContent());
        return rs;
    }

    public Company handleUpdateCompany(Company company) {
        Company current = this.handleGetCompany(company.getId());
        if (company != null) {
            current.setAddress(company.getAddress());
            current.setDescription(company.getDescription());
            current.setLogo(company.getLogo());
            current.setName(company.getName());
            current = this.companyRepository.save(current);
        }
        return current;
    }

    public void handleDeleteCompany(long id) {
        Optional<Company> com = this.companyRepository.findById(id);
        if (com.isPresent()) {
            Company company = com.get();
            List<User> listUsers = this.userRepository.findByCompany(company);
            this.userRepository.deleteAll(listUsers);
        }
        this.companyRepository.deleteById(id);
    }

}
