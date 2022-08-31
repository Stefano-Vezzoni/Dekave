package com.dekaveenvelopamentos.dekave.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dekaveenvelopamentos.dekave.domain.entity.DekaveData;
import com.dekaveenvelopamentos.dekave.domain.repository.DekaveDataRepository;
import com.dekaveenvelopamentos.dekave.dto.DataDTO;
import com.dekaveenvelopamentos.dekave.service.DekaveDataService;

@Service
public class DekaveDataServiceImpl implements DekaveDataService {

    @Autowired
    private DekaveDataRepository repository;

    @Override
    public DekaveData getById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public List<DekaveData> getAll() {
        return repository.findAll();
    }

    @Override
    public void saveDekaveData(DataDTO dataDTO) {

        DekaveData dekaveData = new DekaveData();

        dekaveData.setName(dataDTO.getName());
        dekaveData.setLocation(dataDTO.getLocation());
        dekaveData.setCopyright(dataDTO.getCopyright());
        dekaveData.setContact_message(dataDTO.getContactMessage());

        repository.save(dekaveData);
    }

    @Override
    public void updateDekaveData(UUID id, DataDTO dataDTO) {

        DekaveData dekaveData = repository.getById(id);

        if (dataDTO.getName() != null) {
            dekaveData.setName(dataDTO.getName());
        }
        if (dataDTO.getLocation() != null) {
            dekaveData.setLocation(dataDTO.getLocation());
        }
        if (dataDTO.getCopyright() != null) {
            dekaveData.setCopyright(dataDTO.getCopyright());
        }
        if (dataDTO.getContactMessage() != null) {
            dekaveData.setContact_message(dataDTO.getContactMessage());
        }

        repository.save(dekaveData);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
