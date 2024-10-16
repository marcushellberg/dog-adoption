package com.example.service.adoptions;

import org.springframework.data.annotation.Id;

public record Dog(@Id int id, String name, String description, String owner, String image) {
}
