package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import com.dekaveenvelopamentos.dekave.domain.entity.ContactNumbers;
import com.dekaveenvelopamentos.dekave.dto.ContactNumberDTO;

public interface ContactNumberService {

    ContactNumbers getById(UUID id);

    List<ContactNumbers> getAll();

    void saveContactNumber(ContactNumberDTO contactNumberDTO);

    void updateContactNumber(UUID id, ContactNumberDTO contactNumberDTO);

    void deleteById(UUID id);
}
