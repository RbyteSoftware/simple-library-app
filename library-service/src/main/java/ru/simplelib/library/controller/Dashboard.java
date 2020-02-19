package ru.simplelib.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Dashboard {
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String root(Model model) {
        return "index";
    }
}