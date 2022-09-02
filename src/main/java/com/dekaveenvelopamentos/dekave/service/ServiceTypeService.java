package com.dekaveenvelopamentos.dekave.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.ServiceTypes;
import com.dekaveenvelopamentos.dekave.domain.repository.ServiceTypesRepository;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.ServiceTypesDTO;

@Service
public class ServiceTypeService {

    @Value("${images.folder.servicetypes}")
    private String path;

    @Autowired
    private ServiceTypesRepository repository;

    @Autowired
    private GenericService genericService;

    public ServiceTypes getById(UUID id) {
        return repository.findById(id).get();
    }

    public List<ServiceTypes> getServiceTypes(Integer page, Integer size) {

        Pageable pageable = genericService.pageableAndSort(page, size, "serviceTypeOrder");

        return repository.findAll(pageable).getContent();
    }

    @Transactional
    public void activeById(UUID id, ActiveDTO activeDTO) {

        ServiceTypes serviceType = repository.getById(id);

        if (activeDTO.getActive() == true) {
            serviceType.setActive(true);
        }
        if (activeDTO.getActive() == false) {
            serviceType.setActive(false);
        }
    }

    @Transactional
    public void saveServiceType(ServiceTypesDTO serviceTypesDTO, MultipartFile file) throws IOException {

        ServiceTypes serviceTypes = new ServiceTypes();

        serviceTypes.setTitle(serviceTypesDTO.getTitle());
        serviceTypes.setActive(true);
        serviceTypes.setServiceTypeOrder(repository.count() + 1);
        serviceTypes.setPhoto(genericService.uploadImage(path, file));

        repository.save(serviceTypes);

    }

    @Transactional
    public void updateServiceType(UUID id, ServiceTypesDTO serviceTypesDTO, MultipartFile file) throws IOException {

        ServiceTypes serviceType = repository.getById(id);

        if (serviceTypesDTO.getTitle() != null) {
            serviceType.setTitle(serviceTypesDTO.getTitle());
        }
        if (file != null) {
            genericService.deleteFile(serviceType.getPhoto());
            serviceType.setPhoto(genericService.uploadImage(path, file));
        }
    }

    @Transactional
    public void deleteById(UUID id) {

        genericService.deleteFile(repository.findById(id).get().getPhoto());
        repository.deleteById(id);

        Long index = 1L;
        Sort sort = genericService.sort("asc", "serviceTypeOrder");
        List<ServiceTypes> serviceTypes = repository.findAll(sort);

        for (ServiceTypes serviceType : serviceTypes) {
            serviceType.setServiceTypeOrder(index++);
        }
    }
}
