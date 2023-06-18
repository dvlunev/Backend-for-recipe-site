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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class - controller for working with recipes, containing a set of API endpoints
 *
 * @see RecipeService
 */
@RestController
@RequestMapping("/recipe")
@Tag(name = "Recipes", description = "CRUD operations and other endpoints for working with recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(
            summary = "Adding a Recipe and Its Ingredients",
            description = "Adding a new recipe and its ingredients from the request body, assigning them an id from the generator"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The recipe and its ingredients have been added",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Incorrect recipe parameters"
            )
    }
    )
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(recipe);
    }

    @Operation(
            summary = "Recipe search by recipe id",
            description = "You can search by one parameter"
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The recipe has been found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The Recipe is not found"
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
            summary = "Search recipe by ingredient id",
            description = "You can search by one parameter"
    )
    @Parameters(value = {
            @Parameter(name = "idIng", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The recipe has been found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The Recipe is not found"
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
            summary = "Recipe search by two ingredient id",
            description = "You can search by two parameters"
    )
    @Parameters(value = {
            @Parameter(name = "idIng1", example = "1"),
            @Parameter(name = "idIng2", example = "2")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The recipe has been found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The Recipe is not found"
            )
    }
    )
    @GetMapping("/idsIng/{idsIng}")
    public ResponseEntity<List<Recipe>> getRecipeOfIdIng(@PathVariable Integer... idsIng) {
        List<Recipe> recipesList = new ArrayList<>(recipeService.getRecipeOfIdsIng(idsIng));
        if (recipesList.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipesList);
    }

    @Operation(
            summary = "Search all recipes",
            description = "Output of recipes per page 10 pcs per page"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recipes have been found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recipes is not found"
            )
    }
    )
    @GetMapping("/pageNumber/{pageNumber}")
    public ResponseEntity<List<Recipe>> getRecipeOfPage(@PathVariable int pageNumber) {
        List<Recipe> recipesList = new ArrayList<>(recipeService.getRecipeOfPage(pageNumber));
        if (recipesList.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipesList);
    }

    @Operation(
            summary = "Search all recipes",
            description = "Returns a list of all recipes, no parameters"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recipes have been found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recipes is not found"
            )
    }
    )
    @GetMapping
    public ResponseEntity<Map<Integer, Recipe>> getAllRecipes() {
        if (recipeService.getAllRecipes() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @Operation(
            summary = "Getting a file of all recipes",
            description = "Returns a list of all recipes to the file, without parameters"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recipes have been found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recipes is not found"
            )
    }
    )
    @GetMapping("/report")
    public ResponseEntity<Object> getAllRecipesReport() {
        try {
            Path path = recipeService.createAllRecipesReport();
            if (Files.size(path) == 0) {
                return ResponseEntity.notFound().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"allRecieps-report.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }

    @Operation(
            summary = "Changing a recipe by recipe id",
            description = "You can search by one parameter"
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recipe has been changed",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Incorrect recipe parameters"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The Recipe is not found"
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
            summary = "Deleting a recipe by recipe id",
            description = "You can search by one parameter"
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The Recipe has been deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The Recipe is not found"
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
