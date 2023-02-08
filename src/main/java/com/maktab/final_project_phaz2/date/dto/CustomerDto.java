package com.maktab.final_project_phaz2.date.dto;

import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    @Pattern(regexp = "^[A-Za-z]{3,29}$")
    private String name;

    @Pattern(regexp = "^[A-Za-z]{3,29}$")
    private String family;

    @Email
    private String emailAddress;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8}$")
    private String password;

    private double amount;
}
