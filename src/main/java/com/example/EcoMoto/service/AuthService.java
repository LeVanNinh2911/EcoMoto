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
        // 2️⃣ Kiểm tra trùng email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        // 3️⃣ Lấy role USER
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy quyền USER trong hệ thống"));

        // 4️⃣ Tạo User
        User user = new User();
        user.setUsername(request.getName().trim());
        user.setEmail(request.getEmail().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(userRole);
        userRepository.save(user);

        // 5️⃣ Tạo Customer mặc định (gắn với user)
//        Customer customer = new Customer();
//        customer.setUser(user);
//        customer.setName(user.getUsername());          // để user điền sau
//        customer.setPhone(null);
//        customer.setAddress(null);
//        customer.setEmail(user.getEmail());
//        customerRepository.save(customer);

        return "Đăng ký thành công!";
    }


    public JwtResponse login(LoginRequest request) {
        // Cho phép login bằng username hoặc email
        User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Tài khoản không hợp lệ"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        String token = jwtUtils.generateToken(user.getEmail(),user.getRole().getName());

        return new JwtResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getName()
        );
    }
}

