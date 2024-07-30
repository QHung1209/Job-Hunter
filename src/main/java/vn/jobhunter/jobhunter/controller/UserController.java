package vn.jobhunter.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.jobhunter.jobhunter.domain.RestResponse;
import vn.jobhunter.jobhunter.domain.User;
import vn.jobhunter.jobhunter.service.UserService;
import vn.jobhunter.jobhunter.util.error.IdInvalidException;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User postManUser) {
        postManUser.setPassword(this.passwordEncoder.encode(postManUser.getPassword()));
        User Hung = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(Hung);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<RestResponse<String>> deleteUser(@PathVariable("id") long id) throws IdInvalidException{
        if(id >=1500)
        {
            throw new IdInvalidException("Id khong lon hon 1500");
        }

        this.userService.handleDeleteUser(id);
        RestResponse<String> res = new RestResponse<>();
        res.setData("User deleted successfully");
        res.setMessage("Success");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        return ResponseEntity.ok(this.userService.handleGetUser(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(this.userService.handleGetAllUser());
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User postmanUser) {
        return ResponseEntity.ok(this.userService.handleUpdateUser(postmanUser));
    }

}
