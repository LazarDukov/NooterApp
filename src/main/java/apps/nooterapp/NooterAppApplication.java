package apps.nooterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@SpringBootApplication
public class NooterAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(NooterAppApplication.class, args);
    }

}
