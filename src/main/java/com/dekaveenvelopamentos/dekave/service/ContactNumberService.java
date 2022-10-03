package com.dekaveenvelopamentos.dekave.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dekaveenvelopamentos.dekave.domain.entity.ContactNumbers;
import com.dekaveenvelopamentos.dekave.domain.repository.ContactNumbersRepository;
import com.dekaveenvelopamentos.dekave.dto.ContactNumberDTO;

@Service
public class ContactNumberService {

    @Autowired
    private ContactNumbersRepository repository;

    @Autowired
    private GenericService genericService;

    public ContactNumbers getById(UUID id) {
        return repository.findById(id).get();
    }

    public List<ContactNumbers> getContactNumbers(Integer page, Integer size) {

        Pageable pageable = genericService.pageable(page, size);

        return repository.findAll(pageable).getContent();
    }

    @Transactional
    public void saveContactNumber(ContactNumberDTO contactNumberDTO) {

        ContactNumbers contactNumbers = new ContactNumbers();

        BeanUtils.copyProperties(contactNumberDTO, contactNumbers);

        repository.save(contactNumbers);
    }

    @Transactional
    public void updateContactNumber(UUID id, ContactNumberDTO contactNumberDTO) {

        ContactNumbers contactNumber = repository.getById(id);

        if (contactNumberDTO.getPhone() != null) {
            contactNumber.setPhone(contactNumberDTO.getPhone());
        }
        if (contactNumberDTO.isWhatsapp()) {
            contactNumber.setWhatsapp(contactNumberDTO.isWhatsapp());
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
