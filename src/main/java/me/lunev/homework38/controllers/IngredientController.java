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
import me.lunev.homework38.model.Ingredient;
import me.lunev.homework38.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Class - controller for working with ingredients, containing a set of API endpoints
 *
 * @see IngredientService
 */
@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ingredients", description = "CRUD operations and other endpoints for working with ingredients")
public class IngredientController {

    public final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(
            summary = "Adding an Ingredient",
            description = "Adding a new ingredient from the request body with an id assigned from the generator"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The ingredient has been added",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Incorrect ingredient parameters"
            )
    }
    )
    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(ingredient);
    }

    @Operation(
            summary = "Search for an ingredient by ingredient id",
            description = "You can search by one parameter"
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The ingredient has been found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The ingredient is not found"
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable int id) {
        return ResponseEntity.of(ingredientService.getIngredient(id));
    }

    @Operation(
            summary = "Search all ingredients",
            description = "Returns a list of all ingredients, no parameters"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The ingredients have been found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The ingredients is not found"
            )
    }
    )
    @GetMapping
    public ResponseEntity<Map<Integer, Ingredient>> getAllIngredients() {
        if (ingredientService.getAllIngredients() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @Operation(
            summary = "Changing an ingredient by ingredient id",
            description = "You can search by one parameter"
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The ingredient has been changed",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Incorrect ingredient parameters"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The ingredient is not found"
            )
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        return ResponseEntity.of(ingredientService.editIngredient(id, ingredient));
    }

    @Operation(
            summary = "Removing an ingredient by ingredient id",
            description = "You can search by one parameter"
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The ingredient has been removed",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The ingredient is not found"
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable int id) {
        return ResponseEntity.of(ingredientService.deleteIngredient(id));
    }
}
