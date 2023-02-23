package com.maktab.final_project_phaz2.date.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCustomerFilterDto {

    private CurrentSituation currentSituation;

    private String name;

    private double proposedPrice;

    private String nameSubService;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateAndTimeOfWork;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate beforeTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate afterTime;

}
