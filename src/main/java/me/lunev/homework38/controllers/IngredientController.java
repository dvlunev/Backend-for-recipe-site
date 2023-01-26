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

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "CRUD-операции и другие эндпоинты для работы с ингредиентами")
public class IngredientController {

    public final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(
            summary = "Добавление ингредиента",
            description = "Добавление нового ингредиента из тела запроса с присвоением id из генератора"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры ингредиента"
            )
    }
    )
    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(ingredient);
    }

    @Operation(
            summary = "Поиск ингредиента по id ингредиента",
            description = "Можно искать по одному параметру"
    )
    @Parameters( value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не был найден"
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable int id) {
        return ResponseEntity.of(ingredientService.getIngredient(id));
    }

    @Operation(
            summary = "Поиск всех ингредиентов",
            description = "Возвращает список всех ингредиентов, без параметров"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты были найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиенты не были найдены"
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
            summary = "Изменение ингредиента по id ингредиента",
            description = "Можно искать по одному параметру"
    )
    @Parameters( value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был изменен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры ингредиента"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не был найден"
            )
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        return ResponseEntity.of(ingredientService.editIngredient(id, ingredient));
    }

    @Operation(
            summary = "Удаление ингредиента по id ингредиента",
            description = "Можно искать по одному параметру"
    )
    @Parameters( value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был удален",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не был найден"
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable int id) {
        return ResponseEntity.of(ingredientService.deleteIngredient(id));
    }
}
