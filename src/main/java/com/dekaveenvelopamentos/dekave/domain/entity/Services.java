package com.dekaveenvelopamentos.dekave.domain.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "services")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String resume;

    @Column(nullable = false)
    private String photo;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Long serviceOrder;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "service_type_id", referencedColumnName = "id")
    @JsonIgnore
    private ServiceTypes serviceType;

}
