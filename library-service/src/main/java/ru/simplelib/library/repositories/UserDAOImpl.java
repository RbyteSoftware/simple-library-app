package ru.simplelib.library.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.simplelib.library.domain.Person;
import ru.simplelib.library.domain.Role;
import ru.simplelib.library.domain.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDAOImpl implements UserDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findOneByLogin(String login) {
        String sqlQuery = "select * from User u " +
                "left outer join Person p on p.userId = u.Id " +
                "where u.login = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sqlQuery, new Object[]{login}, (rs, rowNum) -> {
                        User user = new User(
                                rs.getString("login"),
                                rs.getString("password"),
                                new Person(
                                        rs.getString("firstName"),
                                        rs.getString("lastName"),
                                        rs.getString("email")
                                )
                        );
                        user.setId(rs.getLong("id"));
                        user.addRoles(getUserRoles(user));
                        return user;
                    }
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<Iterable<User>> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Iterable<User>> findAllByIds(Iterable<Long> ids, Pageable pageable) {
        return null;
    }

    private Set<Role> getUserRoles(User user) {
        String sqlQuery = "select * from Role role " +
                "left join UserRoles roles on role.Id = roles.roleId " +
                "where roles.userId = ?";
        return new HashSet<>(jdbcTemplate.query(sqlQuery, new Object[]{user.getId()}, (rs, rowNum) -> new Role(
                        rs.getLong("id"),
                        rs.getString("systemName")
                )
        ));
    }

}
