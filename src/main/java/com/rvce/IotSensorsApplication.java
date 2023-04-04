package com.rvce;

import com.pi4j.Pi4J;
import com.pi4j.util.Console;
import com.rvce.controller.PrintInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IotSensorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotSensorsApplication.class, args);

		final var console = new Console();

		// Print program title/header
		console.title("<-- The Pi4J Project -->", "Minimal Example project");

		var pi4j = Pi4J.newAutoContext();

		PrintInfo.printLoadedPlatforms(console, pi4j);
		PrintInfo.printDefaultPlatform(console, pi4j);
		PrintInfo.printProviders(console, pi4j);
	}

}
