package edu.miu.cs489.aerotran.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigClass {
    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

}
