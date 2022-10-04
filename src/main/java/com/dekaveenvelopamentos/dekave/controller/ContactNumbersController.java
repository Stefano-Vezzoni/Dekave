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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dekaveenvelopamentos.dekave.domain.entity.ContactNumbers;
import com.dekaveenvelopamentos.dekave.dto.ContactNumberDTO;
import com.dekaveenvelopamentos.dekave.service.ContactNumberService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/contactnumbers")
public class ContactNumbersController {

    final String contactNumbersTag = "Contact Numbers";

    @Autowired
    private ContactNumberService service;

    @Operation(summary = "Get by id.", tags = contactNumbersTag)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactNumbers getContactNumberById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @Operation(summary = "Get all per page and size.", tags = contactNumbersTag)
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ContactNumbers> getAllContactNumbers(@RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "50") Integer size) {
        return service.getContactNumbers(page, size);
    }

    @Operation(summary = "Save new contact number.", tags = contactNumbersTag)
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveContactNumber(@RequestBody @Valid ContactNumberDTO contactNumberDTO) {
        service.saveContactNumber(contactNumberDTO);
    }

    @Operation(summary = "Update by id.", tags = contactNumbersTag)
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateContactNumber(@PathVariable UUID id, @RequestBody ContactNumberDTO contactNumberDTO) {
        service.updateContactNumber(id, contactNumberDTO);
    }

    @Operation(summary = "Delete by id.", tags = contactNumbersTag)
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContactNumberById(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
