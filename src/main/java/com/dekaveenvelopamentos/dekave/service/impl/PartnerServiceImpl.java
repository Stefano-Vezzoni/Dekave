package com.dekaveenvelopamentos.dekave.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.domain.entity.Partners;
import com.dekaveenvelopamentos.dekave.domain.repository.PartnersRepository;
import com.dekaveenvelopamentos.dekave.dto.ActiveDTO;
import com.dekaveenvelopamentos.dekave.dto.PartnersDTO;
import com.dekaveenvelopamentos.dekave.service.FileService;
import com.dekaveenvelopamentos.dekave.service.PartnerService;

@Service
public class PartnerServiceImpl implements PartnerService {

    @Value("${images.folder.partners}")
    private String path;

    @Autowired
    private PartnersRepository repository;

    @Autowired
    private FileService fileService;

    @Override
    public List<Partners> findAll() {
        return repository.findAllByOrderByPartnerOrder();
    }

    @Override
    public Partners getById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public void activeById(UUID id, ActiveDTO activeDTO) {

        Partners partner = repository.getById(id);

        if (activeDTO.getActive() == true) {
            partner.setActive(true);
            repository.save(partner);
        }
        if (activeDTO.getActive() == false) {
            partner.setActive(false);
            repository.save(partner);
        }
    }

    @Override
    @Transactional
    public void savePartner(PartnersDTO partnersDTO, MultipartFile file) {

        Partners partner = new Partners();

        partner.setName(partnersDTO.getName());
        partner.setActive(true);
        partner.setPartnerOrder(repository.count() + 1);
        partner.setLogo(fileService.uploadImage(path, file));

        repository.save(partner);
    }

    @Override
    @Transactional
    public void updatePartner(UUID id, PartnersDTO partnersDTO, MultipartFile file) {

        Partners partner = repository.getById(id);

        if (partnersDTO.getName() != null) {
            partner.setName(partnersDTO.getName());
        }
        if (file != null) {
            fileService.deleteFile(partner.getLogo());

            partner.setLogo(fileService.uploadImage(path, file));
        }

        repository.save(partner);
    }

    @Override
    public void deleteById(UUID id) {

        fileService.deleteFile(repository.findById(id).get().getLogo());
        repository.deleteById(id);

        Long index = 1L;
        List<Partners> partners = repository.findAllByOrderByPartnerOrder();
        for (Partners partner : partners) {
            partner.setPartnerOrder(index++);
        }
        repository.saveAll(partners);
    }

}
