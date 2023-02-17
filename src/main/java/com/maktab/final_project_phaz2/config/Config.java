package com.maktab.final_project_phaz2.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }

}
