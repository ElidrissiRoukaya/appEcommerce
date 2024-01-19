package com.example.ProjetExam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class ProjetExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetExamApplication.class, args);
	}

}
