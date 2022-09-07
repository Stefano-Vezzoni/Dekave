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

import com.dekaveenvelopamentos.dekave.domain.entity.Partners;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.PartnersDTO;
import com.dekaveenvelopamentos.dekave.service.PartnerService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class PartnersController {

    private static final String partnersTag = "Partners";

    @Autowired
    private PartnerService service;

    @Operation(summary = "Get by id.", tags = partnersTag)
    @GetMapping("/partners")
    @ResponseStatus(HttpStatus.OK)
    public Partners getPartnerById(@RequestHeader UUID id) {
        return service.getById(id);
    }

    @Operation(summary = "Get all per page and size.", tags = partnersTag)
    @GetMapping("/partners/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<Partners> getAllPartners(@PathVariable Integer page, @PathVariable Integer size) {
        return service.getPartners(page, size);
    }

    @Operation(summary = "Save new partner.", tags = partnersTag)
    @PostMapping(value = "/partners/save", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.CREATED)
    public void savePartner(@RequestPart("partner") @Valid PartnersDTO partnersDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.savePartner(partnersDTO, file);
    }

    @Operation(summary = "Update by id.", tags = partnersTag)
    @PutMapping(value = "/partners/update", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePartner(@RequestHeader UUID id, @RequestPart("partner") PartnersDTO partnersDTO,
            @RequestPart("file") MultipartFile file) throws IOException {
        service.updatePartner(id, partnersDTO, file);
    }

    @Operation(summary = "Activate/Disable by id.", tags = partnersTag)
    @PutMapping("/partners/active")
    @ResponseStatus(HttpStatus.OK)
    public void activeById(@RequestHeader UUID id, @RequestBody @Valid ActiveDTO activeDTO) {
        service.activeById(id, activeDTO);
    }

    @Operation(summary = "Delete by id.", tags = partnersTag)
    @DeleteMapping("/partners/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartnerById(@RequestHeader UUID id) {
        service.deleteById(id);
    }
}
