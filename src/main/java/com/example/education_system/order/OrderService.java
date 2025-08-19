package com.example.education_system.order;

import com.example.education_system.config.exceptions.classes.CourseNotFoundException;
import com.example.education_system.config.exceptions.classes.OrderNotFoundException;
import com.example.education_system.course.entity.CourseEntity;
import com.example.education_system.course.repository.CourseRepository;
import com.example.education_system.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final CourseRepository courseRepository;
    private final UserService userService;


    Page<OrderResponseDTO> getAll(Integer page, Integer size) {
        if (page == null || size == null) {
            List<OrderResponseDTO> dtos = mapper.toListDTO(orderRepository.findAll());
            return new PageImpl<>(dtos);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        return orderRepository.findAll(pageable)
                .map(mapper::toResponseDto);
    }

    Page<OrderResponseDTO> get(
            Integer page, Integer size) {
        Long id = userService.getCurrentUser().getId();
        if (page == null || size == null) {
            List<OrderResponseDTO> dtos = mapper.toListDTO(orderRepository.findByUserId(id));
            return new PageImpl<>(dtos);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        return orderRepository.findByUserId(id, pageable)
                .map(mapper::toResponseDto);
    }


    OrderResponseDTO add(@Valid OrderRequestDTO request) {
        var user = userService.getCurrentUser();

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setCurrency(OrderEntity.CurrencyEnum.valueOf(request.getCurrency().toUpperCase()));

        // build order items
        for (var itemDto : request.getItems()) {
            CourseEntity course = courseRepository.findById(itemDto.getCourseId())
                    .orElseThrow(CourseNotFoundException::new);

            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setCourse(course);
            orderItem.setOrder(order);

            order.getItems().add(orderItem);
        }

        OrderEntity saved = orderRepository.save(order);
        return mapper.toResponseDto(saved);
    }


    OrderResponseDTO update(Long orderId, @Valid OrderRequestDTO request) throws AccessDeniedException {


        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        var user = userService.getCurrentUser();
        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You cannot update this order");
        }

        order.getItems().clear();

        for (var itemDto : request.getItems()) {
            CourseEntity course = courseRepository.findById(itemDto.getCourseId())
                    .orElseThrow(CourseNotFoundException::new);

            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setCourse(course);
            orderItem.setOrder(order);

            order.getItems().add(orderItem);
        }

        OrderEntity updated = orderRepository.save(order);
        return mapper.toResponseDto(updated);
    }

    void delete(Long id) {
        orderRepository.deleteById(id);
    }

}
