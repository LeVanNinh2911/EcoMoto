package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.entity.enums.ExchangeStatus;
import com.example.EcoMoto.dto.exchange.ExchangeRequestDto;
import com.example.EcoMoto.entity.*;
import com.example.EcoMoto.repository.*;
import com.example.EcoMoto.service.service.ExchangeRequestService;
import com.example.EcoMoto.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRequestServiceImpl implements ExchangeRequestService {

    @Autowired
    private ExchangeRequestRepository exchangeRequestRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ExchangeRequest createExchangeRequest(String token, ExchangeRequestDto dto) {
        // Lấy username từ token
        String username = jwtUtils.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // Lấy customer tương ứng user
        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin khách hàng"));

        // Kiểm tra chi nhánh tồn tại
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi nhánh"));

        // Tạo yêu cầu đổi xe
        ExchangeRequest request = new ExchangeRequest();
        request.setCustomer(customer);
        request.setBranch(branch);
        request.setRequestedTime(dto.getRequestedTime());
        request.setStatus(ExchangeStatus.PENDING);
        request.setNote(dto.getNote());

        return exchangeRequestRepository.save(request);
    }

    @Override
    public List<ExchangeRequest> getMyRequests(String token) {
        String username = jwtUtils.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin khách hàng"));

        return exchangeRequestRepository.findByCustomerId(customer.getId());
    }

    @Override
    public List<ExchangeRequest> getAllRequests() {
        return exchangeRequestRepository.findAll();
    }

    @Override
    public ExchangeRequest updateStatus(Long id, ExchangeStatus status) {
        ExchangeRequest request = exchangeRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu đổi xe"));

        request.setStatus(status);
        return exchangeRequestRepository.save(request);
    }
}
