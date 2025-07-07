package com.example.education_system.config.services;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "storage")
public class StorageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // The URL returned from Cloudinary
    @Column(nullable = false)
    private String url;

    // The public ID from Cloudinary (needed to delete any file later)
    @Column(nullable = false, unique = true)
    private String publicId;
    @Column(nullable = false)
    private String size;

}
