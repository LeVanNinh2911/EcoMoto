package com.example.EcoMoto.controller;

import com.example.EcoMoto.dto.category.CategoryDTO;
import com.example.EcoMoto.entity.Category;
import com.example.EcoMoto.service.Impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // ✅ Ai cũng có thể xem danh mục
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // ✅ Ai cũng có thể xem chi tiết 1 danh mục
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔐 Chỉ ADMIN mới được thêm
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    // 🔐 Chỉ ADMIN mới được sửa
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    // 🔐 Chỉ ADMIN mới được xóa
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
