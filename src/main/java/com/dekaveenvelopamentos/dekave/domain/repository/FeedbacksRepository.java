package com.dekaveenvelopamentos.dekave.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dekaveenvelopamentos.dekave.domain.entity.Feedbacks;

@Repository
public interface FeedbacksRepository extends JpaRepository<Feedbacks, UUID> {

    List<Feedbacks> findAllByOrderByFeedbackOrder();
}
