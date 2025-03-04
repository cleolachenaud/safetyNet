package com.oprprojet.safetyNet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.oprproject.safetyNet.webapp.CustomProperties;
import com.oprprojet.safetyNet.controller.DonneesLieesController;
import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;
@EnableConfigurationProperties(CustomProperties.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SafetyNetApplication implements CommandLineRunner {

	@Autowired
	CustomProperties properties;
	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		//Donnees donnees = Reader.jsonReader("C:\\Users\\cleol\\Desktop\\PROJET5\\safetyNet\\src\\main\\resources\\data.json");
		//Writer.jsonWriter("C:\\Users\\cleol\\Desktop\\PROJET5\\safetyNet\\donneesSorties\\fichierSortie.json", donnees);
		System.out.println(properties.getApiUrl());
		DonneesLieesController donneesLieesController = new DonneesLieesController();
		System.out.println(donneesLieesController.childAlertAddress("1509 Culver St"));
	}
}
