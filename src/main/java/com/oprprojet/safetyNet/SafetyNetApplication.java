package com.oprprojet.safetyNet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.JsonReader;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SafetyNetApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Donnees donnees = JsonReader.jsonReader("C:\\Users\\cleol\\Desktop\\PROJET5\\safetyNet\\donneesEntrees\\fichierEntree.json");
	}
}
