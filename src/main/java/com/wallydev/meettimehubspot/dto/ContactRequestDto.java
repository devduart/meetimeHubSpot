package com.wallydev.meettimehubspot.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto para criação de contato no HubSpot")
public record ContactRequestDto(
        @Schema(description = "Primeiro nome do contato", example = "John")
        String firstname,

        @Schema(description = "Último nome do contato", example = "Doe")
        String lastname,

        @Schema(description = "Email do contato", example = "john.doe@example.com")
        String email
) {}
