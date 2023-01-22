package com.maktab.final_project_phaz2.date.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Customer extends Person implements Serializable {

    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderCustomer> orderCustomerList=new ArrayList<>();

}
