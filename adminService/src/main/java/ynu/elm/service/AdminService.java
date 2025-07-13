package ynu.elm.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ynu.elm.entity.Admin;

import java.util.Optional;

public interface AdminService {

    @Cacheable(value = "admins", key = "#adminId", unless = "#result == null")
    Optional<Admin> getAdminByIdByPass(String adminId, String password);

    @Cacheable(value = "adminExist", key = "#adminId")
    boolean isAdminExist(String adminId);

    @CacheEvict(value = {"admins", "adminExist"}, allEntries = true)
    Admin saveAdmin(Admin admin);
}