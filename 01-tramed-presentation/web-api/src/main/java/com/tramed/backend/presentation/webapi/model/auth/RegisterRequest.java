package com.tramed.backend.presentation.webapi.model.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank @Size(min = 4, max = 100) String username,
    @NotBlank @Size(min = 6, max = 255) String password,
    @NotBlank @Size(max = 255) String fullName) {}
