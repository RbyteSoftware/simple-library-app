package ru.simplelib.library.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simplelib.library.domain.Person;
import ru.simplelib.library.domain.Role;
import ru.simplelib.library.domain.User;
import ru.simplelib.library.exceptions.ServiceModificationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDAOImpl implements UserDAO {
    // FIXME: do refactoring query`s to MappingSqlQuery wrappers coz it is thread-safe
    public static final String COUNT = "select count(*) from User";
    public static final String FIND_BY_LOGIN = "select * from User u " +
            "left join Person p on p.userId = u.Id " +
            "where u.login = ?";
    public static final String FIND_ALL = "select * from User u " +
            "left join Person p on p.userId = u.Id";
    private static final String SELECT_USER_ROLES = "select * from Role role " +
            "left join UserRoles roles on role.Id = roles.roleId " +
            "where roles.userId = ?";
    private static final String SELECT_ALL_ROLES = "select * from Role";

    private final JdbcTemplate jdbcTemplate;

    Map<String, SimpleJdbcInsertOperations> insertTemplates;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate, Map<String, SimpleJdbcInsertOperations> insertTemplates) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertTemplates = insertTemplates;
        log.info("Insert templates {}", insertTemplates);
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

    @Transactional
    @Override
    public User create(User user) throws ServiceModificationException {
        Long userId = insertUser(user);
        user.setId(userId);
        if (Objects.nonNull(user.getPerson())) {
            insertPerson(user);
        }
        if (Objects.nonNull(user.getRoles()) && !user.getRoles().isEmpty()) {
            insertUserRoles(user, user.getRoles());
        }
        return user;
    }

    @Override
    public Integer getCount() {
        return Objects.requireNonNull(jdbcTemplate.queryForObject(COUNT, Long.class)).intValue();
    }

    private void insertUserRoles(User user, Set<Role> incomingRoles) {
        Map<String, Role> availableRoles = getAllRoles()
                .stream().collect(Collectors.toMap(Role::getSystemName, e -> e));
        List<Role> filtred = incomingRoles.stream()
                .filter(it -> availableRoles.containsKey(it.getSystemName()))
                .collect(Collectors.toList());
        Set<Role> toAdd = new HashSet<>(filtred.size());
        for (Role it : filtred) {
            Role role = availableRoles.get(it.getSystemName());
            insertUserRoleRef(user.getId(), role.getId());
            toAdd.add(role);
        }
        user.setRoles(toAdd);
    }

    private void insertUserRoleRef(final Long userId, final Long roleId) {
        try {
            SimpleJdbcInsertOperations insertOperations = insertTemplates.get("roleRefInsert");
            Objects.requireNonNull(insertOperations, "Could be Autowired userInsert operation bean");
            insertOperations.execute(new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("roleId", roleId)
            );
        } catch (DuplicateKeyException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void insertPerson(User user) {
        SimpleJdbcInsertOperations insertOperations = insertTemplates.get("personInsert");
        Objects.requireNonNull(insertOperations, "Could be Autowired userInsert operation bean");
        Objects.requireNonNull(user.getId(), "Entity has no key reference");
        Person person = user.getPerson();
        insertOperations.executeAndReturnKey(new MapSqlParameterSource()
                .addValue("userId", user.getId())
                .addValue("firstName", person.getFirstName())
                .addValue("lastName", person.getLastName())
                .addValue("email", person.getEmail())
        );
    }

    private Long insertUser(User user) throws ServiceModificationException {
        try {
            SimpleJdbcInsertOperations insertOperations = insertTemplates.get("userInsert");
            Objects.requireNonNull(insertOperations, "Could be Autowired userInsert operation bean");

            return Optional.of(insertOperations.executeAndReturnKey(new MapSqlParameterSource()
                    .addValue("login", user.getLogin())
                    .addValue("password", user.getPassword()))
            ).get().longValue();

        } catch (DuplicateKeyException e) {
            throw new ServiceModificationException("Duplicate unique constrain");
        }
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

    private Set<Role> getAllRoles() {
        return new HashSet<>(jdbcTemplate.query(SELECT_ALL_ROLES, (rs, rowNum) -> new Role(
                        rs.getLong("id"),
                        rs.getString("systemName")
                )
        ));
    }

    private Set<Role> getUserRoles(User user) {
        return new HashSet<>(jdbcTemplate.query(SELECT_USER_ROLES, new Object[]{user.getId()}, (rs, rowNum) -> new Role(
                        rs.getLong("id"),
                        rs.getString("systemName")
                )
        ));
    }

}
