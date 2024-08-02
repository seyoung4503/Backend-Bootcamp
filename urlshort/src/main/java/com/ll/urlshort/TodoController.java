package com.ll.urlshort;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {
    @GetMapping("/add")
    public void add(String body) {
        Todo todo = new Todo(++todosLastID, body);
    }
}
