package ru.simplelib.library.repositories;

import org.springframework.data.domain.Pageable;
import ru.simplelib.library.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * This interface is modeling for show how to use native jdbcTemplate without Spring Data crud
 * functionality
 *
 * @author Mikhail Yuzbashev
 */
public interface UserDAO {
    /**
     * todo: write javadoc
     *
     * @param login
     * @return
     */
    Optional<User> findOneByLogin(String login);

    /**
     * todo: write javadoc
     *
     * @param id
     * @return
     */
    Optional<User> findOne(Long id);

    /**
     * todo: write javadoc
     *
     * @param pageable
     * @return
     */
    List<User> findAll(Pageable pageable);

    /**
     * todo: write javadoc
     *
     * @param ids
     * @param pageable
     * @return
     */
    List<User> findAllByIds(Iterable<Long> ids, Pageable pageable);

    void save(User user);
}
