package com.maktab.final_project_phaz2.date.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Image extends BaseEntity {
    @Lob
    private byte[] imageData;

    private String type;
}
