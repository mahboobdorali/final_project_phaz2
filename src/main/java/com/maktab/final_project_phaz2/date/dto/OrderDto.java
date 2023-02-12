package com.maktab.final_project_phaz2.date.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import jakarta.persistence.Embedded;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private double proposedPrice;

    private String jobDescription;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateAndTimeOfWork;

    private String street;

    private String city;

    private String plaque;

    private CurrentSituation currentSituation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date whenWorkDone;

}
