package com.maktab.final_project_phaz2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ServletComponentScan
public class FinalProjectPhaz2Application {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectPhaz2Application.class, args);
    }

}
