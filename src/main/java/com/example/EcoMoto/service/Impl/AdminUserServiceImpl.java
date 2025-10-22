package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.entity.Role;
import com.example.EcoMoto.entity.User;
import com.example.EcoMoto.repository.RoleRepository;
import com.example.EcoMoto.repository.UserRepository;
import com.example.EcoMoto.service.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUserRole(Long id, String role) {
        User user = getUserById(id);
        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy quyền USER trong hệ thống"));
        user.setRole(userRole);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

