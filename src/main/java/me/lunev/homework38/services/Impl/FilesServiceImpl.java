package me.lunev.homework38.services.Impl;

import me.lunev.homework38.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Value("${path.to.ingredients.file}")
    private String ingredientsFilePath;
    @Value("${path.to.recipes.file}")
    private String recipesFilePath;
    @Value("${name.of.ingredients.file}")
    private String ingredientsFileName;
    @Value("${name.of.recipes.file}")
    private String recipesFileName;

    @Override
    public boolean saveIngredientToFile(String json) {
        return saveDataToFile(json, ingredientsFilePath, ingredientsFileName);
    }

    @Override
    public boolean saveRecipeToFile(String json) {
        return saveDataToFile(json, recipesFilePath, recipesFileName);
    }

    @Override
    public String readFromIngredientsFile() {
        return readFromDataFile(ingredientsFilePath, ingredientsFileName);
    }

    @Override
    public String readFromRecipesFile() {
        return readFromDataFile(recipesFilePath, recipesFileName);
    }

    @Override
    public File getIngredientsFile() {
        return getDataFile(ingredientsFilePath, ingredientsFileName);
    }

    @Override
    public File getRecipesFile() {
        return getDataFile(recipesFilePath, recipesFileName);
    }


    @Override
    public boolean cleanRecipesFile() {
        return cleanDataFile(recipesFilePath, recipesFileName);
    }

    @Override
    public boolean cleanIngredientsFile() {
        return cleanDataFile(ingredientsFilePath, ingredientsFileName);
    }

    private boolean saveDataToFile(String json, String  filePath, String  fileName) {
        try {
            cleanDataFile(filePath, fileName);
            Files.writeString(Path.of(filePath, fileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String readFromDataFile(String filePath, String fileName) {
        try {
            Path path = Path.of(filePath, fileName);
            if (!Files.exists(path)) {
                Files.createFile(path);
                Files.writeString(path,"{}");
            }
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean cleanDataFile(String filePath, String fileName) {
        try {
            Path path = Path.of(filePath, fileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public File getDataFile(String filePath, String fileName) {
        return new File(filePath + "/" + fileName);
    }

    @Override
    public Path createTempFiles(String suffix) {
        try {
            return Files.createTempFile(Path.of(recipesFilePath), "tempFile", suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
