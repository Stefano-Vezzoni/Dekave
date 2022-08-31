package com.dekaveenvelopamentos.dekave.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Feedbacks;
import com.dekaveenvelopamentos.dekave.domain.repository.FeedbacksRepository;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.FeedbacksDTO;
import com.dekaveenvelopamentos.dekave.service.FeedbackService;
import com.dekaveenvelopamentos.dekave.service.FileService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Value("${images.folder.feedbacks}")
    private String path;

    @Autowired
    private FeedbacksRepository repository;

    @Autowired
    private FileService fileService;

    @Override
    public List<Feedbacks> findAll() {
        return repository.findAllByOrderByFeedbackOrder();
    }

    @Override
    public Feedbacks getById(UUID id) {

        try {
            return repository.findById(id).get();

        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Id not found.");
        }
    }

    @Override
    public void activeById(UUID id, ActiveDTO activeDTO) {

        Feedbacks feedback = repository.getById(id);

        if (activeDTO.getActive() == true) {
            feedback.setActive(true);
            repository.save(feedback);
        }
        if (activeDTO.getActive() == false) {
            feedback.setActive(false);
            repository.save(feedback);
        }
    }

    @Override
    @Transactional
    public void saveFeedback(FeedbacksDTO feedbacksDTO, MultipartFile file) {

        Feedbacks feedback = new Feedbacks();

        feedback.setName(feedbacksDTO.getName());
        feedback.setTitle(feedbacksDTO.getTitle());
        feedback.setComment(feedbacksDTO.getComment());
        feedback.setActive(true);
        feedback.setFeedbackOrder(repository.count() + 1);
        feedback.setAvatar(fileService.uploadImage(path, file));

        repository.save(feedback);
    }

    @Override
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
            fileService.deleteFile(feedback.getAvatar());

            feedback.setAvatar(fileService.uploadImage(path, file));
        }

        repository.save(feedback);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        fileService.deleteFile(repository.findById(id).get().getAvatar());
        repository.deleteById(id);

        Long index = 1L;
        List<Feedbacks> feedbacks = repository.findAllByOrderByFeedbackOrder();
        for (Feedbacks feedback : feedbacks) {
            feedback.setFeedbackOrder(index++);
        }
        repository.saveAll(feedbacks);
    }

}
