package com.dekaveenvelopamentos.dekave.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Services;
import com.dekaveenvelopamentos.dekave.domain.repository.ServiceTypesRepository;
import com.dekaveenvelopamentos.dekave.domain.repository.ServicesRepository;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.ServicesDTO;
import com.dekaveenvelopamentos.dekave.service.FileService;
import com.dekaveenvelopamentos.dekave.service.ServiceService;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Value("${images.folder.services}")
    private String path;

    @Autowired
    private ServicesRepository repository;

    @Autowired
    private ServiceTypesRepository serviceTypesRepository;

    @Autowired
    private FileService fileService;

    @Override
    public Services getById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Services> findAllByServiceTypeId(UUID serviceTypeId) {
        return repository.findAllByServiceTypeId(serviceTypeId);
    }

    @Override
    public void activeById(UUID id, ActiveDTO activeDTO) {

        Services service = repository.getById(id);

        if (activeDTO.getActive() == true) {
            service.setActive(true);
            repository.save(service);
        }
        if (activeDTO.getActive() == false) {
            service.setActive(false);
            repository.save(service);
        }
    }

    @Override
    @Transactional
    public void saveService(UUID serviceTypeId, ServicesDTO servicesDTO, MultipartFile file) {

        Services service = new Services();

        service.setTitle(servicesDTO.getTitle());
        service.setResume(servicesDTO.getResume());
        service.setActive(true);
        service.setServiceType(serviceTypesRepository.findById(serviceTypeId).get());
        service.setServiceOrder(
                repository.countByServiceType(serviceTypesRepository.findById(serviceTypeId).get()) + 1);
        service.setPhoto(fileService.uploadImage(path, file));

        repository.save(service);

    }

    @Override
    @Transactional
    public void updateService(UUID id, ServicesDTO servicesDTO, MultipartFile file) {

        Services service = repository.getById(id);

        if (servicesDTO.getTitle() != null) {
            service.setTitle(servicesDTO.getTitle());
        }
        if (servicesDTO.getResume() != null) {
            service.setResume(servicesDTO.getResume());
        }
        if (file != null) {
            fileService.deleteFile(service.getPhoto());

            service.setPhoto(fileService.uploadImage(path, file));
        }

        repository.save(service);
    }

    @Override
    public void deleteById(UUID id) {

        fileService.deleteFile(repository.findById(id).get().getPhoto());
        repository.deleteById(id);

        Long index = 1L;
        List<Services> services = repository.findAllByOrderByServiceOrder();
        for (Services service : services) {
            service.setServiceOrder(index++);
        }
        repository.saveAll(services);
    }

}
