package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


private String cuisine;
private String title;
private Float rating;
private Integer prepTime;
private Integer cookTime;
private Integer totalTime;


@Column(columnDefinition = "TEXT")//jpa how to map this filed to db
private String description;


@Lob
@Column(columnDefinition = "LONGTEXT")
private String nutrients; // Stored as JSON string


private String serves;
}