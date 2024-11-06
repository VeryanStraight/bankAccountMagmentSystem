package com.veryan.springbootapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * the back end API for the bank account management system
 */
@SpringBootApplication
public class SpringBootApiApplication {

	/**
	 * main method to start the API
	 * @param args command line arguments
	 */
    public static void main(String[] args) {
		SpringApplication.run(SpringBootApiApplication.class, args);
	}

}
