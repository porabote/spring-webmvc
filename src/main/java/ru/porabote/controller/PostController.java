package ru.porabote.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.porabote.model.Post;
import ru.porabote.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  @GetMapping
  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var data = service.all();
    final var gson = new Gson();
    response.getWriter().print(gson.toJson(data));
  }

  @GetMapping("/{id}")
  public void getById(@PathVariable long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    final var post = this.service.getById(id);
    response.getWriter().print(gson.toJson(post));
  }

  @PostMapping
  public void save(@RequestBody Reader body, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    final var post = gson.fromJson(body, Post.class);
    final var data = service.save(post);
    response.getWriter().print(gson.toJson(data));
  }

  @DeleteMapping("/{id}")
  public void removeById(@PathVariable long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    final var post = this.service.getById(id);
    service.removeById(id);
    response.getWriter().print(gson.toJson(post));
  }
}
