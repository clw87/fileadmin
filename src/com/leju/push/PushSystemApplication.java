package com.leju.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;

@Controller
@EnableAutoConfiguration
@EnableAsync
@ComponentScan
public class PushSystemApplication {
	public static void main(String[] args){
		
		SpringApplication.run(PushSystemApplication.class);
	}
}
