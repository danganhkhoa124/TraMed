package com.tramed.backend.presentation.webapi.model.auth;

import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusRequest(@NotNull Boolean active) {}
