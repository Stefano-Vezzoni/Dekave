package com.dekaveenvelopamentos.dekave.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.ServiceTypes;
import com.dekaveenvelopamentos.dekave.domain.repository.ServiceTypesRepository;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.ServiceTypesDTO;
import com.dekaveenvelopamentos.dekave.service.FileService;
import com.dekaveenvelopamentos.dekave.service.ServiceTypeService;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    @Value("${images.folder.servicetypes}")
    private String path;

    @Autowired
    private ServiceTypesRepository repository;

    @Autowired
    private FileService fileService;

    @Override
    public List<ServiceTypes> findAll() {
        return repository.findAllByOrderByServiceTypeOrder();
    }

    @Override
    public ServiceTypes getById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public void activeById(UUID id, ActiveDTO activeDTO) {

        ServiceTypes serviceType = repository.getById(id);

        if (activeDTO.getActive() == true) {
            serviceType.setActive(true);
            repository.save(serviceType);
        }
        if (activeDTO.getActive() == false) {
            serviceType.setActive(false);
            repository.save(serviceType);
        }
    }

    @Override
    @Transactional
    public void saveServiceType(ServiceTypesDTO serviceTypesDTO, MultipartFile file) {

        ServiceTypes serviceTypes = new ServiceTypes();

        serviceTypes.setTitle(serviceTypesDTO.getTitle());
        serviceTypes.setActive(true);
        serviceTypes.setServiceTypeOrder(repository.count() + 1);
        serviceTypes.setPhoto(fileService.uploadImage(path, file));

        repository.save(serviceTypes);

    }

    @Override
    @Transactional
    public void updateServiceType(UUID id, ServiceTypesDTO serviceTypesDTO, MultipartFile file) {

        ServiceTypes serviceType = repository.getById(id);

        if (serviceTypesDTO.getTitle() != null) {
            serviceType.setTitle(serviceTypesDTO.getTitle());
        }
        if (file != null) {
            fileService.deleteFile(serviceType.getPhoto());

            serviceType.setPhoto(fileService.uploadImage(path, file));
        }

        repository.save(serviceType);
    }

    @Override
    public void deleteById(UUID id) {

        fileService.deleteFile(repository.findById(id).get().getPhoto());
        repository.deleteById(id);

        Long index = 1L;
        List<ServiceTypes> serviceTypes = repository.findAllByOrderByServiceTypeOrder();
        for (ServiceTypes serviceType : serviceTypes) {
            serviceType.setServiceTypeOrder(index++);
        }
        repository.saveAll(serviceTypes);
    }
}
