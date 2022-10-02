package com.dekaveenvelopamentos.dekave.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Partners;
import com.dekaveenvelopamentos.dekave.domain.repository.PartnersRepository;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.PartnersDTO;
import com.dekaveenvelopamentos.dekave.exception.ReorderActionException;
import com.dekaveenvelopamentos.dekave.exception.ReorderPositionException;

@Service
public class PartnerService {

    @Value("${images.folder.partners}")
    private String path;

    @Autowired
    private PartnersRepository repository;

    @Autowired
    private GenericService genericService;

    public Partners getById(UUID id) {
        return repository.findById(id).get();
    }

    public List<Partners> getPartners(Integer page, Integer size) {

        Pageable pageable = genericService.pageableAndSort(page, size, "partnerOrder");

        return repository.findAll(pageable).getContent();
    }

    @Transactional
    public void activeById(UUID id, ActiveDTO activeDTO) {

        Partners partner = repository.getById(id);

        if (activeDTO.getActive() == true) {
            partner.setActive(true);
        }
        if (activeDTO.getActive() == false) {
            partner.setActive(false);
        }
    }

    public ResponseEntity<?> getImageById(String fileName) throws IOException {
        return genericService.getImageByFileName(path + fileName);
    }

    @Transactional
    public void savePartner(PartnersDTO partnersDTO, MultipartFile file) throws IOException {

        Partners partner = new Partners();

        partner.setName(partnersDTO.getName());
        partner.setActive(true);
        partner.setPartnerOrder(repository.count() + 1);
        partner.setLogo(genericService.uploadImage(path, file));

        repository.save(partner);
    }

    @Transactional
    public void updatePartner(UUID id, PartnersDTO partnersDTO, MultipartFile file) throws IOException {

        Partners partner = repository.getById(id);

        if (partnersDTO.getName() != null) {
            partner.setName(partnersDTO.getName());
        }
        if (file != null) {
            genericService.deleteFile(partner.getLogo());
            partner.setLogo(genericService.uploadImage(path, file));
        }
    }

    @Transactional
    public void reorder(Long currentPosition, String action) throws ReorderPositionException, ReorderActionException {

        Partners currentPartner = repository.findByPartnerOrder(currentPosition);
        Partners nextPartner = repository.findByPartnerOrder(currentPosition + 1);
        Partners previousPartner = repository.findByPartnerOrder(currentPosition - 1);

        if (action.equalsIgnoreCase("down") && action.equalsIgnoreCase("up")) {
            throw new ReorderActionException();

        } else if (action.equalsIgnoreCase("down")) {
            reorderDown(currentPartner, nextPartner, currentPosition);

        } else if (action.equalsIgnoreCase("up")) {
            if (currentPosition == 1) {
                throw new ReorderPositionException();
            }
            reorderUp(currentPartner, previousPartner, currentPosition);
        }
    }

    @Transactional
    public void deleteById(UUID id) {

        genericService.deleteFile(repository.findById(id).get().getLogo());
        repository.deleteById(id);

        Long index = 1L;
        List<Partners> partners = repository.findAll(Sort.by("partnerOrder"));

        for (Partners partner : partners) {
            partner.setPartnerOrder(index++);
        }
    }

    @Transactional
    public void reorderDown(Partners current, Partners next, Long currentPosition) throws ReorderPositionException {

        if (next != null) {
            // resetting positions
            current.setPartnerOrder(-1L);
            next.setPartnerOrder(-2L);

            current.setPartnerOrder(currentPosition + 1);
            next.setPartnerOrder(currentPosition);
        } else {
            throw new ReorderPositionException();
        }

    }

    @Transactional
    public void reorderUp(Partners current, Partners previous, Long currentPosition) {
        // resetting positions
        current.setPartnerOrder(-1L);
        previous.setPartnerOrder(-2L);

        current.setPartnerOrder(currentPosition - 1);
        previous.setPartnerOrder(currentPosition);
    }
}
