package ru.simplelib.library.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.simplelib.library.repositories.UserDAO;

@Service
@Slf4j
public class JdbcUserDetailsServiceImpl implements UserDetailsService {
    private UserDAO userDAO;

    @Autowired
    public JdbcUserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findOneByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
    }
}
