package vn.jobhunter.jobhunter.domain.response.user;

import java.time.Instant;

import vn.jobhunter.jobhunter.domain.response.user.ResCreateUserDTO.CompanyUser;
import vn.jobhunter.jobhunter.domain.response.user.ResCreateUserDTO.RoleUser;
import vn.jobhunter.jobhunter.util.constant.GenderEnum;

public class ResUserDTO {
    private long id;
    private String name;
    private String email;
    private GenderEnum GenderEnum;
    private int age;
    private String address;
    private Instant createdAt;
    private String createdBy;
    private CompanyUser company;
    private RoleUser role;

    public RoleUser getRole() {
        return role;
    }

    public void setRole(RoleUser role) {
        this.role = role;
    }

    public CompanyUser getCompanyUser() {
        return company;
    }

    public void setCompanyUser(CompanyUser companyUser) {
        this.company = companyUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GenderEnum getGenderEnum() {
        return GenderEnum;
    }

    public void setGenderEnum(GenderEnum genderEnum) {
        GenderEnum = genderEnum;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
