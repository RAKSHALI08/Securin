package com.example.demo.service;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Page<Recipe> getRecipes(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("rating").descending());
        return recipeRepository.findAll(pageable);
    }

    public List<Recipe> searchRecipes(String title, String cuisine, Float rating, Integer totalTime, Integer caloriesMax) {
        List<Recipe> results = recipeRepository.findAll();
        // Filter in Java for simplicity
        if (title != null) {
            results = results.stream().filter(r -> r.getTitle() != null && r.getTitle().toLowerCase().contains(title.toLowerCase())).collect(Collectors.toList());
        }
        if (cuisine != null) {
            results = results.stream().filter(r -> r.getCuisine() != null && r.getCuisine().equalsIgnoreCase(cuisine)).collect(Collectors.toList());
        }
        if (rating != null) {
            results = results.stream().filter(r -> r.getRating() != null && r.getRating() >= rating).collect(Collectors.toList());
        }
        if (totalTime != null) {
            results = results.stream().filter(r -> r.getTotalTime() != null && r.getTotalTime() <= totalTime).collect(Collectors.toList());
        }
        if (caloriesMax != null) {
            results = results.stream().filter(r -> {
                try {
                    JsonNode node = objectMapper.readTree(r.getNutrients());
                    String caloriesStr = node.get("calories").asText().replaceAll("[^0-9]", "");
                    int calories = Integer.parseInt(caloriesStr);
                    return calories <= caloriesMax;
                } catch (Exception e) {
                    return false;
                }
            }).collect(Collectors.toList());
        }
        return results;
    }
    // Save a recipe to the database
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
    // Import recipes from a local JSON file
    public int importRecipesFromLocalFile(String filePath) {
        int count = 0;
        try {
            ObjectMapper mapper = new ObjectMapper();
            java.io.File file = new java.io.File(filePath);
            JsonNode root = mapper.readTree(file);
            for (JsonNode node : root) {
                Recipe recipe = com.example.demo.util.JsonToRecipeMapper.map(node);
                recipeRepository.save(recipe);
                count++;
            }
        } catch (Exception e) {
            // Log error or handle as needed
        }
        return count;
    }
}
