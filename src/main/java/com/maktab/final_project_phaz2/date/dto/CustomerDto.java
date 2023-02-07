package com.maktab.final_project_phaz2.date.dto;

import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private String name;

    private String family;

    private String emailAddress;

    private String password;

    private Role role;

    private double amount;
}
