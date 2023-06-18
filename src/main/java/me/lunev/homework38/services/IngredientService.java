package me.lunev.homework38.services;

import me.lunev.homework38.model.Ingredient;

import java.util.Map;
import java.util.Optional;

/**
 * Service class interface IngredientServiceImpl containing a set of CRUD operations on an ingredient object
 */
public interface IngredientService {

    Ingredient addIngredient(Ingredient ingredient);

    Optional<Ingredient> getIngredient(int id);

    Optional<Ingredient> editIngredient(int id, Ingredient ingredient);

    Optional<Ingredient> deleteIngredient(int id);

    Map<Integer, Ingredient> getAllIngredients();
}
