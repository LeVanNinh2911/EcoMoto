package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.dto.order.*;
import com.example.EcoMoto.entity.*;
import com.example.EcoMoto.repository.*;
import com.example.EcoMoto.service.service.OrderService;
import com.example.EcoMoto.util.VNPayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Value("${vnpay.tmn-code}")
    private String vnpTmnCode;

    @Value("${vnpay.hash-secret}")
    private String vnpHashSecret;

    @Value("${vnpay.return-url}")
    private String vnpReturnUrl;

    @Value("${vnpay.pay-url}")
    private String vnpPayUrl;

    @Override
    public OrderResponseDto placeOrder(Long userId, OrderRequestDto request) {
        // Lấy customer từ user (hoặc tạo mới nếu chưa có)
        Optional<Customer> optionalCustomer = customerRepository.findByUserId(userId);

        Customer customer;
        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
        } else {
            customer = new Customer();
            customer.setName(request.getName());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
            customer.setAddress(request.getAddress());
        }

        // Tính tổng tiền
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemDto item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        BigDecimal deposit = total.multiply(BigDecimal.valueOf(0.15));

        // Tạo order
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(total);
        order.setDepositAmount(deposit);
        order.setStatus("PENDING");
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus("UNPAID");
        order.setAddress(request.getAddress());

        // Thêm chi tiết order
        List<OrderDetail> details = request.getItems().stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    OrderDetail detail = new OrderDetail();
                    detail.setOrder(order);
                    detail.setProduct(product);
                    detail.setQuantity(item.getQuantity());
                    detail.setPrice(product.getPrice());
                    return detail;
                })
                .collect(Collectors.toList());
        order.setOrderDetails(details);

        orderRepository.save(order);

        // Tích hợp VNPay
        String paymentUrl = null;
        if ("VNPAY".equalsIgnoreCase(request.getPaymentMethod())) {
            paymentUrl = VNPayUtil.createPaymentUrl(
                    order.getId().toString(),
                    deposit.longValue(),
                    vnpTmnCode,
                    vnpHashSecret,
                    vnpReturnUrl,
                    vnpPayUrl
            );
        }

        return new OrderResponseDto(
                order.getId(),
                total,
                deposit,
                order.getStatus(),
                paymentUrl,
                request.getItems()
        );
    }

    @Override
    public OrderResponseDto placeGuestOrder(GuestOrderRequestDto request) {
        // Tạo customer mới
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customerRepository.save(customer);

        // Tính toán tiền
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemDto item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        BigDecimal deposit = total.multiply(BigDecimal.valueOf(0.15));

        // Tạo order
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(total);
        order.setDepositAmount(deposit);
        order.setStatus("PENDING");
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus("UNPAID");
        order.setAddress(request.getAddress());

        // Thêm chi tiết
        List<OrderDetail> details = request.getItems().stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    OrderDetail detail = new OrderDetail();
                    detail.setOrder(order);
                    detail.setProduct(product);
                    detail.setQuantity(item.getQuantity());
                    detail.setPrice(product.getPrice());
                    return detail;
                })
                .collect(Collectors.toList());
        order.setOrderDetails(details);

        orderRepository.save(order);

        // Tích hợp VNPay
        String paymentUrl = null;
        if ("VNPAY".equalsIgnoreCase(request.getPaymentMethod())) {
            paymentUrl = VNPayUtil.createPaymentUrl(
                    order.getId().toString(),
                    deposit.longValue(),
                    vnpTmnCode,
                    vnpHashSecret,
                    vnpReturnUrl,
                    vnpPayUrl
            );
        }

        return new OrderResponseDto(
                order.getId(),
                total,
                deposit,
                order.getStatus(),
                paymentUrl,
                request.getItems()
        );
    }
}
