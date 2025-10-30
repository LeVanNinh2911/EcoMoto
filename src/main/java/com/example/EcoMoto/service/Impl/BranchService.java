package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.entity.Branch;
import com.example.EcoMoto.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    public Branch saveBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public Branch updateBranch(Long id, Branch updatedBranch) {
        return branchRepository.findById(id)
                .map(branch -> {
                    branch.setName(updatedBranch.getName());
                    branch.setAddress(updatedBranch.getAddress());
                    branch.setPhone(updatedBranch.getPhone());
                    branch.setCity(updatedBranch.getCity());
                    branch.setFeatured(updatedBranch.getFeatured());
                    branch.setBrand(updatedBranch.getBrand());
                    return branchRepository.save(branch);
                })
                .orElseThrow(() -> new RuntimeException("Branch not found with id " + id));
    }

    public void deleteBranch(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new RuntimeException("Branch not found with id " + id);
        }
        branchRepository.deleteById(id);
    }
}
