package com.dekaveenvelopamentos.dekave.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dekaveenvelopamentos.dekave.domain.entity.Posts;
import com.dekaveenvelopamentos.dekave.domain.entity.Services;

@Repository
public interface PostsRepository extends JpaRepository<Posts, UUID> {

    @Query("from Posts p where p.service.id = :id order by postsOrder")
    List<Posts> findAllByServiceId(UUID id);

    Long countByService(Services service);

    List<Posts> findAllByOrderByPostsOrder();
}
