package com.example.education_system.cart;

import com.example.education_system.config.exceptions.classes.CourseNotFoundException;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.course.mapper.CourseMapper;
import com.example.education_system.course.repository.CourseRepository;
import com.example.education_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserService userService;

    private final RedisTemplate<String, Object> redisTemplate;


    private String getKey() {
        Long userId = userService.getCurrentUser().getId();
        return "cart:" + userId;
    }

    public CartDTO getCart() {
        String key = getKey();
        CartDTO cartDTO = (CartDTO) redisTemplate.opsForValue().get(key);
        if (cartDTO == null) {
            cartDTO = new CartDTO(new ArrayList<>());
        }
        return cartDTO;
    }

    public void addToCart(CartItemRequestDTO item) {
        CartDTO cartDTO = getCart();

        // check if item already exists in the cart
        Optional<CartItemResponseDTO> existing = cartDTO.getItems().stream()
                .filter(i -> i.getCourse().id().equals(item.getCourseId()))
                .findFirst();

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Course already exists in cart");
        }

        CourseEntity course = courseRepository.findById(item.getCourseId())
                .orElseThrow(CourseNotFoundException::new);


        CartItemResponseDTO cartItem = new CartItemResponseDTO();
        cartItem.setCourse(courseMapper.toDto(course));
        cartItem.setThumbnailUrl(course.getThumbnailFile().url());
        cartItem.setUnitPrice(course.getPrice());
        cartItem.setQuantity(1);


        cartDTO.getItems().add(cartItem);

        redisTemplate.opsForValue().set(getKey(), cartDTO);
    }


    public void clearCart() {
        redisTemplate.delete(getKey());
    }
}
