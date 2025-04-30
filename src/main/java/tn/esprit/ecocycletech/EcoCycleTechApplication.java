package tn.esprit.ecocycletech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
//@EntityScan("tn.esprit.ecocycletech.Entity")
public class EcoCycleTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoCycleTechApplication.class, args);
    }

}
