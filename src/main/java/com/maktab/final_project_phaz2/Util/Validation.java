package com.maktab.final_project_phaz2.Util;

import com.maktab.final_project_phaz2.exception.RequestIsNotValidException;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validation {
    public static void checkPayment(String cardNumber, String cvv2, LocalDate expiredDate, String password) {
        if(!Pattern.matches("[0-9]{16}", cardNumber))
            throw new RequestIsNotValidException("Wrong credit card number");
        if(!Pattern.matches("[0-9]{3,6}",cvv2))
            throw new RequestIsNotValidException("Wrong cvv2");
        if(expiredDate.isBefore(LocalDate.now()))
            throw new RequestIsNotValidException("Wrong expired date");
        if(!Pattern.matches("[0-9]{4,8}",password))
            throw new RequestIsNotValidException("Wrong password");
    }
}
