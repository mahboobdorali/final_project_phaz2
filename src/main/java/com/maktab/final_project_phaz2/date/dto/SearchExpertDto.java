package com.maktab.final_project_phaz2.date.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchExpertDto {

    private String name;

    private String family;

    private String emailAddress;

    private Role role;

    private long minScore;

    private long maxScore;

    private double amount;

    private String nameSubService;

    private double averageScore;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beforeTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date afterTime;


}
