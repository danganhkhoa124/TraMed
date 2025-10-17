package com.tramed.backend.applicationcore.systemcore.service.user;

/** Command object used to register a new user. */
public record RegisterUserCommand(String username, String password, String fullName) {}
