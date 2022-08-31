package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Feedbacks;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.FeedbacksDTO;

public interface FeedbackService {

    Feedbacks getById(UUID id);

    List<Feedbacks> findAll();

    void activeById(UUID id, ActiveDTO activeDTO);

    void saveFeedback(FeedbacksDTO feedbacksDTO, MultipartFile file);

    void updateFeedback(UUID id, FeedbacksDTO feedbacksDTO, MultipartFile file);

    void deleteById(UUID id);
}
