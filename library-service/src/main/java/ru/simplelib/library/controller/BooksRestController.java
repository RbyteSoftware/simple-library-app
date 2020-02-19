package ru.simplelib.library.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mikhail Yuzbashev
 */
@RestController
@RequestMapping("books")
public class BooksRestController {

    @CrossOrigin
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(
            @PathVariable("id") Integer id
    ) {
        // todo: add service to update
        return ResponseEntity.ok().build();
    }


    private HttpHeaders getHttpContentRange(Integer page, Integer perPage, Integer records) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", "assumptions " + page + "-" + perPage + "/" + records);
        headers.add("Access-Control-Expose-Headers", "Content-Range");
        return headers;
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
