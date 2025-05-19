package com.talentreef.interviewquestions.takehome.dataloader;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(WidgetRepository repository) {
        return args -> {
            repository.save(new Widget( "Weather",  "Weather widget ",new BigDecimal("12.34")));
            repository.save(new Widget("Clock", "Clock widget",new BigDecimal("23.45")));
            repository.save(new Widget("Emoji", "Emoji widget",new BigDecimal("34.56")));
            System.out.println("Sample widgets loaded.");
        };
    }
}
