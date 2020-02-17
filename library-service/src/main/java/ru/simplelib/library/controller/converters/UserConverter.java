package ru.simplelib.library.controller.converters;

import org.springframework.stereotype.Component;
import ru.simplelib.library.controller.transfer.user.UserDto;
import ru.simplelib.library.domain.Person;
import ru.simplelib.library.domain.User;

import java.util.Objects;

/**
 * Convert User <--> UserDto
 *
 * @author Mikhail Yuzbashev
 */
@Component
public class UserConverter implements Converter<User, UserDto> {

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
        return userDto;
    }

    @Override
    public User to(UserDto userDto) {
        User user = new User();
        user.setId(user.getId());
        user.setLogin(userDto.getLogin());
        user.setPerson(new Person(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail()));
        return user;
    }
}
