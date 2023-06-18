package me.lunev.homework38.services.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.lunev.homework38.model.Ingredient;
import me.lunev.homework38.services.FilesService;
import me.lunev.homework38.services.IngredientService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The service class containing the implementation of the interface {@link IngredientService}
 *
 * @see FilesService
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    private final FilesService filesService;
    protected static Map<Integer, Ingredient> ingredients = new HashMap<>();
    protected static int idIng = 1;

    public IngredientServiceImpl(FilesService filesService) {
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
    public Ingredient addIngredient(Ingredient ingredient) {
        ingredients.put(idIng++, ingredient);
        saveToFile();
        return ingredient;
    }

    @Override
    public Optional<Ingredient> getIngredient(int id) {
        return Optional.ofNullable(ingredients.get(id));
    }

    @Override
    public Optional<Ingredient> editIngredient(int id, Ingredient ingredient) {
        Optional<Ingredient> Optional = java.util.Optional.ofNullable(ingredients.replace(id, ingredient));
        saveToFile();
        return Optional;
    }

    @Override
    public Optional<Ingredient> deleteIngredient(int id) {
        Optional<Ingredient> Optional = java.util.Optional.ofNullable(ingredients.remove(id));
        saveToFile();
        return Optional;
    }

    @Override
    public Map<Integer, Ingredient> getAllIngredients() {
        return ingredients;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesService.saveIngredientToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromIngredientsFile();
            ingredients = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
