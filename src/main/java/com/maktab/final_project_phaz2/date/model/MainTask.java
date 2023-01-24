package com.maktab.final_project_phaz2.date.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;
@Setter
@Getter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainTask extends BaseEntity {

    @Column(unique = true)
    private String name;

}
