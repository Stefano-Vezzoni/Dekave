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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dekaveenvelopamentos.dekave.domain.entity.DekaveData;
import com.dekaveenvelopamentos.dekave.dto.DataDTO;
import com.dekaveenvelopamentos.dekave.service.DekaveDataService;

@RestController
@RequestMapping("/")
public class DekaveDataController {

    @Autowired
    private DekaveDataService service;

    @GetMapping("/dekavedata")
    @ResponseStatus(HttpStatus.OK)
    public DekaveData getDekaveDataById(@RequestHeader UUID id) {
        return service.getById(id);
    }

    @GetMapping("/dekavedata/all")
    @ResponseStatus(HttpStatus.OK)
    public List<DekaveData> getAllDekaveData() {
        return service.getAll();
    }

    @PostMapping("/dekavedata/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveDekaveData(@RequestBody @Valid DataDTO dataDTO) {
        service.saveDekaveData(dataDTO);
    }

    @PatchMapping("/dekavedata/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDekaveData(@RequestHeader UUID id, @RequestBody DataDTO dataDTO) {
        service.updateDekaveData(id, dataDTO);
    }

    @DeleteMapping("/dekavedata/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDekaveDataById(@RequestHeader UUID id) {
        service.deleteById(id);
    }

}
