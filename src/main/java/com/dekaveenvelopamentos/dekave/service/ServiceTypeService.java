package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.ServiceTypes;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.ServiceTypesDTO;

public interface ServiceTypeService {

    ServiceTypes getById(UUID id);

    List<ServiceTypes> findAll();

    void activeById(UUID id, ActiveDTO activeDTO);

    void saveServiceType(ServiceTypesDTO serviceTypesDTO, MultipartFile file);

    void updateServiceType(UUID id, ServiceTypesDTO serviceTypesDTO, MultipartFile file);

    void deleteById(UUID id);
}
