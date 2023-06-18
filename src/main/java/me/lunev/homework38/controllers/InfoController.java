package me.lunev.homework38.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class - controller with the app Information, containing a set of API endpoints
 */
@RestController
@Tag(name = "Information", description = "App Information")
public class InfoController {

    @Operation(
            summary = "Application Launch Information",
            description = "Returns information about the launch of the application"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Application launched",
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
        return "Application launched";
    }

    @Operation(
            summary = "App Information",
            description = "Returns information about the author, title, creation date, and application title"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "App Information",
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
        return "Dmitrii Lunev Course3 12.01.2023 Recipe website app";
    }
}
