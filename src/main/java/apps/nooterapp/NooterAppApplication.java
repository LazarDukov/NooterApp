package apps.nooterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
public class NooterAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(NooterAppApplication.class, args);
    }

}
// TODO: Edit whole nav bar to be in left
// TODO: create my profile page for better front end
// TODO: password change - repair
// TODO: check email is it real for registration
// TODO: go back archived note - for secondary complete
// TODO: maybe add images for notes
// TODO: add new functionality for logged my home page