package com.maktab.final_project_phaz2.Util;

import com.maktab.final_project_phaz2.exception.InputInvalidException;

public class Validation {
    public static void emailIsValid(String email) throws InputInvalidException {
        if (!email.equals("") && email.matches("^(?!.*((?:(?<![\\w.\\-+%])[\\w._%+-]+@[\\w.-]+.[a-zA-Z]{2,}\\b)).*\\b\\1\\b)(?:[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})(?:,(?:[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}))*$\n"))
            return;
        throw new InputInvalidException("your email address is not valid");
    }

    public static void passwordIsValid(String password) throws InputInvalidException {
        if (!password.equals("") && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%&])(?=\\S+$).{8}$"))
            return;
        throw new InputInvalidException("your password must be a combination of letters and numbers with " +
                "a length 8!!");
    }
}
