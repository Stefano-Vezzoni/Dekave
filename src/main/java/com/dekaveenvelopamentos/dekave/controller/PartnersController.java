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

import com.dekaveenvelopamentos.dekave.domain.entity.Partners;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.PartnersDTO;
import com.dekaveenvelopamentos.dekave.exception.ReorderActionException;
import com.dekaveenvelopamentos.dekave.exception.ReorderPositionException;
import com.dekaveenvelopamentos.dekave.service.PartnerService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/partners")
public class PartnersController {

    final String partnersTag = "Partners";

    @Autowired
    private PartnerService service;

    @Operation(summary = "Get by id.", tags = partnersTag)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Partners getPartnerById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @Operation(summary = "Get all per page and size.", tags = partnersTag)
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Partners> getAllPartners(@RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "50") Integer size) {
        return service.getPartners(page, size);
    }

    @Operation(summary = "Get image by id.", tags = partnersTag)
    @GetMapping("/images/{fileName}")
    public ResponseEntity<?> getImageById(@PathVariable String fileName) throws IOException {
        return service.getImageById(fileName);
    }

    @Operation(summary = "Save new partner.", tags = partnersTag)
    @PostMapping(value = "/save", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.CREATED)
    public void savePartner(@RequestPart("partner") @Valid PartnersDTO partnersDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.savePartner(partnersDTO, file);
    }

    @Operation(summary = "Update by id.", tags = partnersTag)
    @PutMapping(value = "/update/{id}", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePartner(@PathVariable UUID id, @RequestPart("partner") PartnersDTO partnersDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.updatePartner(id, partnersDTO, file);
    }

    @Operation(summary = "Activate/Disable by id.", tags = partnersTag)
    @PutMapping("/active/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void activeById(@PathVariable UUID id, @RequestBody @Valid ActiveDTO activeDTO) {
        service.activeById(id, activeDTO);
    }

    @Operation(summary = "Reorder.", tags = partnersTag)
    @PutMapping("/reorder")
    @ResponseStatus(HttpStatus.OK)
    public void reorder(@RequestParam Long currentPosition, @RequestParam String action)
            throws ReorderPositionException, ReorderActionException {
        service.reorder(currentPosition, action);
    }

    @Operation(summary = "Delete by id.", tags = partnersTag)
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartnerById(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
