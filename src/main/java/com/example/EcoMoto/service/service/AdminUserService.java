package com.example.EcoMoto.service.service;

import com.example.EcoMoto.entity.Role;
import com.example.EcoMoto.entity.User;

import java.util.List;

public interface AdminUserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUserRole(Long id, String role);
    void deleteUser(Long id);
}
