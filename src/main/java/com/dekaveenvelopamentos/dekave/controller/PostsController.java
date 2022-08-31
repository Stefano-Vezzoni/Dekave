package com.dekaveenvelopamentos.dekave.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/")
public class PostsController {

    @Autowired
    private PostService service;

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public Posts getById(@RequestHeader UUID id) {
        return service.getById(id);
    }

    @GetMapping("/posts/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Posts> getServices(@RequestHeader UUID id) {
        return service.findAllByServiceId(id);
    }

    @PostMapping(value = "/posts/save", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.CREATED)
    public void savePost(@RequestHeader UUID id, @RequestPart("post") @Valid PostDTO postDTO,
            @RequestPart("file") MultipartFile file) {
        service.savePost(id, postDTO, file);

    }

    @PatchMapping(value = "/posts/update", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@RequestHeader UUID id, @RequestPart("post") PostDTO postDTO,
            @RequestPart("file") MultipartFile file) {
        service.updatePost(id, postDTO, file);

    }

    @DeleteMapping("/posts/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestHeader UUID id) {
        service.deleteById(id);
    }

}
