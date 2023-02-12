package com.maktab.final_project_phaz2.date.dto;

import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchExpertDto {
    private String name;
    private String family;
    private String emailAddress;
    private Role role;
    private double amount;
   // private UnderServiceDto underServiceDto;
    private String nameSubService;
    private double averageScore;
}
