package vn.jobhunter.jobhunter.service;

import java.util.Optional;
import java.util.List;
import org.springframework.stereotype.Service;

import vn.jobhunter.jobhunter.domain.User;
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

    public List<User> handleGetAllUser() {
        return this.userRepository.findAll();
    }

    public User handleUpdateUser(User user) {
        User currentUser = this.handleGetUser(user.getId());
        if (currentUser != null) {
            currentUser.setEmail(user.getEmail());
            currentUser.setName(user.getName());
            currentUser.setPassword(user.getPassword());

            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }
    public User handleGetUserByUserName(String username)
    {
        return this.userRepository.findByEmail(username);
    }

}
