package vn.jobhunter.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.jobhunter.jobhunter.domain.Company;
import vn.jobhunter.jobhunter.repository.CompanyRepository;
import vn.jobhunter.jobhunter.util.SecurityUtil;
import java.time.Instant;
import java.util.Optional;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public Company handleGetCompany(long id) {
        Optional<Company> company = this.companyRepository.findById(id);

        return company.isPresent() == true ? company.get() : null;
    }

    public List<Company> handleGetAllCompany() {
        return this.companyRepository.findAll();
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

    public void handleDeleteCompany(long id)
    {
        this.companyRepository.deleteById(id);
    }

}
