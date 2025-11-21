package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.config.SecurityConfig;
import com.example.EcoMoto.entity.User;
import com.example.EcoMoto.entity.enums.Status;
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
    @Autowired
    SecurityConfig securityConfig;

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
    public User updateUser(Long id, User updatedUser) {
        // 1️⃣ Tìm user hiện có theo ID
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // 2️⃣ Cập nhật các thông tin cần thiết
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(securityConfig.passwordEncoder().encode(updatedUser.getPassword()));
        }
        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }
        if (updatedUser.getStatus() != null) {
            existingUser.setStatus(updatedUser.getStatus());
        }

        // 3️⃣ Lưu lại vào DB
        return userRepository.save(existingUser);
    }
    @Override
    public User createUser(User user) {
        // 1️⃣ Kiểm tra trùng username hoặc email
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // 2️⃣ Mã hoá mật khẩu
        user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));

        // 3️⃣ Thiết lập role mặc định nếu chưa có
        if (user.getRole() == null) {
            user.setRole(roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found")));
        }

        // 4️⃣ Thiết lập trạng thái mặc định (nếu có cột status)
        if (user.getStatus() == null) {
            user.setStatus(Status.ACTIVE);
        }
        // 5️⃣ Lưu user vào DB
        return userRepository.save(user);
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

