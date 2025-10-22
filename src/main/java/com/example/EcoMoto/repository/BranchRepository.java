package com.example.EcoMoto.repository;

import com.example.EcoMoto.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    // Tìm tất cả chi nhánh theo thành phố (city)
    List<Branch> findByCityIgnoreCase(String city);

    // Tìm tất cả chi nhánh thuộc một brand cụ thể
    List<Branch> findByBrandId(Long brandId);

    // Tìm các chi nhánh được đánh dấu nổi bật
    List<Branch> findByIsFeaturedTrue();

    // Tìm theo tên có chứa keyword (phục vụ tìm kiếm)
    List<Branch> findByNameContainingIgnoreCase(String keyword);
}

