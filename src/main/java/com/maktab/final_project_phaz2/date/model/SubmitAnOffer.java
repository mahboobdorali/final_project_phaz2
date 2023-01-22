package com.maktab.final_project_phaz2.date.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Duration;
import java.util.Date;
@Getter
@Setter
@ToString
@Entity
public class SubmitAnOffer extends BaseEntity {
    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateAndTimeRegistrationOfOffer;

    @ManyToOne
    private Expert expert;

    @OneToOne
    private OrderCustomer ordersCustomer;

    private double priceOffer;

    private Duration durationOfWork;

    @Temporal(value = TemporalType.TIME)
    private Date TimeProposeToStartWork;

}
