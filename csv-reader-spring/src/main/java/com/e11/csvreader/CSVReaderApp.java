package com.e11.csvreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CSVReaderApp {

	public static void main(String[] args) {

		SpringApplication.run(CSVReaderApp.class, args);

	}

}
