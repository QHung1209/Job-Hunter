package vn.jobhunter.jobhunter.domain.dto;

import java.time.Instant;

import vn.jobhunter.jobhunter.util.constant.GenderEnum;

public class ResUpdateUserDTO {
    private long id;
    private String address;
    private GenderEnum gender;
    private int age;
    private String name;
    private Instant updatedAt;
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
