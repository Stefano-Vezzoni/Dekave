package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Posts;
import com.dekaveenvelopamentos.dekave.domain.repository.PostsRepository;
import com.dekaveenvelopamentos.dekave.domain.repository.ServicesRepository;
import com.dekaveenvelopamentos.dekave.dto.PostDTO;

@Service
public class PostService {

    @Value("${images.folder.posts}")
    private String path;

    @Autowired
    private PostsRepository repository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private GenericService genericService;

    public Posts getById(UUID id) {
        return repository.findById(id).get();
    }

    public List<Posts> getPostsByServiceId(UUID serviceId, Integer page, Integer size) {

        Pageable pageable = genericService.pageableAndSort(page, size, "postsOrder");

        Page<Posts> postsByServiceId = repository.findAllByServiceId(serviceId, pageable);

        return postsByServiceId.getContent();
    }

    @Transactional
    public void savePost(UUID serviceId, PostDTO postDTO, MultipartFile file) {

        Posts post = new Posts();

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setPhoto(genericService.uploadImage(path, file));
        post.setPostsOrder(repository.countByService(servicesRepository.findById(serviceId).get()) + 1);
        post.setService(servicesRepository.findById(serviceId).get());

        repository.save(post);
    }

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
            genericService.deleteFile(post.getPhoto());

            post.setPhoto(genericService.uploadImage(path, file));
        }
    }

    @Transactional
    public void deleteById(UUID id) {

        // Get service ID to re-order after delete.
        UUID serviceId = repository.findById(id).get().getService().getId();

        genericService.deleteFile(repository.findById(id).get().getPhoto());
        repository.deleteById(id);

        Long index = 1L;
        List<Posts> posts = repository.findAllByServiceIdOrdered(serviceId);

        for (Posts post : posts) {
            post.setPostsOrder(index++);
        }
    }
}
