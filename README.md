# Securin

This project is built with Spring Boot where I used REST APIs to manage recipes. I connected it to MySQL using JPA, handled JSON parsing with Jackson, and exposed endpoints with @RestController. I also implemented pagination, sorting, and filtering. Swagger UI was used for API documentation and testing.

GET /api/recipes: Returns paginated recipes from the database.
GET /api/recipes/search: Returns filtered recipes based on query parameters.
POST /api/recipes: Adds a new recipe to the database.
POST /api/recipes/import: Imports recipes from a local JSON file (recipes.json).
GET /api/recipes/from-file: Reads recipes directly from the JSON file (not the database).




1. Entity: Recipe.java
Defines the structure of a recipe in the database. Each field (like title, cuisine, rating, etc.) is a column in the database table.

2. Repository: RecipeRepository.java
Handles database operations for recipes. It uses Spring Data JPA to provide methods like findAll(), save(), etc. No custom code needed—Spring generates everything.

3. Service: RecipeService.java
Contains business logic for recipes:

getRecipes(page, limit): Returns a paginated list of recipes.
searchRecipes(...): Filters recipes based on parameters (title, cuisine, rating, etc.) using simple Java code.
saveRecipe(recipe): Saves a new recipe to the database.
importRecipesFromLocalFile(filePath): Reads a local JSON file, maps each recipe, and saves it to the database.
