package com.example.demo.spec;

import com.example.demo.entity.Recipe;
import org.springframework.data.jpa.domain.Specification;

public class RecipeSpecifications {

    public static Specification<Recipe> titleContains(String title) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Recipe> cuisineEquals(String cuisine) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get("cuisine")), cuisine.toLowerCase());
    }

    public static Specification<Recipe> ratingGreaterThanOrEqual(Float rating) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("rating"), rating);
    }

    public static Specification<Recipe> totalTimeLessThanOrEqual(Integer totalTime) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("totalTime"), totalTime);
    }
}
