package com.dekaveenvelopamentos.dekave.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Posts;
import com.dekaveenvelopamentos.dekave.domain.repository.PostsRepository;
import com.dekaveenvelopamentos.dekave.domain.repository.ServicesRepository;
import com.dekaveenvelopamentos.dekave.dto.PostDTO;
import com.dekaveenvelopamentos.dekave.exception.ReorderActionException;
import com.dekaveenvelopamentos.dekave.exception.ReorderPositionException;

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

    public ResponseEntity<?> getImageById(String fileName) throws IOException {
        return genericService.getImageByFileName(path + fileName);
    }

    @Transactional
    public void savePost(UUID serviceId, PostDTO postDTO, MultipartFile file) throws IOException {

        Posts post = new Posts();

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setPhoto(genericService.uploadImage(path, file));
        post.setPostsOrder(repository.countByService(servicesRepository.findById(serviceId).get()) + 1);
        post.setService(servicesRepository.findById(serviceId).get());

        repository.save(post);
    }

    @Transactional
    public void updatePost(UUID id, PostDTO postDTO, MultipartFile file) throws IOException {

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
    public void reorder(UUID serviceId, Long currentPosition, String action)
            throws ReorderPositionException, ReorderActionException {

        Posts currentPost = repository.findByServiceIdAndPosition(serviceId, currentPosition);
        Posts nextPost = repository.findByServiceIdAndPosition(serviceId, currentPosition + 1);
        Posts previousService = repository.findByServiceIdAndPosition(serviceId, currentPosition - 1);

        if (action.equalsIgnoreCase("down") && action.equalsIgnoreCase("up")) {
            throw new ReorderActionException();

        } else if (action.equalsIgnoreCase("down")) {
            reorderDown(currentPost, nextPost, currentPosition);

        } else if (action.equalsIgnoreCase("up")) {
            if (currentPosition == 1) {
                throw new ReorderPositionException();
            }
            reorderUp(currentPost, previousService, currentPosition);
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

    @Transactional
    public void reorderDown(Posts current, Posts next, Long currentPosition) throws ReorderPositionException {

        if (next != null) {
            // resetting positions
            current.setPostsOrder(-1L);
            next.setPostsOrder(-2L);

            current.setPostsOrder(currentPosition + 1);
            next.setPostsOrder(currentPosition);
        } else {
            throw new ReorderPositionException();
        }

    }

    @Transactional
    public void reorderUp(Posts current, Posts previous, Long currentPosition) {
        // resetting positions
        current.setPostsOrder(-1L);
        previous.setPostsOrder(-2L);

        current.setPostsOrder(currentPosition - 1);
        previous.setPostsOrder(currentPosition);
    }
}
