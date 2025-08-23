package com.example.education_system.category;



import java.io.Serializable;


public record CategoryResponseDto(Long id,
                                  String name,
                                  String description,
                                  String iconUrl) implements Serializable {

}
