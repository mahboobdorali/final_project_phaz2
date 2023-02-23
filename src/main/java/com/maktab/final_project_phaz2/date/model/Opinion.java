package com.maktab.final_project_phaz2.date.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class Opinion extends BaseEntity {
    @ManyToOne
    private Expert expert;

    @Column(nullable = false)
    /* @Size(max=5,min = 1)*/
    private long score;

    private String comment;
}
