package me.lunev.homework38.services.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.lunev.homework38.model.Ingredient;
import me.lunev.homework38.model.Recipe;
import me.lunev.homework38.services.FilesService;
import me.lunev.homework38.services.RecipeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.lunev.homework38.services.Impl.IngredientServiceImpl.idIng;
import static me.lunev.homework38.services.Impl.IngredientServiceImpl.ingredients;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final FilesService filesService;
    private static Map<Integer, Recipe> recipes = new HashMap<>();
    private static int id = 1;

    public RecipeServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            if (!ingredients.containsValue(recipe.getIngredients().get(i))) {
                ingredients.put(idIng++,recipe.getIngredients().get(i));
            }
        }
        recipes.put(id++,recipe);
        saveToFile();
        return recipe;
    }

    @Override
    public Recipe getRecipe(int id) {
        if (recipes.containsKey(id)) {
            return recipes.get(id);
        }
        return null;
    }

    @Override
    public List<Recipe> getRecipeOfIdIng(int idIng) {
        Ingredient ingredient = ingredients.get(idIng);
        List<Recipe> recipesList = new ArrayList<>();
        for (Recipe recipe : recipes.values()) {
            if (recipe.getIngredients().contains(ingredient)) {
                recipesList.add(recipe);
            }
        }
        return recipesList;
    }

    @Override
    public List<Recipe> getRecipeOfIdsIng(Integer... idsIng) {
        List<Ingredient> ingredientList = new ArrayList<>();
        List<Recipe> recipesList = new ArrayList<>();
        for (int i = 0; i < idsIng.length; i++) {
            ingredientList.add(ingredients.get(idsIng[i]));
        }
        for (Recipe recipe : recipes.values()) {
            if (recipe.getIngredients().containsAll(ingredientList)) {
                recipesList.add(recipe);
            }
        }
        return recipesList;
    }

    @Override
    public Recipe editRecipe(int id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, Recipe> getAllRecipes() {
        return recipes;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            String jsonIng = new ObjectMapper().writeValueAsString(ingredients);
            filesService.saveRecipeToFile(json);
            filesService.saveIngredientToFile(jsonIng);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readFromFile() {
        try {
            String json = filesService.readFromRecipesFile();
            String jsonIng = filesService.readFromIngredientsFile();
            recipes = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Recipe>>(){});
            ingredients = new ObjectMapper().readValue(jsonIng, new TypeReference<Map<Integer, Ingredient>>(){});
            for (Recipe recipe : recipes.values()) {
                for (int i = 0; i < recipe.getIngredients().size(); i++) {
                    if (!ingredients.containsValue(recipe.getIngredients().get(i))) {
                        ingredients.put(idIng++,recipe.getIngredients().get(i));
                    }
                }
            }
            jsonIng = new ObjectMapper().writeValueAsString(ingredients);
            filesService.saveIngredientToFile(jsonIng);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Recipe> getRecipeOfPage(int pageNumber) {
        List<Recipe> recipesList = new ArrayList<>();
        for (Integer idRecipe : recipes.keySet()) {
            if (idRecipe > (pageNumber * 10 - 10) && idRecipe <= pageNumber * 10) {
                recipesList.add(recipes.get(idRecipe));
            }
        }
        return recipesList;
    }

    @Override
    public Path createAllRecipesReport() throws IOException {
        Path path = filesService.createTempFiles("allRecipesReport");
        for (Map.Entry<Integer, Recipe> recipe : recipes.entrySet()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append("Рецепт № " + recipe.getKey() + "\n" + recipe.getValue().getName() +
                        "\nВремя приготовления: " + recipe.getValue().getCookingTime() + " минут." +
                        "\nИнгредиенты:");
                for (Ingredient ingredient : recipe.getValue().getIngredients()) {
                    writer.append(ingredient.toString());
                }
                writer.append("\nИнструкция приготовления:\n");
                int i = 1;
                for (String cookingStep : recipe.getValue().getCookingSteps()) {
                    writer.append(i++ + " " + cookingStep + "\n");
                }
                writer.append("\n");
            }
        }
        return path;
    }
}
