package com.tramed.backend.presentation.webapi.model.auth;

public record LoginResponse(String token, String tokenType, long expiresInMinutes) {}
