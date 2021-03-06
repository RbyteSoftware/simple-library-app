package ru.simplelib.library.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * User domain object
 *
 * @author Mikhail Yuzbashev
 */
@Data
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    private Long id;
    @Length(max = 32)
    private String login;
    private String password;
    private Person person;
    private Set<Role> roles = new HashSet<>();

    /**
     * Constructor create full data User
     *
     * @param login    user login in system
     * @param password encrypted secret word
     * @param person   Personal information about User
     */
    public User(String login, String password, Person person) {
        this.login = login;
        this.password = password;
        this.person = person;
    }

    /**
     * Method check have User any roles
     *
     * @return true - if have roles/false if not
     */
    public boolean hasRoles() {
        return !roles.isEmpty();
    }

    /**
     * Add role to User
     *
     * @param role Role
     */
    public void addRole(Role role) {
        addRoles(Collections.singletonList(role));
    }

    /**
     * Add collection Role`s to user
     *
     * @param roleCollection Accept any type of collection with roles
     */
    public void addRoles(Collection<Role> roleCollection) {
        this.roles.addAll(roleCollection);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
