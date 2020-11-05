package cz.cvut.fit.tjv.chukavol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.out.println("Hello Spring!");
        SpringApplication.run(Application.class, args);
    }
}


