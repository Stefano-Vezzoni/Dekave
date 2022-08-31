package com.dekaveenvelopamentos.dekave.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Feedbacks;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.FeedbacksDTO;
import com.dekaveenvelopamentos.dekave.service.FeedbackService;

@RestController
@RequestMapping("/")
public class FeedbacksController {

    @Autowired
    private FeedbackService service;

    @GetMapping("/feedbacks")
    @ResponseStatus(HttpStatus.OK)
    public Feedbacks getFeedbackById(@RequestHeader UUID id) {
        return service.getById(id);
    }

    @GetMapping("/feedbacks/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Feedbacks> getFeedbacks() {
        return service.findAll();
    }

    @PostMapping(value = "/feedbacks/save", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.CREATED)
    public void saveFeedback(@RequestPart("feedback") @Valid FeedbacksDTO feedbacksDTO,
            @RequestPart("file") MultipartFile file) {
        service.saveFeedback(feedbacksDTO, file);
    }

    @PatchMapping(value = "/feedbacks/update", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFeedback(@RequestHeader UUID id, @RequestPart("feedback") FeedbacksDTO feedbacksDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        service.updateFeedback(id, feedbacksDTO, file);
    }

    @PatchMapping("/feedbacks/active")
    @ResponseStatus(HttpStatus.OK)
    public void activeById(@RequestHeader UUID id, @RequestBody @Valid ActiveDTO activeDTO) {
        service.activeById(id, activeDTO);
    }

    @DeleteMapping("/feedbacks/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeedbackById(@RequestHeader UUID id) {
        service.deleteById(id);
    }

}
