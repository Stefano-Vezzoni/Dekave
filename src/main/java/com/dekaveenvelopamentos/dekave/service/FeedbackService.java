package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Feedbacks;
import com.dekaveenvelopamentos.dekave.domain.repository.FeedbacksRepository;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.FeedbacksDTO;

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
    public void saveFeedback(FeedbacksDTO feedbacksDTO, MultipartFile file) {

        Feedbacks feedback = new Feedbacks();

        feedback.setName(feedbacksDTO.getName());
        feedback.setTitle(feedbacksDTO.getTitle());
        feedback.setComment(feedbacksDTO.getComment());
        feedback.setActive(true);
        feedback.setFeedbackOrder(repository.count() + 1);
        feedback.setAvatar(genericService.uploadImage(path, file));

        repository.save(feedback);
    }

    @Transactional
    public void updateFeedback(UUID id, FeedbacksDTO feedbacksDTO, MultipartFile file) {

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
}
