package com.dekaveenvelopamentos.dekave.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dekaveenvelopamentos.dekave.domain.entity.Posts;
import com.dekaveenvelopamentos.dekave.domain.entity.Services;

@Repository
public interface PostsRepository extends JpaRepository<Posts, UUID> {

    @Query("from Posts p where p.service.id = :id order by postsOrder")
    List<Posts> findAllByServiceIdOrdered(@Param("id") UUID serviceId);

    Page<Posts> findAllByServiceId(UUID serviceId, Pageable pageable);

    Long countByService(Services service);

    @Query("from Posts p where p.service.id = :id and p.postsOrder = :order")
    Posts findByServiceIdAndPosition(@Param("id") UUID serviceId, @Param("order") Long postsOrder);

    Posts findByPhoto(String avatar);
}
