package com.example.education_system.cart;


import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO implements Serializable {
    private List<CartItemResponseDTO> items = new ArrayList<>();
}