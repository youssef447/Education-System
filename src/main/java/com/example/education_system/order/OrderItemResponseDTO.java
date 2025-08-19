package com.example.education_system.order;


import java.math.BigDecimal;


public record OrderItemResponseDTO(Long id,
                                   Long productId,
                                   String productName,
                                   BigDecimal unitPrice,
                                   int quantity, int totalPrice) {
}
