package com.example.demo.controller;

import com.example.demo.entity.Recipe;
import com.example.demo.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/import")
    public String importData() {
        recipeService.importRecipesFromLocalFile("src/main/resources/recipes.json");
        return "Data imported";
    }


    @GetMapping
    public Map<String, Object> getAllRecipes(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int limit) {
        Page<Recipe> recipePage = recipeService.getRecipes(page, limit);
        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("limit", limit);
        response.put("total", recipePage.getTotalElements());
        response.put("data", recipePage.getContent());
        return response;
    }

    // GET endpoint to read recipes directly from recipes.json
    @GetMapping("/from-file")
    public List<JsonNode> getRecipesFromFile() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("recipes.json")) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(is);
            if (root.isArray()) {
                return mapper.convertValue(root, new com.fasterxml.jackson.core.type.TypeReference<List<JsonNode>>() {});
            } else {
                return List.of(root);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read recipes.json", e);
        }
    }

    @GetMapping("/search")
    public List<Recipe> searchRecipes(@RequestParam(required = false) String title,
                                      @RequestParam(required = false) String cuisine,
                                      @RequestParam(required = false) Float rating,
                                      @RequestParam(required = false, name = "total_time") Integer totalTime,
                                      @RequestParam(required = false) Integer calories) {
        return recipeService.searchRecipes(title, cuisine, rating, totalTime, calories);
    }

    // POST endpoint to add a new recipe
    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        Recipe saved = recipeService.saveRecipe(recipe);
        return ResponseEntity.ok(saved);
    }

}
