package com.dekaveenvelopamentos.dekave.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dekaveenvelopamentos.dekave.domain.entity.ServiceTypes;
import com.dekaveenvelopamentos.dekave.domain.entity.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, UUID> {

    @Query("from Services s where s.serviceType.id = :id order by serviceOrder")
    List<Services> findAllByServiceTypeIdOrdered(@Param("id") UUID serviceTypeId);

    Page<Services> findAllByServiceTypeId(UUID serviceTypeId, Pageable pageable);

    Long countByServiceType(ServiceTypes serviceType);

    @Query("from Services s where s.serviceType.id = :id and s.serviceOrder = :order")
    Services findByServiceTypeIdAndPosition(@Param("id") UUID serviceTypeId, @Param("order") Long serviceOrder);
}
