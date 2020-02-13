package ru.simplelib.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * Role domain object
 *
 * @author Mikhail Yuzbashev
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    private Long id;
    private String systemName;

    @Override
    public String getAuthority() {
        return systemName;
    }
}
