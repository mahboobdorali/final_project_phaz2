package com.maktab.final_project_phaz2.date.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCustomerDto {

    private double proposedPrice;

    private String jobDescription;

    private Date dateAndTimeOfWork;

    private String street;

    private String city;

    private String plaque;

    private CurrentSituation currentSituation;
}
