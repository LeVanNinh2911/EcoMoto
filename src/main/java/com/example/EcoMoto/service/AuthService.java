package com.example.EcoMoto.service;


import com.example.EcoMoto.dto.auth.JwtResponse;
import com.example.EcoMoto.dto.auth.LoginRequest;
import com.example.EcoMoto.dto.auth.RegisterRequest;
import com.example.EcoMoto.entity.Role;
import com.example.EcoMoto.entity.User;
import com.example.EcoMoto.entity.Customer;
import com.example.EcoMoto.repository.RoleRepository;
import com.example.EcoMoto.repository.UserRepository;
import com.example.EcoMoto.repository.CustomerRepository;
import com.example.EcoMoto.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER không tồn tại"));

        // Tạo user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(userRole);
        userRepository.save(user);

        // Tạo customer gắn với user
        Customer customer = new Customer();
        customer.setUser(user);
        customer.setName(request.getUsername());
        customer.setPhone("");
        customer.setAddress("");
        customer.setEmail(request.getEmail());
        customerRepository.save(customer);
        return "Đăng ký thành công ";
    }

    public JwtResponse login(LoginRequest request) {
        // Cho phép login bằng username hoặc email
        User user = userRepository.findByUsername(request.getUsernameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(request.getUsernameOrEmail())
                        .orElseThrow(() -> new RuntimeException("Tài khoản không hợp lệ")));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        String token = jwtUtils.generateToken(user.getUsername(),user.getRole().getName());

        return new JwtResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getName()
        );
    }
}

