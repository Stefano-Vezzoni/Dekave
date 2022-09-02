package com.dekaveenvelopamentos.dekave.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dekaveenvelopamentos.dekave.domain.entity.ContactNumbers;
import com.dekaveenvelopamentos.dekave.dto.ContactNumberDTO;
import com.dekaveenvelopamentos.dekave.service.ContactNumberService;

@RestController
@RequestMapping("/api/v1")
public class ContactNumbersController {

    @Autowired
    private ContactNumberService service;

    @GetMapping("/contactnumbers")
    @ResponseStatus(HttpStatus.OK)
    public ContactNumbers getContactNumberById(@RequestHeader UUID id) {
        return service.getById(id);
    }

    @GetMapping("/contactnumbers/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactNumbers> getAllContactNumbers(@PathVariable Integer page, @PathVariable Integer size) {
        return service.getContactNumbers(page, size);
    }

    @PostMapping("/contactnumbers/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveContactNumber(@RequestBody @Valid ContactNumberDTO contactNumberDTO) {
        service.saveContactNumber(contactNumberDTO);
    }

    @PutMapping("/contactnumbers/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateContactNumber(@RequestHeader UUID id, @RequestBody ContactNumberDTO contactNumberDTO) {
        service.updateContactNumber(id, contactNumberDTO);
    }

    @DeleteMapping("/contactnumbers/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContactNumberById(@RequestHeader UUID id) {
        service.deleteById(id);
    }
}
