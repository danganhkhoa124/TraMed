package com.tramed.backend.presentation.webapi.model.auth;

import java.util.UUID;

public record RegisterResponse(UUID userId, String username, boolean active) {}
