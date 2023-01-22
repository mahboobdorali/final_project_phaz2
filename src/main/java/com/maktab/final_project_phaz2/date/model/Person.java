package com.maktab.final_project_phaz2.date.model;
import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class Person extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String family;
    @Column(nullable = false, unique = true)
    private String emailAddress;
    @Column(unique = true, nullable = false,length=8)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateAndTimeOfRegistration;
    private double amount;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", dateAndTimeOfRegistration=" + dateAndTimeOfRegistration +
                ", amount=" + amount +
                '}';
    }
}
