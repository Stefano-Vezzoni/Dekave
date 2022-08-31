package com.dekaveenvelopamentos.dekave.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dekaveenvelopamentos.dekave.domain.entity.SocialMedias;
import com.dekaveenvelopamentos.dekave.domain.repository.SocialMediasRepository;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.SocialMediaDTO;
import com.dekaveenvelopamentos.dekave.service.SocialMediaService;

@Service
public class SocialMediaServiceImpl implements SocialMediaService {

    @Autowired
    private SocialMediasRepository repository;

    @Override
    public SocialMedias getById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public List<SocialMedias> getAll() {
        return repository.findAll();
    }

    @Override
    public void activeById(UUID id, ActiveDTO activeDTO) {

        SocialMedias socialMedia = repository.getById(id);

        if (activeDTO.getActive() == true) {
            socialMedia.setActive(true);
            repository.save(socialMedia);
        }
        if (activeDTO.getActive() == false) {
            socialMedia.setActive(false);
            repository.save(socialMedia);
        }
    }

    @Override
    public void saveSocialMedia(SocialMediaDTO socialMediaDTO) {

        SocialMedias socialMedias = new SocialMedias();

        socialMedias.setName(socialMediaDTO.getName());
        socialMedias.setIcon(socialMediaDTO.getIcon());
        socialMedias.setUrl(socialMediaDTO.getUrl());
        socialMedias.setActive(true);

        repository.save(socialMedias);
    }

    @Override
    public void updateSocialMedia(UUID id, SocialMediaDTO socialMediaDTO) {

        SocialMedias socialMedia = repository.getById(id);

        if (socialMediaDTO.getName() != null) {
            socialMedia.setName(socialMediaDTO.getName());
        }
        if (socialMediaDTO.getIcon() != null) {
            socialMedia.setIcon(socialMediaDTO.getIcon());
        }
        if (socialMediaDTO.getUrl() != null) {
            socialMedia.setUrl(socialMediaDTO.getUrl());
        }

        repository.save(socialMedia);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
