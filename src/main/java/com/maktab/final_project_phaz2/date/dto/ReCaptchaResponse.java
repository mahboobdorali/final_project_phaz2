package com.maktab.final_project_phaz2.date.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReCaptchaResponse {

    private Boolean success;

    private String challenge_ts;

    private String hostname;
}
