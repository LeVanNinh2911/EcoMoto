package com.example.EcoMoto.dto.compare;

import java.util.List;

public class CompareRequest {
    private List<Long> productIds;

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}

