package com.example.education_system.order;


import com.example.education_system.config.response.ApiResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('USER')")
    ApiResponseBody add(@ModelAttribute OrderRequestDTO request
    ) {
        return new ApiResponseBody("order added successfully", true);
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ApiResponseBody update(@ModelAttribute OrderRequestDTO request
    ) {
        return new ApiResponseBody("order updated successfully", true);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ApiResponseBody delete(@PathVariable Long id) {
        return new ApiResponseBody("order deleted successfully", true);
    }

}
