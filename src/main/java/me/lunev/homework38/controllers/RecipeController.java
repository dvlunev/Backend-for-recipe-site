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
                                    mediaType = "application/json",
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
                                    mediaType = "application/json",
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
                                    mediaType = "application/json",
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
                                    mediaType = "application/json",
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
    @GetMapping("/idsIng/{idsIng}")
    public ResponseEntity<List<Recipe>> getRecipeOfIdIng(@PathVariable Integer... idsIng) {
        List<Recipe> recipesList = new ArrayList<>(recipeService.getRecipeOfIdsIng(idsIng));
        if (recipesList.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipesList);
    }

    @Operation(
            summary = "Поиск всех рецептов",
            description = "Вывод рецептов постранично 10 шт на странице"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты были найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
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
    @GetMapping("/pageNumber/{pageNumber}")
    public ResponseEntity<List<Recipe>> getRecipeOfPage(@PathVariable int pageNumber) {
        List<Recipe> recipesList = new ArrayList<>(recipeService.getRecipeOfPage(pageNumber));
        if (recipesList.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipesList);
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
                                    mediaType = "application/json",
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
    public ResponseEntity<Map<Integer, Recipe>> getAllRecipes() {
        if (recipeService.getAllRecipes() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @Operation(
            summary = "Получение файла всех рецептов",
            description = "Возвращает в файл список всех рецептов, без параметров"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты были найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
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
                                    mediaType = "application/json",
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
                                    mediaType = "application/json",
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
