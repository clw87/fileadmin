package com.chang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;


@Controller
@EnableAutoConfiguration
@EnableAsync
@ComponentScan
public class ChangApp {

	public static void main(String[] args) {
		SpringApplication.run(ChangApp.class, args);
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(ChangApp.class);
	}
}