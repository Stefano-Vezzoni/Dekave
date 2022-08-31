package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Services;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.ServicesDTO;

public interface ServiceService {

    Services getById(UUID id);

    List<Services> findAllByServiceTypeId(UUID serviceTypeId);

    void activeById(UUID id, ActiveDTO activeDTO);

    void saveService(UUID serviceTypeId, ServicesDTO servicesDTO, MultipartFile file);

    void updateService(UUID id, ServicesDTO servicesDTO, MultipartFile file);

    void deleteById(UUID id);
}
