package rest.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean
    public boolean doMigrate(){
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:postgresql://ec2-54-228-180-92.eu-west-1.compute.amazonaws.com:5432/d57a32kv8593as?user=kizocqwgtqcjlc&password=Sn9guNEnwQdDlYKM35B6x9ob76",
                "kizocqwgtqcjlc","Sn9guNEnwQdDlYKM35B6x9ob76");
        flyway.migrate();
        return true;
    }
}
