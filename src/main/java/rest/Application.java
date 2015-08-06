package rest;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import rest.config.HerokuDatabaseConfig;

@ComponentScan(basePackages = "rest")
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:postgresql://ec2-54-228-180-92.eu-west-1.compute.amazonaws.com:5432/d57a32kv8593as?user=kizocqwgtqcjlc&password=Sn9guNEnwQdDlYKM35B6x9ob76",
                "kizocqwgtqcjlc","Sn9guNEnwQdDlYKM35B6x9ob76");
        flyway.migrate();
    }
}
