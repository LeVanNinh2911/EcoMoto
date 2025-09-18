package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.dto.order.*;
import com.example.EcoMoto.entity.*;
import com.example.EcoMoto.repository.*;
import com.example.EcoMoto.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    private  CustomerRepository customerRepository;
    @Autowired
    private  ProductRepository productRepository;

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

        // Tính tiền đặt cọc (15%)
        BigDecimal deposit = total.multiply(BigDecimal.valueOf(0.15));

        // Tạo đơn hàng
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(total);
        order.setDepositAmount(deposit);
        order.setStatus("PENDING");
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus("UNPAID");
        order.setAddress(request.getAddress());


        // Thêm chi tiết đơn hàng
        // Thêm chi tiết đơn hàng
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

        // Giả lập tạo link thanh toán VNPay
        String paymentUrl = null;
        if ("VNPAY".equalsIgnoreCase(request.getPaymentMethod())) {
            paymentUrl = "https://sandbox.vnpay.vn/payment?orderId=" + order.getId()
                    + "&amount=" + deposit;
        }

        // Chuẩn bị response
        OrderResponseDto response = new OrderResponseDto();
        response.setOrderId(order.getId());
        response.setTotalAmount(total);
        response.setDepositAmount(deposit);
        response.setStatus(order.getStatus());
        response.setPaymentUrl(paymentUrl);
        response.setItems(request.getItems());

        return response;

    }

    @Override
    public OrderResponseDto placeGuestOrder(GuestOrderRequestDto request) {
        // 1. Tạo customer mới (không gắn user)
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customerRepository.save(customer);

        // 2. Tính toán tiền
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemDto item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        BigDecimal deposit = total.multiply(BigDecimal.valueOf(0.15));

        // 3. Tạo order
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(total);
        order.setDepositAmount(deposit);
        order.setStatus("PENDING");
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus("UNPAID");
        order.setAddress(request.getAddress());

        // 4. Thêm chi tiết order
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

        // 5. Trả về kết quả
        return new OrderResponseDto(
                order.getId(),
                total,
                deposit,
                order.getStatus(),
                null, // paymentUrl có thể tích hợp sau
                request.getItems()
        );
    }

}

