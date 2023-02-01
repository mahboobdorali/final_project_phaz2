package com.maktab.final_project_phaz2.date.dto;

import com.maktab.final_project_phaz2.date.model.Comments;
import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExpertDto {
    private double averageScore;
    @Pattern(regexp = "^[A-Za-z]{3,29}$")
    private String name;
    @Pattern(regexp = "^[A-Za-z]{3,29}$")
    private String family;
    @Pattern(regexp = "^(?!.*((?:(?<![\\w.\\-+%])[\\w._%+-]+@[\\w.-]+.[a-zA-Z]{2,}\\b)).*\\b\\1\\b)(?:[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})(?:,(?:[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}))*$\n")
    private String emailAddress;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8}$")
    private String password;
    private Role role;
    private double amount;
    private byte[] image;
    private List<UnderService> underServiceList = new ArrayList<>();
    private List<Comments> commentsList = new ArrayList<>();
    private List<OrderCustomer> ordersCustomer = new ArrayList<>();
}

