package com.maktab.final_project_phaz2.date.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class UnderService extends BaseEntity {
    @Column(unique = true)
    private String nameSubService;

    private double basePrice;

    private String briefExplanation;

    @ManyToOne(fetch = FetchType.LAZY)
    private MainTask MainTask;
}
