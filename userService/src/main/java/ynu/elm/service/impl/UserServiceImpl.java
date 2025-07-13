package ynu.elm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ynu.elm.entity.User;
import ynu.elm.repository.UserRepository;
import ynu.elm.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getUserByIdByPass(String userId, String password) {
        return userRepository.findByUserIdAndPasswordAndDelTag(userId, password, 1);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean isUserExist(String userId) {
        return userRepository.existsByUserIdAndDelTag(userId, 1);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}