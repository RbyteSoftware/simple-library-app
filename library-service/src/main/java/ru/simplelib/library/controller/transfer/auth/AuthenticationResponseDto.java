package ru.simplelib.library.controller.transfer.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDto {
    private Boolean success;
    private Instant timestamp;
    private String message;
    private AuthData data;
}
