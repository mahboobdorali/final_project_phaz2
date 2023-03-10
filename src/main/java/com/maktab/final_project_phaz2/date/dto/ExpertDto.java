package com.maktab.final_project_phaz2.date.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpertDto {
    private double averageScore;

    @Pattern(regexp = "^[A-Za-z]{3,29}$")
    private String name;

    @Pattern(regexp = "^[A-Za-z]{3,29}$")
    private String family;

    @Email
    private String emailAddress;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8}$")
    private String password;

    private double amount;

    private byte[] image;

    private ApprovalStatus approvalStatus;

}

