package com.maktab.final_project_phaz2.date.model;

import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class OrderCustomer extends BaseEntity{

    @ManyToOne
    private Expert expert;

    @ManyToMany
    private List<UnderService> underService=new ArrayList<>();

    private double proposedPrice;

    private String jobDescription;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateAndTimeOfWork;

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name="city",column = @Column(nullable = false)),
            @AttributeOverride(name="plaque",column = @Column(nullable = false))
    })

    private CustomerAddress customerAddress;

    @Enumerated(EnumType.STRING)
    private CurrentSituation currentSituation;
}
