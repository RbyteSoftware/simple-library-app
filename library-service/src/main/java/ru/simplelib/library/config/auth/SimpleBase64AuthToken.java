package ru.simplelib.library.config.auth;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * This token emulate Basic authentication
 *
 * @author Mikhail Yuzbashev
 */
public class SimpleBase64AuthToken {

    private String username;
    private String password;


    private SimpleBase64AuthToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getHash() {
        String toEncode = this.username + ":" + this.password;
        return Base64.getEncoder().encodeToString(
                toEncode.getBytes());
    }


    public static String getToken(UserDetails userDetails) {
        return getToken(userDetails.getUsername(), userDetails.getPassword());
    }

    public static String getToken(String username, String password) {
        String toEncode = username + ":" + password;
        return Base64.getEncoder().encodeToString(
                toEncode.getBytes());
    }

    public static boolean validate(SimpleBase64AuthToken validateToken, String hash) {
        return Objects.equals(validateToken.getHash(), hash);
    }

    public static SimpleBase64AuthToken byToken(String token) {
        String decoded = new String(Base64.getDecoder()
                .decode(token));
        List<String> pair = Arrays.asList(decoded.split(":"));
        // todo: fixes java.lang.ArrayIndexOutOfBoundsException: 1
        return new SimpleBase64AuthToken(pair.get(0), pair.get(1));
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
