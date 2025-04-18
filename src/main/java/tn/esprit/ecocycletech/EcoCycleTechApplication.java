package tn.esprit.ecocycletech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EcoCycleTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoCycleTechApplication.class, args);
    }

}
