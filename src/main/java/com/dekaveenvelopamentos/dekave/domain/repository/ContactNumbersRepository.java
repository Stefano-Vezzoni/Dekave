package com.dekaveenvelopamentos.dekave.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dekaveenvelopamentos.dekave.domain.entity.ContactNumbers;

public interface ContactNumbersRepository extends JpaRepository<ContactNumbers, UUID> {

}
