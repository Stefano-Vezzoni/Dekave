package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dekaveenvelopamentos.dekave.domain.entity.DekaveData;
import com.dekaveenvelopamentos.dekave.domain.repository.DekaveDataRepository;
import com.dekaveenvelopamentos.dekave.dto.DataDTO;

@Service
public class DekaveDataService {

    @Autowired
    private DekaveDataRepository repository;

    @Autowired
    private GenericService genericService;

    public DekaveData getById(UUID id) {
        return repository.findById(id).get();
    }

    public List<DekaveData> getDekaveData(Integer page, Integer size) {

        Pageable pageable = genericService.pageable(page, size);

        return repository.findAll(pageable).getContent();
    }

    @Transactional
    public void saveDekaveData(DataDTO dataDTO) {

        DekaveData dekaveData = new DekaveData();

        BeanUtils.copyProperties(dataDTO, dekaveData);

        repository.save(dekaveData);
    }

    @Transactional
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
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
