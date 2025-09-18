package com.example.EcoMoto.dto.compare;


import com.example.EcoMoto.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

public class CompareResponse {
    private List<Product> products;
    private LocalDateTime comparedAt;
}
