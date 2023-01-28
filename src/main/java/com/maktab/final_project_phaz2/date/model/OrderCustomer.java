package com.maktab.final_project_phaz2.date.model;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class OrderCustomer extends BaseEntity{

    @ManyToOne
    private Expert expert;

    @ManyToOne
    private UnderService underService;

    @ManyToOne
    private Customer customer;

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
