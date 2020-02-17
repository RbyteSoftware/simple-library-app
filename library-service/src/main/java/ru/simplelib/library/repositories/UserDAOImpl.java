package ru.simplelib.library.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.simplelib.library.domain.Person;
import ru.simplelib.library.domain.Role;
import ru.simplelib.library.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
@Slf4j
public class UserDAOImpl implements UserDAO {
    public static final String FIND_BY_LOGIN = "select * from User u " +
            "left join Person p on p.userId = u.Id " +
            "where u.login = ?";
    public static final String FIND_ALL = "select * from User u " +
            "left join Person p on p.userId = u.Id";
    private static final String SELECT_ROLES = "select * from Role role " +
            "left join UserRoles roles on role.Id = roles.roleId " +
            "where roles.userId = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findOneByLogin(String login) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_LOGIN, new Object[]{login}, new UserRowMapper(true)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        try {
            List<User> userList = jdbcTemplate.query(FIND_ALL, new UserRowMapper());
            return Objects.nonNull(pageable) ? PageSort.by(userList, pageable) : userList;
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<User> findAllByIds(Iterable<Long> ids, Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void save(User user) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    class UserRowMapper implements RowMapper<User> {
        private boolean extractRoles = false;

        public UserRowMapper() {
        }

        public UserRowMapper(boolean extractRoles) {
            this.extractRoles = extractRoles;
        }

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = getUserFromResultSet(rs);
            if (extractRoles)
                user.addRoles(getUserRoles(user));
            return user;
        }

        private User getUserFromResultSet(ResultSet rs) throws SQLException {
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
            return user;
        }
    }

    private Set<Role> getUserRoles(User user) {
        return new HashSet<>(jdbcTemplate.query(SELECT_ROLES, new Object[]{user.getId()}, (rs, rowNum) -> new Role(
                        rs.getLong("id"),
                        rs.getString("systemName")
                )
        ));
    }

}
