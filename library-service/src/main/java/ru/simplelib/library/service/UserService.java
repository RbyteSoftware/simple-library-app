package ru.simplelib.library.service;

import ru.simplelib.library.domain.User;

import java.util.List;

public interface UserService {

    List<User> getList(Integer pageNum, Integer perPage, String sort, String order);

}
