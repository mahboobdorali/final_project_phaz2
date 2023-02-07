package com.maktab.final_project_phaz2.date.dto;

import com.maktab.final_project_phaz2.date.model.MainTask;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UnderServiceDto {
    private String nameSubService;

    private double basePrice;

    private String briefExplanation;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private MainTask MainTask;

}
