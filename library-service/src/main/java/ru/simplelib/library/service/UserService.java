package ru.simplelib.library.service;

import ru.simplelib.library.domain.User;
import ru.simplelib.library.exceptions.ServiceModificationException;

import java.util.List;

public interface UserService {

    Integer getCount();

    List<User> getList(Integer pageNum, Integer perPage, String sort, String order);

    User createUser(User user) throws ServiceModificationException;
}
