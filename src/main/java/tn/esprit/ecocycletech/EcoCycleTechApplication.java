package tn.esprit.ecocycletech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan("tn.esprit.ecocycletech.Entity")
public class EcoCycleTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoCycleTechApplication.class, args);
    }

}
