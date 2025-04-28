package com.denzelcode.booklibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BooklibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooklibraryApplication.class, args);
	}


}
