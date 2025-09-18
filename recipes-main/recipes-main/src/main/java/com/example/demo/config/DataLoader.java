package com.example.demo.config;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.util.JsonToRecipeMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (recipeRepository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream("/recipes.json");
            JsonNode root = mapper.readTree(is);

            for (JsonNode node : root) {
                Recipe recipe = JsonToRecipeMapper.map(node);
                recipeRepository.save(recipe);
            }
        }
    }
}
