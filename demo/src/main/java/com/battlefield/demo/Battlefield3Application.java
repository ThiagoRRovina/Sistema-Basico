package com.battlefield.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
@EnableTransactionManagement
public class Battlefield3Application {

	public static void main(String[] args) {
		SpringApplication.run(Battlefield3Application.class, args);
		openBrowser("http://localhost:8080/telaLogin");
	}

	private static void openBrowser(String url) {
		if (!GraphicsEnvironment.isHeadless()) {
			try {
				Desktop.getDesktop().browse(new URI(url));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("java.Aplicattion: " + url);
		}
	}


}
