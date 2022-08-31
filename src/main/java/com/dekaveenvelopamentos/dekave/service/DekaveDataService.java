package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import com.dekaveenvelopamentos.dekave.domain.entity.DekaveData;
import com.dekaveenvelopamentos.dekave.dto.DataDTO;

public interface DekaveDataService {

    DekaveData getById(UUID id);

    List<DekaveData> getAll();

    void saveDekaveData(DataDTO dataDTO);

    void updateDekaveData(UUID id, DataDTO dataDTO);

    void deleteById(UUID id);
}
