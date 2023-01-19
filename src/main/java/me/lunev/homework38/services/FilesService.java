package me.lunev.homework38.services;

import java.io.File;

public interface FilesService {

    boolean saveIngredientToFile(String json);

    boolean saveRecipeToFile(String json);

    String readFromIngredientsFile();

    String readFromRecipesFile();

    boolean cleanRecipesFile();

    boolean cleanIngredientsFile();

    File getIngredientsFile();

    File getRecipesFile();
}
