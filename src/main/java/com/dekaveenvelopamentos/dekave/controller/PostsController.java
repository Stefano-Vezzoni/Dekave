package com.dekaveenvelopamentos.dekave.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Posts;
import com.dekaveenvelopamentos.dekave.dto.PostDTO;
import com.dekaveenvelopamentos.dekave.service.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostsController {

    @Autowired
    private PostService service;

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public Posts getById(@RequestHeader UUID id) {
        return service.getById(id);
    }

    @GetMapping("/posts/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<Posts> getServices(@RequestHeader UUID id, @PathVariable Integer page, @PathVariable Integer size) {
        return service.getPostsByServiceId(id, page, size);
    }

    @PostMapping(value = "/posts/save", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.CREATED)
    public void savePost(@RequestHeader UUID id, @RequestPart("post") @Valid PostDTO postDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.savePost(id, postDTO, file);

    }

    @PutMapping(value = "/posts/update", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@RequestHeader UUID id, @RequestPart("post") PostDTO postDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.updatePost(id, postDTO, file);

    }

    @DeleteMapping("/posts/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestHeader UUID id) {
        service.deleteById(id);
    }
}
