package com.maktab.final_project_phaz2.date.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Email
    private String emailAddress;

    @Pattern(regexp = "^[A-Za-z]{3,29}$")
    private String name;

    @Pattern(regexp = "^[A-Za-z]{3,29}$")
    private String family;
}
