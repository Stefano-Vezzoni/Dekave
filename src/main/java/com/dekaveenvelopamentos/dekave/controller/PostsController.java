package com.dekaveenvelopamentos.dekave.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Posts;
import com.dekaveenvelopamentos.dekave.dto.PostDTO;
import com.dekaveenvelopamentos.dekave.exception.ReorderActionException;
import com.dekaveenvelopamentos.dekave.exception.ReorderPositionException;
import com.dekaveenvelopamentos.dekave.service.PostService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${api.v1}/posts")
public class PostsController {

    final String postsTag = "Posts";

    @Autowired
    private PostService service;

    @Operation(summary = "Get by id.", tags = postsTag)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Posts getPostById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @Operation(summary = "Get all per page and size.", tags = postsTag)
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Posts> getAllPostsByServiceId(@RequestParam UUID id,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "50") Integer size) {
        return service.getPostsByServiceId(id, page, size);
    }

    @Operation(summary = "Get image by id.", tags = postsTag)
    @GetMapping("/images/{fileName}")
    public ResponseEntity<?> getImageById(@PathVariable String fileName) throws IOException {
        return service.getImageById(fileName);
    }

    @Operation(summary = "Save new post.", tags = postsTag)
    @PostMapping(value = "/save/{id}", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.CREATED)
    public void savePost(@PathVariable UUID id, @RequestPart("post") @Valid PostDTO postDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.savePost(id, postDTO, file);

    }

    @Operation(summary = "Update by id.", tags = postsTag)
    @PutMapping(value = "/update/{id}", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@PathVariable UUID id, @RequestPart("post") PostDTO postDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.updatePost(id, postDTO, file);

    }

    @Operation(summary = "Reorder.", tags = postsTag)
    @PutMapping("/reorder")
    @ResponseStatus(HttpStatus.OK)
    public void reorder(@RequestParam UUID id, @RequestParam Long currentPosition,
            @RequestParam String action)
            throws ReorderPositionException, ReorderActionException {
        service.reorder(id, currentPosition, action);
    }

    @Operation(summary = "Delete by id.", tags = postsTag)
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostById(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
