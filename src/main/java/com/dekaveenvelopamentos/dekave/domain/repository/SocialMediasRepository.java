package com.dekaveenvelopamentos.dekave.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dekaveenvelopamentos.dekave.domain.entity.SocialMedias;

public interface SocialMediasRepository extends JpaRepository<SocialMedias, UUID> {

}
