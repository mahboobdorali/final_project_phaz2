package com.maktab.final_project_phaz2.date.model;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private MainTask MainTask;

}
