package com.example.education_system.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    @NotBlank(message = "currency is required")
    String currency;
    @NotEmpty
    List<OrderItemRequestDTO> items;
}
