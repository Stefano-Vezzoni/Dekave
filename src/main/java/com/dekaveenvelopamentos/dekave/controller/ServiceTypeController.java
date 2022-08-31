package com.dekaveenvelopamentos.dekave.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.ServiceTypes;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.ServiceTypesDTO;
import com.dekaveenvelopamentos.dekave.service.ServiceTypeService;

@RestController
@RequestMapping("/")
public class ServiceTypeController {

    @Autowired
    private ServiceTypeService service;

    @GetMapping("/servicetypes")
    @ResponseStatus(HttpStatus.OK)
    public ServiceTypes getServiceTypesById(@RequestHeader UUID id) {
        return service.getById(id);
    }

    @GetMapping("/servicetypes/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceTypes> getServicesTypes() {
        return service.findAll();
    }

    @PostMapping(value = "/servicetypes/save", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createServiceType(@RequestPart("serviceType") @Valid ServiceTypesDTO serviceTypesDTO,
            @RequestPart("file") MultipartFile file) {
        service.saveServiceType(serviceTypesDTO, file);
    }

    @PatchMapping("/servicetypes/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateServiceTypes(@RequestHeader UUID id, @RequestPart("serviceType") ServiceTypesDTO serviceTypesDTO,
            @RequestPart("file") MultipartFile file) {
        service.updateServiceType(id, serviceTypesDTO, file);
    }

    @PatchMapping("/servicetypes/active")
    @ResponseStatus(HttpStatus.OK)
    public void activateById(@RequestHeader UUID id, @RequestBody @Valid ActiveDTO activeDTO) {
        service.activeById(id, activeDTO);
    }

    @DeleteMapping("/servicetypes/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServiceTypeById(@RequestHeader UUID id) {
        service.deleteById(id);
    }

}
