package ynu.elm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ynu.elm.entity.Admin;
import ynu.elm.repository.AdminRepository;
import ynu.elm.service.AdminService;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Optional<Admin> getAdminByIdByPass(String adminId, String password) {
        return adminRepository.findByAdminIdAndAdminPasswordAndDelTag(adminId, password, 1);
    }

    @Override
    public boolean isAdminExist(String adminId) {
        return adminRepository.existsByAdminIdAndDelTag(adminId, 1);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
}