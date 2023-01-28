package com.maktab.final_project_phaz2.date.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Duration;
import java.util.Date;
@Getter
@Setter
@ToString
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Offer extends BaseEntity {
    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateAndTimeRegistrationOfOffer;

    @ManyToOne
    private Expert expert;

    @ManyToOne
    private OrderCustomer ordersCustomer;

    private double priceOffer;

    private Duration durationOfWork;

    @Temporal(value = TemporalType.TIME)
    private Date TimeProposeToStartWork;

    public Offer(Long id, Date dateAndTimeRegistrationOfOffer, Expert expert, OrderCustomer ordersCustomer, double priceOffer, Duration durationOfWork, Date timeProposeToStartWork) {
        super(id);
        this.dateAndTimeRegistrationOfOffer = dateAndTimeRegistrationOfOffer;
        this.expert = expert;
        this.ordersCustomer = ordersCustomer;
        this.priceOffer = priceOffer;
        this.durationOfWork = durationOfWork;
        TimeProposeToStartWork = timeProposeToStartWork;
    }


}
