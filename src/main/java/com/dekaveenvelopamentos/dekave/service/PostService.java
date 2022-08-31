package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Posts;
import com.dekaveenvelopamentos.dekave.dto.PostDTO;

public interface PostService {

    Posts getById(UUID id);

    List<Posts> findAllByServiceId(UUID serviceId);

    void savePost(UUID serviceId, PostDTO postDTO, MultipartFile file);

    void updatePost(UUID id, PostDTO postDTO, MultipartFile file);

    void deleteById(UUID id);
}
