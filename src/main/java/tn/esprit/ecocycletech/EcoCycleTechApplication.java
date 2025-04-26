package tn.esprit.ecocycletech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAspectJAutoProxy
//@EntityScan("tn.esprit.ecocycletech.Entity")
@EnableAsync

public class EcoCycleTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoCycleTechApplication.class, args);
    }

}
