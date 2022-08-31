package com.dekaveenvelopamentos.dekave.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dekaveenvelopamentos.dekave.domain.entity.Partners;

@Repository
public interface PartnersRepository extends JpaRepository<Partners, UUID> {

    List<Partners> findAllByOrderByPartnerOrder();
}
