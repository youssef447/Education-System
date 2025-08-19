package com.example.education_system.order;

import com.example.education_system.config.response.ApiResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponseBody getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        var result = orderService.getAll(page, size);
        return new ApiResponseBody("Orders fetched successfully", result,true);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody get(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        var result = orderService.get(page, size);
        return new ApiResponseBody("Orders fetched successfully", result,true);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody add( @RequestBody OrderRequestDTO request) {
        var result = orderService.add(request);
        return new ApiResponseBody("Order added successfully", result,true);
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ApiResponseBody update(
            @PathVariable Long orderId,
           @RequestBody OrderRequestDTO request
    ) throws Exception {
        var result = orderService.update(orderId, request);
        return new ApiResponseBody("Order updated successfully", result,true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ApiResponseBody delete(@PathVariable Long id) {
        orderService.delete(id);
        return new ApiResponseBody("Order deleted successfully", true);
    }
}
