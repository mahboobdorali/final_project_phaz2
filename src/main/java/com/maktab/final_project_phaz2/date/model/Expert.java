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
    @Column(nullable = false)
    private byte[] image;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<UnderService> underServiceList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comments> commentsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderCustomer> ordersCustomer = new ArrayList<>();
}
