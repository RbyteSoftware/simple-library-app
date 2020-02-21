package ru.simplelib.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.simplelib.library.controller.converters.BookConverter;
import ru.simplelib.library.service.BookService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mikhail Yuzbashev
 */
@RestController
@RequestMapping("books")
public class BooksRestController {

    @Autowired
    BookService bookService;
    @Autowired
    BookConverter bookConverter;


    @CrossOrigin
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(
            @PathVariable("id") Integer id
    ) {
        // todo: add service to update
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
        headers.add("Content-Range", "users " + pageNum + "-" + perPage + "/" + bookService.getBookCount());
        headers.add("Access-Control-Expose-Headers", "Content-Range");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bookConverter.fromList(
                        bookService.getList(pageNum, perPage, sortFieldName, sortDirection))
                );
    }

    @CrossOrigin
    @RequestMapping(value = "/list", method = RequestMethod.OPTIONS)
    public ResponseEntity options(HttpServletRequest request) {
        // todo: check granted option of token from request
        // todo: this method allow actions
        return ResponseEntity.ok().headers(new HttpHeaders() {{
            add(HttpHeaders.ALLOW, "GET,CREATE,PUT,POST,OPTIONS");
        }}).build();
    }
}
