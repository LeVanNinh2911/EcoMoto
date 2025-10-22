package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.dto.order.*;
import com.example.EcoMoto.entity.*;
import com.example.EcoMoto.entity.enums.PaymentStatus;
import com.example.EcoMoto.entity.enums.PaymentMethod;
import com.example.EcoMoto.repository.*;
import com.example.EcoMoto.service.service.OrderService;
import com.example.EcoMoto.util.VNPayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    // ==========================================================
    // 🧾 ĐẶT HÀNG CHO NGƯỜI DÙNG ĐÃ ĐĂNG NHẬP
    // ==========================================================
    @Override
    public OrderResponseDto placeOrder(Long userId, OrderRequestDto request) {
        // 1️⃣ Lấy hoặc tạo Customer
        Customer customer = (Customer) customerRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Customer c = new Customer();
                    c.setName(request.getName());
                    c.setEmail(request.getEmail());
                    c.setPhone(request.getPhone());
                    c.setAddress(request.getAddress());
                    return customerRepository.save(c);
                });

        // 2️⃣ Tính tổng tiền
        BigDecimal total = request.getItems().stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    return product.getFinalPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal deposit = total.multiply(BigDecimal.valueOf(0.15));

        // 3️⃣ Tạo đơn hàng
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(total);
        order.setDepositAmount(deposit);
        order.setAddress(request.getAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus("PENDING");

        // 4️⃣ Xử lý thanh toán
        String paymentUrl = handleInstallmentPayment(order, deposit, request.getInstallmentDownPaymentPercent());


        // 5️⃣ Chi tiết đơn hàng
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

        // 6️⃣ Kết quả trả về
        return new OrderResponseDto(
                order.getId(),
                total,
                deposit,
                order.getStatus(),
                paymentUrl,
                request.getItems()
        );
    }

    // ==========================================================
    // 👤 ĐẶT HÀNG KHÁCH VÃNG LAI (KHÔNG CẦN ĐĂNG NHẬP)
    // ==========================================================
    @Override
    public OrderResponseDto placeGuestOrder(GuestOrderRequestDto request) {
        // 1️⃣ Tạo khách hàng mới
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customerRepository.save(customer);

        // 2️⃣ Tính tổng tiền
        BigDecimal total = request.getItems().stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    return product.getFinalPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal deposit = total.multiply(BigDecimal.valueOf(0.15));

        // 3️⃣ Tạo đơn hàng
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(total);
        order.setDepositAmount(deposit);
        order.setAddress(request.getAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus("PENDING");

        // 4️⃣ Xử lý thanh toán
        String paymentUrl = handleInstallmentPayment(order, deposit, request.getInstallmentDownPaymentPercent());


        // 5️⃣ Chi tiết đơn hàng
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

        // 6️⃣ Kết quả trả về
        return new OrderResponseDto(
                order.getId(),
                total,
                deposit,
                order.getStatus(),
                paymentUrl,
                request.getItems()
        );
    }

    // 💳 HÀM XỬ LÝ CÁC KIỂU THANH TOÁN
    private String handleInstallmentPayment(Order order, BigDecimal deposit, Double downPercent) {
        String paymentUrl = null;

        switch (order.getPaymentMethod()) {
            case BANK_TRANSFER:
                paymentUrl = VNPayUtil.createPaymentUrl(
                        UUID.randomUUID().toString(),
                        order.getTotalAmount().longValue(),
                        vnpTmnCode, vnpHashSecret, vnpReturnUrl, vnpPayUrl
                );
                order.setPaymentStatus(PaymentStatus.AWAITING_BANK_TRANSFER);
                break;

            case COD:
                paymentUrl = VNPayUtil.createPaymentUrl(
                        UUID.randomUUID().toString(),
                        deposit.longValue(),
                        vnpTmnCode, vnpHashSecret, vnpReturnUrl, vnpPayUrl
                );
                order.setPaymentStatus(PaymentStatus.DEPOSIT_PENDING);
                break;

            case INSTALLMENT:
                double percent = (downPercent != null && downPercent > 0) ? downPercent : 0.15;

                // Tính tiền trả trước chính xác
                BigDecimal downPayment = order.getTotalAmount()
                        .multiply(BigDecimal.valueOf(percent))
                        .setScale(0, RoundingMode.UP);

                order.setDepositAmount(downPayment);
                order.setPaymentStatus(PaymentStatus.INSTALLMENT_PENDING);

                // Chia 100 để VNPay hiển thị đúng
                long vnpAmount = downPayment.divide(BigDecimal.valueOf(100)).longValue();

                paymentUrl = VNPayUtil.createPaymentUrl(
                        UUID.randomUUID().toString(),
                        vnpAmount,
                        vnpTmnCode, vnpHashSecret, vnpReturnUrl, vnpPayUrl
                );
                break;

            default:
                throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ: " + order.getPaymentMethod());
        }

        orderRepository.save(order);
        return paymentUrl;
    }


}
