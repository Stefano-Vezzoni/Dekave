package com.dekaveenvelopamentos.dekave.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Posts;
import com.dekaveenvelopamentos.dekave.domain.repository.PostsRepository;
import com.dekaveenvelopamentos.dekave.domain.repository.ServicesRepository;
import com.dekaveenvelopamentos.dekave.dto.PostDTO;
import com.dekaveenvelopamentos.dekave.service.FileService;
import com.dekaveenvelopamentos.dekave.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Value("${images.folder.posts}")
    private String path;

    @Autowired
    private PostsRepository repository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private FileService fileService;

    @Override
    public Posts getById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Posts> findAllByServiceId(UUID serviceId) {
        return repository.findAllByServiceId(serviceId);
    }

    @Override
    @Transactional
    public void savePost(UUID serviceId, PostDTO postDTO, MultipartFile file) {

        Posts post = new Posts();

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setPhoto(fileService.uploadImage(path, file));
        post.setPostsOrder(repository.countByService(servicesRepository.findById(serviceId).get()) + 1);
        post.setService(servicesRepository.findById(serviceId).get());

        repository.save(post);
    }

    @Override
    @Transactional
    public void updatePost(UUID id, PostDTO postDTO, MultipartFile file) {

        Posts post = repository.getById(id);

        if (postDTO.getTitle() != null) {
            post.setTitle(postDTO.getTitle());
        }
        if (postDTO.getDescription() != null) {
            post.setDescription(postDTO.getDescription());
        }
        if (file != null) {
            fileService.deleteFile(post.getPhoto());

            post.setPhoto(fileService.uploadImage(path, file));
        }

        repository.save(post);

    }

    @Override
    public void deleteById(UUID id) {

        fileService.deleteFile(repository.findById(id).get().getPhoto());
        repository.deleteById(id);

        Long index = 1L;
        List<Posts> posts = repository.findAllByOrderByPostsOrder();
        for (Posts post : posts) {
            post.setPostsOrder(index++);
        }
        repository.saveAll(posts);
    }

}
