package com.example.education_system.order;


import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponseDTO(Long id,
                               int itemCount,
                               Long userId,
                               String status,
                               BigDecimal totalPrice,
                               String currency,
                               LocalDateTime createdDate,

                               List<OrderItemResponseDTO> items) {
}
