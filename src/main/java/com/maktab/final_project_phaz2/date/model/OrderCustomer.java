package com.maktab.final_project_phaz2.date.model;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class OrderCustomer extends BaseEntity{




   /* @OneToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER,mappedBy ="ordersCustomer" )
    private List<Offer> offerList=new ArrayList<>();
*/
    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Expert expert;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
    private UnderService underService;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
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
