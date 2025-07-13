package ynu.elm.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ynu.elm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    @Cacheable(value = "users", key = "#userId", unless = "#result == null")
    Optional<User> getUserByIdByPass(String userId, String password);

    @Cacheable(value = "usersAll", unless = "#result == null or #result.empty")
    List<User> getAllUsers();

    @Cacheable(value = "usersExist", key = "#userId")
    boolean isUserExist(String userId);

    @CacheEvict(value = {"users", "usersExist", "usersAll"}, allEntries = true)
    User saveUser(User user);
}