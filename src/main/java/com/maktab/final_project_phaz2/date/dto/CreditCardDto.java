package com.maktab.final_project_phaz2.date.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreditCardDto {

    @JsonFormat(pattern = "yy-MM")
    private Date expirationDate;

    @Pattern(regexp = "^[0-9]{16}$")
    private String cardNumber;

    @Pattern(regexp = "^[0-9]{3,4}$")
    private String cvv2;

    private String captcha;

}
