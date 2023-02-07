package com.maktab.final_project_phaz2.date.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.web.bind.annotation.Mapping;

import java.time.Duration;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OfferDto {
    private double priceOffer;
     private Duration durationOfWork;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String timeProposeToStartWork;
   // @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private OrderCustomer orderCustomer;
}
