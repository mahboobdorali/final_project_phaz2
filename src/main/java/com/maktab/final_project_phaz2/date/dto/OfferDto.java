package com.maktab.final_project_phaz2.date.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferDto {

    private double priceOffer;

    private Long durationOfWork;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeProposeToStartWork;
}
