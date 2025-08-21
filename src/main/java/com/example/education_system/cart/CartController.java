package com.example.education_system.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartDTO getCart() {
        return cartService.getCart();
    }

    @PostMapping
    public void addToCart(@RequestBody CartItemRequestDTO item) {
        cartService.addToCart(item);
    }

    @DeleteMapping
    public void clearCart() {
        cartService.clearCart();
    }
}
