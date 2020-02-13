package ru.simplelib.library.controller.transfer.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mikhail Yuzbashev
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialPairDto {
    private String username;
    private String password;
}
