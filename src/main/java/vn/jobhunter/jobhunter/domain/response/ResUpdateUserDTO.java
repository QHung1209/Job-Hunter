package vn.jobhunter.jobhunter.domain.response;

import java.time.Instant;

import vn.jobhunter.jobhunter.domain.response.ResCreateUserDTO.CompanyUser;
import vn.jobhunter.jobhunter.util.constant.GenderEnum;

public class ResUpdateUserDTO {
    private long id;
    private String address;
    private GenderEnum gender;
    private int age;
    private String name;
    private Instant updatedAt;

    private CompanyUser company;

    public CompanyUser getCompanyUser() {
        return company;
    }

    public void setCompanyUser(CompanyUser companyUser) {
        this.company = companyUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}
