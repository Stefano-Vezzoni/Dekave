package com.dekaveenvelopamentos.dekave.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dekaveenvelopamentos.dekave.domain.entity.DekaveData;

public interface DekaveDataRepository extends JpaRepository<DekaveData, UUID> {

}
