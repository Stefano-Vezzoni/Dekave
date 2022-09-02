package com.dekaveenvelopamentos.dekave.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Services;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.ServicesDTO;
import com.dekaveenvelopamentos.dekave.service.ServiceService;

@RestController
@RequestMapping("/api/v1")
public class ServiceController {

    @Autowired
    private ServiceService service;

    @GetMapping("/services")
    @ResponseStatus(HttpStatus.OK)
    public Services getServiceById(@RequestHeader UUID id) {
        return service.getById(id);
    }

    @GetMapping("/services/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<Services> getServices(@RequestHeader UUID id, @PathVariable Integer page, @PathVariable Integer size) {
        return service.getServicesByServiceTypeId(id, page, size);
    }

    @PostMapping(value = "/services/save", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createService(@RequestHeader UUID id, @RequestPart("services") @Valid ServicesDTO servicesDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.saveService(id, servicesDTO, file);
    }

    @PutMapping("/services/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateService(@RequestHeader UUID id, @RequestPart("service") ServicesDTO servicesDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.updateService(id, servicesDTO, file);
    }

    @PutMapping("/services/active")
    @ResponseStatus(HttpStatus.OK)
    public void activateById(@RequestHeader UUID id, @RequestBody @Valid ActiveDTO activeDTO) {
        service.activeById(id, activeDTO);
    }

    @DeleteMapping("/services/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServiceById(@RequestHeader UUID id) {
        service.deleteById(id);
    }
}
