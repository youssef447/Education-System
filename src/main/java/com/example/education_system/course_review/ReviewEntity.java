package com.example.education_system.course_review;

import com.example.ericka_j_products.Entity.Product;
import com.example.ericka_j_products.Entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Product product;
    @Min(value = 0, message = "rating cannot be less than 0")
    @Max(value = 5, message = "rating cannot be more than 5")
    private int rate = 0;

    private String comment;

    boolean approved = false;


}
