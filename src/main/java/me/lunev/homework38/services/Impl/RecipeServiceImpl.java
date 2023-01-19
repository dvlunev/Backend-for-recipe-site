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
        readFromFile();
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
    public Recipe getRecipeOfIdIng2(int idIng1, int idIng2) {
        Ingredient ingredient1 = ingredients.get(idIng1);
        Ingredient ingredient2 = ingredients.get(idIng2);
        for (Recipe recipe : recipes.values()) {
            if (recipe.getIngredients().contains(ingredient1) && recipe.getIngredients().contains(ingredient2)) {
                return recipe;
            }
        }
        return null;
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

    private void readFromFile() {
        try {
            String json = filesService.readFromRecipesFile();
            String json1 = filesService.readFromIngredientsFile();
            recipes = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Recipe>>(){});
            ingredients = new ObjectMapper().readValue(json1, new TypeReference<Map<Integer, Ingredient>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
