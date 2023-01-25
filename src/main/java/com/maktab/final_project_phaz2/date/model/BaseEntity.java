package com.maktab.final_project_phaz2.date.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public BaseEntity(Long id) {
        this.id = id;
    }

    public BaseEntity() {
    }
}
