package com.example.demo.util;

import com.example.demo.entity.Recipe;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToRecipeMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Recipe map(JsonNode node) {
        Recipe recipe = new Recipe();

        recipe.setCuisine(getText(node, "cuisine"));
        recipe.setTitle(getText(node, "title"));
        recipe.setRating(getFloat(node, "rating"));
        recipe.setPrepTime(getInt(node, "prep_time"));
        recipe.setCookTime(getInt(node, "cook_time"));
        recipe.setTotalTime(getInt(node, "total_time"));
        recipe.setDescription(getText(node, "description"));
        recipe.setServes(getText(node, "serves"));

        try {
            recipe.setNutrients(objectMapper.writeValueAsString(node.get("nutrients")));
        } catch (Exception e) {
            recipe.setNutrients(null);
        }

        return recipe;
    }

    private static String getText(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }

    private static Float getFloat(JsonNode node, String field) {
        try {
            String val = node.get(field).asText();
            return val.equalsIgnoreCase("NaN") ? null : Float.parseFloat(val);
        } catch (Exception e) {
            return null;
        }
    }

    private static Integer getInt(JsonNode node, String field) {
        try {
            String val = node.get(field).asText();
            return val.equalsIgnoreCase("NaN") ? null : Integer.parseInt(val);
        } catch (Exception e) {
            return null;
        }
    }
}
