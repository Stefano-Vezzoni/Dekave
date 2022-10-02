package com.dekaveenvelopamentos.dekave.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Services;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.ServicesDTO;
import com.dekaveenvelopamentos.dekave.exception.ReorderActionException;
import com.dekaveenvelopamentos.dekave.exception.ReorderPositionException;
import com.dekaveenvelopamentos.dekave.service.ServiceService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${api.v1}")
public class ServiceController {

    private static final String servicesTag = "Services";

    @Autowired
    private ServiceService service;

    @Operation(summary = "Get by id.", tags = servicesTag)
    @GetMapping("/services/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Services getServiceById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @Operation(summary = "Get all per page and size.", tags = servicesTag)
    @GetMapping("/services")
    @ResponseStatus(HttpStatus.OK)
    public List<Services> getAllServicesByServiceTypeId(@RequestParam UUID id,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "50") Integer size) {
        return service.getServicesByServiceTypeId(id, page, size);
    }

    @Operation(summary = "Get image by id.", tags = servicesTag)
    @GetMapping("/images/services/{fileName}")
    public ResponseEntity<?> getImageById(@PathVariable String fileName) throws IOException {
        return service.getImageById(fileName);
    }

    @Operation(summary = "Save new service.", tags = servicesTag)
    @PostMapping(value = "/services/save/{id}", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveService(@PathVariable UUID id, @RequestPart("services") @Valid ServicesDTO servicesDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.saveService(id, servicesDTO, file);
    }

    @Operation(summary = "Update by id.", tags = servicesTag)
    @PutMapping("/services/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateService(@PathVariable UUID id, @RequestPart("service") ServicesDTO servicesDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.updateService(id, servicesDTO, file);
    }

    @Operation(summary = "Activate/Disable by id.", tags = servicesTag)
    @PutMapping("/services/active/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void activeById(@PathVariable UUID id, @RequestBody @Valid ActiveDTO activeDTO) {
        service.activeById(id, activeDTO);
    }

    @Operation(summary = "Reorder.", tags = servicesTag)
    @PutMapping("/services/reorder")
    @ResponseStatus(HttpStatus.OK)
    public void reorder(@RequestParam UUID id, @RequestParam Long currentPosition,
            @RequestParam String action)
            throws ReorderPositionException, ReorderActionException {
        service.reorder(id, currentPosition, action);
    }

    @Operation(summary = "Delete by id.", tags = servicesTag)
    @DeleteMapping("/services/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServiceById(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
