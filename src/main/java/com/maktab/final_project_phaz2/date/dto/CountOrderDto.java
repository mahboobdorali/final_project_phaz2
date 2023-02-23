package com.maktab.final_project_phaz2.date.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountOrderDto {
    @Email
    private String emailAddress;

    private CurrentSituation currentSituation;
}
