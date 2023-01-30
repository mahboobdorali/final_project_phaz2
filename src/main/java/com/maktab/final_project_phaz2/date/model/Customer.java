package com.maktab.final_project_phaz2.date.model;

import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import jakarta.persistence.*;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer",cascade = CascadeType.PERSIST)
    private List<OrderCustomer> orderCustomerList=new ArrayList<>();

    public Customer(String name, String family, String emailAddress, String password, Role role, Date dateAndTimeOfRegistration, double amount, List<OrderCustomer> orderCustomerList) {
        super(name, family, emailAddress, password, role, dateAndTimeOfRegistration, amount);
        this.orderCustomerList = orderCustomerList;
    }
}
