package me.lunev.homework38.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * The class describes a Recipe
 *
 * @see Ingredient
 */
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Recipe {

    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private List<String> cookingSteps;
    private static int number = 1;

    public Recipe(String name, int cookingTime, List<Ingredient> ingredients, List<String> cookingSteps) {
        setName(name);
        setCookingTime(cookingTime);
        this.ingredients = ingredients;
        this.cookingSteps = cookingSteps;
    }

    public void setName(String name) {
        if (StringUtils.isBlank(name)) {
            name = "Рецепт " + number++;
        }
        this.name = name;
    }

    public void setCookingTime(int cookingTime) {
        if (cookingTime <= 0) {
            cookingTime = 15;
        }
        this.cookingTime = cookingTime;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setCookingSteps(List<String> cookingSteps) {
        this.cookingSteps = cookingSteps;
    }

    @Override
    public String toString() {
        return name +
                "\nВремя приготовления: " + cookingTime + " минут." +
                "\nИнгредиенты:\n" + ingredients +
                "\nИнструкция приготовления:\n" + cookingSteps;
    }
}
