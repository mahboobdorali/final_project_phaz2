package com.maktab.final_project_phaz2.date.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentDto {

    private String cardNumber;

    private String cvv2;

    private LocalDate expiredTime;

    private String password;

    private Long orderId;

    private Long customerId;
}
