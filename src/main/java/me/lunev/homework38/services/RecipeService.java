package me.lunev.homework38.services;

import me.lunev.homework38.model.Recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Service class interface IngredientServiceImpl containing a set of CRUD operations on a recipe object
 */
public interface RecipeService {

    Recipe addRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    List<Recipe> getRecipeOfIdIng(int idIng);

    List<Recipe> getRecipeOfIdsIng(Integer... idsIng);

    Recipe editRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);

    Map<Integer, Recipe> getAllRecipes();

    void readFromFile();

    List<Recipe> getRecipeOfPage(int pageNumber);

    Path createAllRecipesReport() throws IOException;
}
