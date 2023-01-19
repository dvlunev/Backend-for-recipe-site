package me.lunev.homework38.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.lunev.homework38.model.Recipe;
import me.lunev.homework38.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "CRUD-операции и другие эндпоинты для работы с рецептами")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(
            summary = "Добавление рецепта и его ингредиентов",
            description = "Добавление нового рецепта и его ингредиентов из тела запроса с присвоением им id из генератора"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт и его ингредиенты были добавлены",
                    content = {
                            @Content(
                                    mediaType = "addlication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры рецепта"
            )
    }
    )
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(recipe);
    }

    @Operation(
            summary = "Поиск рецепта по id рецепта",
            description = "Можно искать по одному параметру"
    )
    @Parameters( value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был найден",
                    content = {
                            @Content(
                                    mediaType = "addlication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не был найден"
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @Operation(
            summary = "Поиск рецепта по id ингредиента",
            description = "Можно искать по одному параметру"
    )
    @Parameters( value = {
            @Parameter(name = "idIng", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был найден",
                    content = {
                            @Content(
                                    mediaType = "addlication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не был найден"
            )
    }
    )
    @GetMapping("/idIng/{idIng}")
    public ResponseEntity<List<Recipe>> getRecipeOfIdIng(@PathVariable int idIng) {
        List<Recipe> recipesList = new ArrayList<>(recipeService.getRecipeOfIdIng(idIng));
        if (recipesList.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipesList);
    }

    @Operation(
            summary = "Поиск рецепта по двум id ингредиентов",
            description = "Можно искать по двум параметрам"
    )
    @Parameters( value = {
            @Parameter(name = "idIng1", example = "1"),
            @Parameter(name = "idIng2", example = "2")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был найден",
                    content = {
                            @Content(
                                    mediaType = "addlication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не был найден"
            )
    }
    )
    @GetMapping("/idIng1/{idIng1}/idIng2/{idIng2}")
    public ResponseEntity<Recipe> getRecipeOfIdIng(@PathVariable int idIng1, @PathVariable int idIng2) {
        Recipe recipe = recipeService.getRecipeOfIdIng2(idIng1, idIng2);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @Operation(
            summary = "Поиск всех рецептов",
            description = "Возвращает список всех рецептов, без параметров"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты были найдены",
                    content = {
                            @Content(
                                    mediaType = "addlication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепты не были найдены"
            )
    }
    )
    @GetMapping
    public ResponseEntity<Map<Integer, Recipe>> getAllIngredients() {
        Map<Integer, Recipe> listRecipes = recipeService.getAllRecipes();
        if (listRecipes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listRecipes);
    }

    @Operation(
            summary = "Изменение рецепта по id рецепта",
            description = "Можно искать по одному параметру"
    )
    @Parameters( value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был изменен",
                    content = {
                            @Content(
                                    mediaType = "addlication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры рецепта"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не был найден"
            )
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.editRecipe(id, recipe);
        if (newRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newRecipe);
    }

    @Operation(
            summary = "Удаление рецепта по id рецепта",
            description = "Можно искать по одному параметру"
    )
    @Parameters( value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был удален",
                    content = {
                            @Content(
                                    mediaType = "addlication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не был найден"
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
