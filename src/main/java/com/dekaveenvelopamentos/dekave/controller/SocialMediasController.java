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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dekaveenvelopamentos.dekave.domain.entity.SocialMedias;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.SocialMediaDTO;
import com.dekaveenvelopamentos.dekave.service.SocialMediaService;

@RestController
@RequestMapping("/")
public class SocialMediasController {

    @Autowired
    private SocialMediaService service;

    @GetMapping("/socialmedias")
    @ResponseStatus(HttpStatus.OK)
    public SocialMedias getSocialMediaById(@RequestHeader UUID id) {
        return service.getById(id);
    }

    @GetMapping("/socialmedias/all")
    @ResponseStatus(HttpStatus.OK)
    public List<SocialMedias> getAllSocialMedias() {
        return service.getAll();
    }

    @PostMapping("/socialmedias/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveSocialMedia(@RequestBody @Valid SocialMediaDTO socialMediaDTO) {
        service.saveSocialMedia(socialMediaDTO);
    }

    @PatchMapping("/socialmedias/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSocialMedia(@RequestHeader UUID id, @RequestBody SocialMediaDTO socialMediaDTO) {
        service.updateSocialMedia(id, socialMediaDTO);
    }

    @PatchMapping("/socialmedias/active")
    @ResponseStatus(HttpStatus.OK)
    public void activateById(@RequestHeader UUID id, @RequestBody @Valid ActiveDTO activeDTO) {
        service.activeById(id, activeDTO);
    }

    @DeleteMapping("/socialmedias/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSocialMediaById(@RequestHeader UUID id) {
        service.deleteById(id);
    }

}
