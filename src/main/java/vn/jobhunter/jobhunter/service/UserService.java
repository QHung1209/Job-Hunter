package vn.jobhunter.jobhunter.service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.jobhunter.jobhunter.domain.Company;
import vn.jobhunter.jobhunter.domain.User;
import vn.jobhunter.jobhunter.domain.dto.Meta;
import vn.jobhunter.jobhunter.domain.dto.ResCreateUserDTO;
import vn.jobhunter.jobhunter.domain.dto.ResUpdateUserDTO;
import vn.jobhunter.jobhunter.domain.dto.ResUserDTO;
import vn.jobhunter.jobhunter.domain.dto.ResultPaginationDTO;
import vn.jobhunter.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User handleGetUser(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public ResultPaginationDTO handleGetAllUser(Specification<User> specification, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(specification, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);
        List<User> listUsers = pageUser.getContent();
        rs.setResult(this.convertToListUserDTO(listUsers));
        return rs;
    }

    public User handleUpdateUser(User user) {
        User currentUser = this.handleGetUser(user.getId());
        if (currentUser != null) {
            currentUser.setName(user.getName());
            currentUser.setGender(user.getGender());
            currentUser.setAddress(user.getAddress());
            currentUser.setAge(user.getAge());

            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User handleGetUserByUserName(String username) {
        return this.userRepository.findByEmail(username);
    }

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user) {
        ResCreateUserDTO res = new ResCreateUserDTO();
        res.setAddress(user.getAddress());
        res.setAge(user.getAge());
        res.setCreatedAt(user.getCreatedAt());
        res.setEmail(user.getEmail());
        res.setGenderEnum(user.getGender());
        res.setId(user.getId());
        res.setName(user.getName());

        return res;
    }

    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO res = new ResUserDTO();
        res.setAddress(user.getAddress());
        res.setAge(user.getAge());
        res.setCreatedAt(user.getCreatedAt());
        res.setEmail(user.getEmail());
        res.setGenderEnum(user.getGender());
        res.setId(user.getId());
        res.setName(user.getName());
        res.setCreatedBy(user.getCreatedBy());

        return res;
    }

    public List<ResUserDTO> convertToListUserDTO(List<User> listUsers) {
        List<ResUserDTO> listUserDTOs = new ArrayList<>();
        for (User user : listUsers) {
            ResUserDTO res = convertToResUserDTO(user);
            listUserDTOs.add(res);
        }
        return listUserDTOs;
    }

    public ResUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResUpdateUserDTO res = new ResUpdateUserDTO();
        res.setAddress(user.getAddress());
        res.setAge(user.getAge());
        res.setGender(user.getGender());
        res.setName(user.getName());
        res.setId(user.getId());
        res.setUpdatedAt(user.getUpdatedAt());
        return res;
    }

}
