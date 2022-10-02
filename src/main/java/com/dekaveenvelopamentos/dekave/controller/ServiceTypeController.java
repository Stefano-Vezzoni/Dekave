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

import com.dekaveenvelopamentos.dekave.domain.entity.ServiceTypes;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.ServiceTypesDTO;
import com.dekaveenvelopamentos.dekave.exception.ReorderActionException;
import com.dekaveenvelopamentos.dekave.exception.ReorderPositionException;
import com.dekaveenvelopamentos.dekave.service.ServiceTypeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class ServiceTypeController {

    private static final String serviceTypesTag = "Service Types";

    @Autowired
    private ServiceTypeService service;

    @Operation(summary = "Get by id.", tags = serviceTypesTag)
    @GetMapping("/servicetypes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceTypes getServiceTypesById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @Operation(summary = "Get all per page and size.", tags = serviceTypesTag)
    @GetMapping("/servicetypes")
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceTypes> getAllServiceTypes(@RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "50") Integer size) {
        return service.getServiceTypes(page, size);
    }

    @Operation(summary = "Get image by id.", tags = serviceTypesTag)
    @GetMapping("/servicetypes/image/{id}")
    public ResponseEntity<?> getImageById(@PathVariable UUID id) throws IOException {
        return service.getImageById(id);
    }

    @Operation(summary = "Save new service type.", tags = serviceTypesTag)
    @PostMapping(value = "/servicetypes/save", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveServiceType(@RequestPart("serviceType") @Valid ServiceTypesDTO serviceTypesDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.saveServiceType(serviceTypesDTO, file);
    }

    @Operation(summary = "Update by id.", tags = serviceTypesTag)
    @PutMapping("/servicetypes/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateServiceType(@PathVariable UUID id, @RequestPart("serviceType") ServiceTypesDTO serviceTypesDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.updateServiceType(id, serviceTypesDTO, file);
    }

    @Operation(summary = "Activate/Disable by id.", tags = serviceTypesTag)
    @PutMapping("/servicetypes/active/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void activeById(@PathVariable UUID id, @RequestBody @Valid ActiveDTO activeDTO) {
        service.activeById(id, activeDTO);
    }

    @Operation(summary = "Reorder.", tags = serviceTypesTag)
    @PutMapping("/servicetypes/reorder")
    @ResponseStatus(HttpStatus.OK)
    public void reorder(@RequestParam Long currentPosition, @RequestParam String action)
            throws ReorderPositionException, ReorderActionException {
        service.reorder(currentPosition, action);
    }

    @Operation(summary = "Delete by id.", tags = serviceTypesTag)
    @DeleteMapping("/servicetypes/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServiceTypeById(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
