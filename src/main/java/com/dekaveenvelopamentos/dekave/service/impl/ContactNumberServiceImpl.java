package com.dekaveenvelopamentos.dekave.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dekaveenvelopamentos.dekave.domain.entity.ContactNumbers;
import com.dekaveenvelopamentos.dekave.domain.repository.ContactNumbersRepository;
import com.dekaveenvelopamentos.dekave.dto.ContactNumberDTO;
import com.dekaveenvelopamentos.dekave.service.ContactNumberService;

@Service
public class ContactNumberServiceImpl implements ContactNumberService {

    @Autowired
    private ContactNumbersRepository repository;

    @Override
    public ContactNumbers getById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public List<ContactNumbers> getAll() {
        return repository.findAll();
    }

    @Override
    public void saveContactNumber(ContactNumberDTO contactNumberDTO) {

        ContactNumbers contactNumbers = new ContactNumbers();

        contactNumbers.setPhone(contactNumberDTO.getPhone());
        contactNumbers.setWhatsapp(contactNumberDTO.isWhatsapp());

        repository.save(contactNumbers);
    }

    @Override
    public void updateContactNumber(UUID id, ContactNumberDTO contactNumberDTO) {

        ContactNumbers contactNumber = repository.getById(id);

        if (contactNumberDTO.getPhone() != null) {
            contactNumber.setPhone(contactNumberDTO.getPhone());
        }

        Boolean whatsapp = contactNumberDTO.isWhatsapp();
        if (whatsapp != null) {
            contactNumber.setWhatsapp(contactNumberDTO.isWhatsapp());
        }

        repository.save(contactNumber);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
