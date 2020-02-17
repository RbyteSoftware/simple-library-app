package ru.simplelib.library.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.simplelib.library.domain.User;
import ru.simplelib.library.exceptions.ServiceModificationException;
import ru.simplelib.library.repositories.UserDAO;
import ru.simplelib.library.service.common.PageRequestBuilder;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${simplelib.default.onPage}")
    private Integer onPage;

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getList(Integer pageNum, Integer perPage, String sort, String order) {
        PageRequestBuilder pageRequestBuilder = new PageRequestBuilder()
                .setDefaultRowOnPage(onPage)
                .setRange(new PageRequestBuilder.BasicRangeMapper(perPage, pageNum))
                .setSort(new PageRequestBuilder.BasicSortMapper(sort, order));
        return userDAO.findAll(pageRequestBuilder.build());
    }

    @Override
    public User createUser(User user) throws ServiceModificationException {
        return userDAO.create(user);
    }
}
