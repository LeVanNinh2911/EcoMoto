package com.example.EcoMoto.controller.admin;

import com.example.EcoMoto.entity.Branch;
import com.example.EcoMoto.service.Impl.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")

public class BranchController {

    @Autowired
    private BranchService branchService;

    // 🔹 Public: Lấy tất cả chi nhánh
    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    // 🔹 Public: Lấy chi nhánh theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        return branchService.getBranchById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔸 Admin: Thêm chi nhánh
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody Branch branch) {
        return ResponseEntity.ok(branchService.saveBranch(branch));
    }

    // 🔸 Admin: Cập nhật chi nhánh
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @RequestBody Branch branch) {
        return ResponseEntity.ok(branchService.updateBranch(id, branch));
    }

    // 🔸 Admin: Xóa chi nhánh
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
