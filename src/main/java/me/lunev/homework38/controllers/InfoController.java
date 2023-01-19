package me.lunev.homework38.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Информация", description = "Информация о приложении")
public class InfoController {

    @Operation(
            summary = "Информация о запуске приложения",
            description = "Возвращает информацию о запуске приложения"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Приложение запущено",
                    content = {
                            @Content(
                                    mediaType = "String"
                            )
                    }
            )
    }
    )
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String start() {
        return "Приложение запущено";
    }

    @Operation(
            summary = "Информация о приложении",
            description = "Возвращает информацию об авторе, названии, дате создания и названии приложения"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о приложении",
                    content = {
                            @Content(
                                    mediaType = "String"
                            )
                    }
            )
    }
    )
    @GetMapping("/info")
    public String info() {
        return "Дмитрий Course3 12.01.2023 Приложение для сайта рецептов";
    }
}
