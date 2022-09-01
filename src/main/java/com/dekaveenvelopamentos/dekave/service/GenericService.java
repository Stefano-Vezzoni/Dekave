package com.dekaveenvelopamentos.dekave.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GenericService {

    public Pageable pageable(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        return pageable;
    }

    public Pageable pageableAndSort(Integer page, Integer size, String properties) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(properties));

        return pageable;
    }

    public Sort sort(String direction, String properties) {

        if (direction.equalsIgnoreCase("dsc")) {

            return Sort.by(properties).descending();

        } else {

            return Sort.by(properties).ascending();
        }
    }
}
