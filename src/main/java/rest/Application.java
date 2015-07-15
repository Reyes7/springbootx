package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import rest.user.User;
import rest.user.UserRepository;

//@EnableAutoConfiguration
//@Configuration
//@ComponentScan(basePackages = "rest")

@ComponentScan(basePackages = "rest")
@SpringBootApplication
public class Application implements CommandLineRunner{

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        User user = userRepository.findOne(0);
        System.out.println("1: "+user);

        for (User user1 : userRepository.findAll()) {
            System.out.println(user1);
        }
        System.out.println();
    }
}
