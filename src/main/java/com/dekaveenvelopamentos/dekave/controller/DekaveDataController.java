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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dekaveenvelopamentos.dekave.domain.entity.DekaveData;
import com.dekaveenvelopamentos.dekave.dto.DataDTO;
import com.dekaveenvelopamentos.dekave.service.DekaveDataService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class DekaveDataController {

    private static final String dekaveDataTag = "Dekave Data";

    @Autowired
    private DekaveDataService service;

    @Operation(summary = "Get by id.", tags = dekaveDataTag)
    @GetMapping("/dekavedata/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DekaveData getDekaveDataById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @Operation(summary = "Get all per page and size.", tags = dekaveDataTag)
    @GetMapping("/dekavedata/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<DekaveData> getAllDekaveData(@PathVariable Integer page, @PathVariable Integer size) {
        return service.getDekaveData(page, size);
    }

    @Operation(summary = "Save new dekave data.", tags = dekaveDataTag)
    @PostMapping("/dekavedata/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveDekaveData(@RequestBody @Valid DataDTO dataDTO) {
        service.saveDekaveData(dataDTO);
    }

    @Operation(summary = "Update by id.", tags = dekaveDataTag)
    @PutMapping("/dekavedata/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDekaveData(@PathVariable UUID id, @RequestBody DataDTO dataDTO) {
        service.updateDekaveData(id, dataDTO);
    }

    @Operation(summary = "Delete by id.", tags = dekaveDataTag)
    @DeleteMapping("/dekavedata/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDekaveDataById(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
