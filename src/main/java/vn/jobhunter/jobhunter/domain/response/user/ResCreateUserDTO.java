package vn.jobhunter.jobhunter.domain.response.user;

import java.time.Instant;

import vn.jobhunter.jobhunter.util.constant.GenderEnum;

public class ResCreateUserDTO {
    private long id;
    private String name;
    private String email;
    private GenderEnum GenderEnum;
    private int age;
    private String address;
    private Instant createdAt;
    private CompanyUser company;
    private RoleUser role;

    public CompanyUser getCompanyUser() {
        return company;
    }

    public void setCompanyUser(CompanyUser companyUser) {
        this.company = companyUser;
    }

    public static class CompanyUser {
        private long id;
        private String name;

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

    }

    public static class RoleUser {
        private long id;
        private String name;

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

    public CompanyUser getCompany() {
        return company;
    }

    public void setCompany(CompanyUser company) {
        this.company = company;
    }

    public RoleUser getRole() {
        return role;
    }

    public void setRole(RoleUser role) {
        this.role = role;
    }

}
