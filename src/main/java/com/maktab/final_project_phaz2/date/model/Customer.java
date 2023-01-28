package com.maktab.final_project_phaz2.date.model;

import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
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
public class Customer extends Person implements Serializable {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<OrderCustomer> orderCustomerList=new ArrayList<>();
   }
