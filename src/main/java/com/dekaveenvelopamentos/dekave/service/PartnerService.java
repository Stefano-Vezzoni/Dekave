package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Partners;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.PartnersDTO;

public interface PartnerService {

    Partners getById(UUID id);

    List<Partners> findAll();

    void activeById(UUID id, ActiveDTO activeDTO);

    void savePartner(PartnersDTO partnersDTO, MultipartFile file);

    void updatePartner(UUID id, PartnersDTO partnersDTO, MultipartFile file);

    void deleteById(UUID id);
}
