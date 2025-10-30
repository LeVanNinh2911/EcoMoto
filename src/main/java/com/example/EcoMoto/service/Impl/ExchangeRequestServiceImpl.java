package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.entity.enums.ExchangeStatus;
import com.example.EcoMoto.dto.exchange.ExchangeRequestDto;
import com.example.EcoMoto.dto.exchange.ExchangeResponseDto;
import com.example.EcoMoto.entity.*;
import com.example.EcoMoto.repository.*;
import com.example.EcoMoto.service.service.ExchangeRequestService;
import com.example.EcoMoto.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExchangeRequestServiceImpl implements ExchangeRequestService {

    @Autowired
    private ExchangeRequestRepository exchangeRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ExchangeResponseDto createExchangeRequest(String token, ExchangeRequestDto dto) {
        // Lấy username/email từ token
        String email = jwtUtils.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // Kiểm tra chi nhánh
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi nhánh"));

        // Tạo yêu cầu đổi xe
        ExchangeRequest request = new ExchangeRequest();
        request.setUser(user);
        request.setBranch(branch);
        request.setRequestedTime(dto.getRequestedTime());
        request.setStatus(ExchangeStatus.PENDING);
        request.setNote(dto.getNote());

        ExchangeRequest saved = exchangeRequestRepository.save(request);

        // Trả về DTO phản hồi (tránh vòng lặp)
        return mapToDto(saved);
    }

    @Override
    public List<ExchangeResponseDto> getMyRequests(String token) {
        String email = jwtUtils.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        List<ExchangeResponseDto> list = exchangeRequestRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        // ✅ Đảo ngược danh sách (để yêu cầu mới nhất ở đầu)
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<ExchangeResponseDto> getAllRequests() {
        List<ExchangeResponseDto> list = exchangeRequestRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        // ✅ Đảo ngược danh sách
        Collections.reverse(list);
        return list;
    }

    @Override
    public ExchangeResponseDto updateStatus(Long id, ExchangeStatus status) {
        ExchangeRequest request = exchangeRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu đổi xe"));

        request.setStatus(status);
        ExchangeRequest updated = exchangeRequestRepository.save(request);
        return mapToDto(updated);
    }

    // ====== Hàm private chuyển entity -> DTO phản hồi ======
    private ExchangeResponseDto mapToDto(ExchangeRequest entity) {
        ExchangeResponseDto dto = new ExchangeResponseDto();
        dto.setId(entity.getId());
        dto.setBranchName(entity.getBranch() != null ? entity.getBranch().getName() : null);
        dto.setUserEmail(entity.getUser() != null ? entity.getUser().getEmail() : null);
        dto.setRequestedTime(entity.getRequestedTime());
        dto.setStatus(entity.getStatus());
        dto.setNote(entity.getNote());
        return dto;
    }
}
