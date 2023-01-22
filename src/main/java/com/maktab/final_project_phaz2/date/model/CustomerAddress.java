package com.maktab.final_project_phaz2.date.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
public class CustomerAddress {

    private String street;

    private String city;

    private String plaque;
}
