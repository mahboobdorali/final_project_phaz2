package com.maktab.final_project_phaz2.date.model;

import jakarta.persistence.*;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<OrderCustomer> orderCustomerList = new ArrayList<>();

}
