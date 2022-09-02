package com.dekaveenvelopamentos.dekave.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Services;
import com.dekaveenvelopamentos.dekave.domain.repository.ServiceTypesRepository;
import com.dekaveenvelopamentos.dekave.domain.repository.ServicesRepository;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.ServicesDTO;

@Service
public class ServiceService {

    @Value("${images.folder.services}")
    private String path;

    @Autowired
    private ServicesRepository repository;

    @Autowired
    private ServiceTypesRepository serviceTypesRepository;

    @Autowired
    private GenericService genericService;

    public Services getById(UUID id) {
        return repository.findById(id).get();
    }

    public List<Services> getServicesByServiceTypeId(UUID serviceTypeId, Integer page, Integer size) {

        Pageable pageable = genericService.pageableAndSort(page, size, "serviceOrder");

        Page<Services> servicesByServiceTypeId = repository.findAllByServiceTypeId(serviceTypeId, pageable);

        return servicesByServiceTypeId.getContent();
    }

    @Transactional
    public void activeById(UUID id, ActiveDTO activeDTO) {

        Services service = repository.getById(id);

        if (activeDTO.getActive() == true) {
            service.setActive(true);
        }
        if (activeDTO.getActive() == false) {
            service.setActive(false);
        }
    }

    @Transactional
    public void saveService(UUID serviceTypeId, ServicesDTO servicesDTO, MultipartFile file) throws IOException {

        Services service = new Services();

        service.setTitle(servicesDTO.getTitle());
        service.setResume(servicesDTO.getResume());
        service.setActive(true);
        service.setServiceType(serviceTypesRepository.findById(serviceTypeId).get());
        service.setServiceOrder(
                repository.countByServiceType(serviceTypesRepository.findById(serviceTypeId).get()) + 1);
        service.setPhoto(genericService.uploadImage(path, file));

        repository.save(service);

    }

    @Transactional
    public void updateService(UUID id, ServicesDTO servicesDTO, MultipartFile file) throws IOException {

        Services service = repository.getById(id);

        if (servicesDTO.getTitle() != null) {
            service.setTitle(servicesDTO.getTitle());
        }
        if (servicesDTO.getResume() != null) {
            service.setResume(servicesDTO.getResume());
        }
        if (file != null) {
            genericService.deleteFile(service.getPhoto());
            service.setPhoto(genericService.uploadImage(path, file));
        }
    }

    @Transactional
    public void deleteById(UUID id) {

        // Get serviceType ID to re-order after delete.
        UUID serviceTypeId = repository.findById(id).get().getServiceType().getId();

        genericService.deleteFile(repository.findById(id).get().getPhoto());
        repository.deleteById(id);

        Long index = 1L;
        List<Services> services = repository.findAllByServiceTypeIdOrdered(serviceTypeId);

        for (Services service : services) {
            service.setServiceOrder(index++);
        }
    }
}
