package ru.simplelib.library.controller.transfer.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicTokenWithRole implements AuthData {
    private String token;
    private Set<String> roles;
}
