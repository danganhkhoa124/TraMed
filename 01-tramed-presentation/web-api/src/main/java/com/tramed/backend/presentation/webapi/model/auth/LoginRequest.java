package com.tramed.backend.presentation.webapi.model.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "E0005") String username, @NotBlank(message = "E0005") String password) {}
