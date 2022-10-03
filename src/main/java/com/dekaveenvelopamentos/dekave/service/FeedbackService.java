package com.dekaveenvelopamentos.dekave.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Feedbacks;
import com.dekaveenvelopamentos.dekave.domain.repository.FeedbacksRepository;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.FeedbacksDTO;
import com.dekaveenvelopamentos.dekave.exception.ReorderActionException;
import com.dekaveenvelopamentos.dekave.exception.ReorderPositionException;

@Service
public class FeedbackService {

    @Value("${images.folder.feedbacks}")
    private String path;

    @Autowired
    private FeedbacksRepository repository;

    @Autowired
    private GenericService genericService;

    public Feedbacks getById(UUID id) {
        return repository.findById(id).get();
    }

    public List<Feedbacks> getFeedbacks(Integer page, Integer size) {

        Pageable pageable = genericService.pageableAndSort(page, size, "feedbackOrder");

        return repository.findAll(pageable).getContent();
    }

    public ResponseEntity<?> getImageById(String fileName) throws IOException {
        return genericService.getImageByFileName(path + fileName);
    }

    @Transactional
    public void activeById(UUID id, ActiveDTO activeDTO) {

        Feedbacks feedback = repository.getById(id);

        if (activeDTO.getActive() == true) {
            feedback.setActive(true);
        }
        if (activeDTO.getActive() == false) {
            feedback.setActive(false);
        }
    }

    @Transactional
    public void saveFeedback(FeedbacksDTO feedbacksDTO, MultipartFile file) throws IOException {

        Feedbacks feedback = new Feedbacks();

        BeanUtils.copyProperties(feedbacksDTO, feedback);
        feedback.setActive(true);
        feedback.setFeedbackOrder(repository.count() + 1);
        feedback.setAvatar(genericService.uploadImage(path, file));

        repository.save(feedback);
    }

    @Transactional
    public void updateFeedback(UUID id, FeedbacksDTO feedbacksDTO, MultipartFile file) throws IOException {

        Feedbacks feedback = repository.getById(id);

        if (feedbacksDTO.getName() != null) {
            feedback.setName(feedbacksDTO.getName());
        }
        if (feedbacksDTO.getTitle() != null) {
            feedback.setTitle(feedbacksDTO.getTitle());
        }
        if (feedbacksDTO.getComment() != null) {
            feedback.setComment(feedbacksDTO.getComment());
        }
        if (file != null) {
            genericService.deleteFile(feedback.getAvatar());
            feedback.setAvatar(genericService.uploadImage(path, file));
        }
    }

    @Transactional
    public void reorder(Long currentPosition, String action) throws ReorderPositionException, ReorderActionException {

        Feedbacks currentFeedback = repository.findByFeedbackOrder(currentPosition);
        Feedbacks nextFeedback = repository.findByFeedbackOrder(currentPosition + 1);
        Feedbacks previousFeedback = repository.findByFeedbackOrder(currentPosition - 1);

        if (action.equalsIgnoreCase("down") && action.equalsIgnoreCase("up")) {
            throw new ReorderActionException();

        } else if (action.equalsIgnoreCase("down")) {
            reorderDown(currentFeedback, nextFeedback, currentPosition);

        } else if (action.equalsIgnoreCase("up")) {
            if (currentPosition == 1) {
                throw new ReorderPositionException();
            }
            reorderUp(currentFeedback, previousFeedback, currentPosition);
        }
    }

    @Transactional
    public void deleteById(UUID id) {

        genericService.deleteFile(repository.findById(id).get().getAvatar());
        repository.deleteById(id);

        Long index = 1L;
        Sort sort = genericService.sort("asc", "feedbackOrder");
        List<Feedbacks> feedbacks = repository.findAll(sort);

        for (Feedbacks feedback : feedbacks) {
            feedback.setFeedbackOrder(index++);
        }
    }

    @Transactional
    public void reorderDown(Feedbacks current, Feedbacks next, Long currentPosition) throws ReorderPositionException {

        if (next != null) {
            // resetting positions
            current.setFeedbackOrder(-1L);
            next.setFeedbackOrder(-2L);

            current.setFeedbackOrder(currentPosition + 1);
            next.setFeedbackOrder(currentPosition);
        } else {
            throw new ReorderPositionException();
        }

    }

    @Transactional
    public void reorderUp(Feedbacks current, Feedbacks previous, Long currentPosition) {
        // resetting positions
        current.setFeedbackOrder(-1L);
        previous.setFeedbackOrder(-2L);

        current.setFeedbackOrder(currentPosition - 1);
        previous.setFeedbackOrder(currentPosition);
    }
}
