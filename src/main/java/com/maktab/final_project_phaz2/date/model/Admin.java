package com.maktab.final_project_phaz2.date.model;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@SuperBuilder
public class Admin extends Person {
}
