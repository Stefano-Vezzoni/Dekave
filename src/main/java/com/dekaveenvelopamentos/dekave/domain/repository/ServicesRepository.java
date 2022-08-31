package com.dekaveenvelopamentos.dekave.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dekaveenvelopamentos.dekave.domain.entity.ServiceTypes;
import com.dekaveenvelopamentos.dekave.domain.entity.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, UUID> {

    List<Services> findAllByOrderByServiceOrder();

    @Query("from Services s where s.serviceType.id = :id order by serviceOrder")
    List<Services> findAllByServiceTypeId(@Param("id") UUID serviceTypeId);

    Long countByServiceType(ServiceTypes serviceType);
}
