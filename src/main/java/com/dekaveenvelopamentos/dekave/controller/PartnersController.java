package com.dekaveenvelopamentos.dekave.controller;

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

@RestController
@RequestMapping("/api/v1")
public class PartnersController {

    @Autowired
    private PartnerService service;

    @GetMapping("/partners")
    @ResponseStatus(HttpStatus.OK)
    public Partners getPartnersById(@RequestHeader UUID id) {
        return service.getById(id);
    }

    @GetMapping("/partners/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<Partners> getPartners(@PathVariable Integer page, @PathVariable Integer size) {
        return service.getPartners(page, size);
    }

    @PostMapping(value = "/partners/save", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.CREATED)
    public void createPartners(@RequestPart("partner") @Valid PartnersDTO partnersDTO,
            @RequestPart("file") MultipartFile file) {
        service.savePartner(partnersDTO, file);
    }

    @PutMapping(value = "/partners/update", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePartners(@RequestHeader UUID id, @RequestPart("partner") PartnersDTO partnersDTO,
            @RequestPart("file") MultipartFile file) {
        service.updatePartner(id, partnersDTO, file);
    }

    @PutMapping("/partners/active")
    @ResponseStatus(HttpStatus.OK)
    public void activateById(@RequestHeader UUID id, @RequestBody @Valid ActiveDTO activeDTO) {
        service.activeById(id, activeDTO);
    }

    @DeleteMapping("/partners/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartnersById(@RequestHeader UUID id) {
        service.deleteById(id);
    }
}
