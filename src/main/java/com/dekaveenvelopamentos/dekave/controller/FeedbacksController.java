package com.dekaveenvelopamentos.dekave.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Feedbacks;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.FeedbacksDTO;
import com.dekaveenvelopamentos.dekave.service.FeedbackService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class FeedbacksController {

    private static final String feedbacksTag = "Feedbacks";

    @Autowired
    private FeedbackService service;

    @Operation(summary = "Get by id.", tags = feedbacksTag)
    @GetMapping("/feedbacks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Feedbacks getFeedbackById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @Operation(summary = "Get all per page and size.", tags = feedbacksTag)
    @GetMapping("/feedbacks/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<Feedbacks> getAllFeedbacks(@PathVariable Integer page, @PathVariable Integer size) {
        return service.getFeedbacks(page, size);
    }

    @Operation(summary = "Save new feedback.", tags = feedbacksTag)
    @PostMapping(value = "/feedbacks/save", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.CREATED)
    public void saveFeedback(@RequestPart("feedback") @Valid FeedbacksDTO feedbacksDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.saveFeedback(feedbacksDTO, file);
    }

    @Operation(summary = "Update by id.", tags = feedbacksTag)
    @PutMapping(value = "/feedbacks/update/{id}", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFeedback(@PathVariable UUID id, @RequestPart("feedback") FeedbacksDTO feedbacksDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        service.updateFeedback(id, feedbacksDTO, file);
    }

    @Operation(summary = "Activate/Disable by id.", tags = feedbacksTag)
    @PutMapping("/feedbacks/active/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void activeById(@PathVariable UUID id, @RequestBody @Valid ActiveDTO activeDTO) {
        service.activeById(id, activeDTO);
    }

    @Operation(summary = "Delete by id.", tags = feedbacksTag)
    @DeleteMapping("/feedbacks/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeedbackById(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
