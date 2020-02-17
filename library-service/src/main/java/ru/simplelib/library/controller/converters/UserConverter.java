package ru.simplelib.library.controller.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.simplelib.library.controller.transfer.user.UserDto;
import ru.simplelib.library.domain.Person;
import ru.simplelib.library.domain.Role;
import ru.simplelib.library.domain.User;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Convert User <--> UserDto
 *
 * @author Mikhail Yuzbashev
 */
@Component
public class UserConverter implements Converter<User, UserDto> {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setId(user.getId());
        if (Objects.nonNull(user.getPerson())) {
            userDto.setFirstName(user.getPerson().getFirstName());
            userDto.setLastName(user.getPerson().getLastName());
            userDto.setEmail(user.getPerson().getEmail());
        }
        userDto.setRoles(user.getRoles().stream().map(Role::getSystemName).collect(Collectors.toList()));
        return userDto;
    }

    @Override
    public User to(UserDto userDto) {
        User user = new User();
        user.setId(user.getId());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setLogin(userDto.getLogin());
        user.setPerson(new Person(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail()));
        user.setRoles(userDto.getRoles().stream().map(it -> new Role(null, it)).collect(Collectors.toSet()));
        return user;
    }
}
