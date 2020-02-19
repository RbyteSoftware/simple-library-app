package ru.simplelib.library.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.simplelib.library.controller.converters.UserConverter;
import ru.simplelib.library.controller.transfer.user.UserDto;
import ru.simplelib.library.exceptions.ServiceModificationException;
import ru.simplelib.library.service.UserService;

import java.security.Principal;

/**
 * Rest controller for user
 *
 * @author Mikhail Yuzbashev
 */
@RestController
@RequestMapping("users")
@Slf4j
public class UserRestController {

    private final UserConverter userConverter;
    private final UserService userService;

    public UserRestController(UserConverter userConverter, UserService userService) {
        this.userConverter = userConverter;
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        try {
            userService.createUser(userConverter.to(userDto));
        } catch (ServiceModificationException e) {
            log.error(e.getMessage(), e);
        }
        // todo: add good response
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @Secured({"USER", "ADMIN"})
    @RequestMapping(value = "/list", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userList(
            @RequestParam(value = "sort", required = false) String sortFieldName,
            @RequestParam(value = "order", required = false) String sortDirection,
            @RequestParam(value = "page", required = false) Integer pageNum,
            @RequestParam(value = "perPage", required = false) Integer perPage
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", "users " + pageNum + "-" + perPage + "/" + userService.getCount());
        headers.add("Access-Control-Expose-Headers", "Content-Range");

        return ResponseEntity.ok()
                .headers(headers)
                .body(userConverter.fromList(
                        userService.getList(pageNum, perPage, sortFieldName, sortDirection))
                );
    }

    @CrossOrigin
    @RequestMapping(value = {"/{idCollection}"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("idCollection") String idCollection) {

        return ResponseEntity.ok().build();
    }


    @CrossOrigin
    @RequestMapping(value = {"/{idCollection}"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options(@AuthenticationPrincipal Principal principal,
                                     @PathVariable("idCollection") String idCollection) {
        // todo: get Authority`s
        return ResponseEntity.ok().headers(new HttpHeaders() {{
            add(HttpHeaders.ALLOW, "GET, CREATE, PUT, DELETE, POST, OPTIONS");
        }}).build();
    }


}
