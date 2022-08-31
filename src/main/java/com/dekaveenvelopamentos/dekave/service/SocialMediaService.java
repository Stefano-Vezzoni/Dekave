package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import com.dekaveenvelopamentos.dekave.domain.entity.SocialMedias;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.SocialMediaDTO;

public interface SocialMediaService {

    SocialMedias getById(UUID id);

    List<SocialMedias> getAll();

    void activeById(UUID id, ActiveDTO activeDTO);

    void saveSocialMedia(SocialMediaDTO socialMediaDTO);

    void updateSocialMedia(UUID id, SocialMediaDTO socialMediaDTO);

    void deleteById(UUID id);
}
