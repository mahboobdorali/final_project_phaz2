package com.maktab.final_project_phaz2.date.model;

import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@SuperBuilder
public class Expert extends Person implements Serializable {

    private double averageScore;

    @Lob
    private byte[] image;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @ManyToMany(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
    private List<UnderService> underServiceList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "expert",cascade = CascadeType.PERSIST)
    private List<Opinion> opinionList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "expert",cascade = CascadeType.PERSIST)
    private List<OrderCustomer> ordersCustomer = new ArrayList<>();
}
