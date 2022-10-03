package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dekaveenvelopamentos.dekave.domain.entity.SocialMedias;
import com.dekaveenvelopamentos.dekave.domain.repository.SocialMediasRepository;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.SocialMediaDTO;

@Service
public class SocialMediaService {

    @Autowired
    private SocialMediasRepository repository;

    @Autowired
    private GenericService genericService;

    public SocialMedias getById(UUID id) {
        return repository.findById(id).get();
    }

    public List<SocialMedias> getSocialMedias(Integer page, Integer size) {

        Pageable pageable = genericService.pageable(page, size);

        return repository.findAll(pageable).getContent();
    }

    @Transactional
    public void activeById(UUID id, ActiveDTO activeDTO) {

        SocialMedias socialMedia = repository.getById(id);

        if (activeDTO.getActive() == true) {
            socialMedia.setActive(true);
        }
        if (activeDTO.getActive() == false) {
            socialMedia.setActive(false);
        }
    }

    @Transactional
    public void saveSocialMedia(SocialMediaDTO socialMediaDTO) {

        SocialMedias socialMedia = new SocialMedias();

        BeanUtils.copyProperties(socialMediaDTO, socialMedia);
        socialMedia.setActive(true);

        repository.save(socialMedia);
    }

    @Transactional
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
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
