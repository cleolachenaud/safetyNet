package com.oprprojet.safetyNet;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import com.oprproject.safetyNet.webapp.CustomProperties;
import com.oprprojet.safetyNet.controller.DonneesLieesController;
import com.oprprojet.safetyNet.repository.Reader;
@EnableConfigurationProperties(CustomProperties.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

public class SafetyNetApplication implements CommandLineRunner {
	private static final Logger logger = LogManager.getLogger(SafetyNetApplication.class);
	
	private static ConfigurableApplicationContext ctx;  
	@Autowired
	Reader reader;
	@Autowired
	DonneesLieesController donneesLieesController;
	@Value ("${fichier.entree}")
	private String nomDeFichier;
	@Autowired
	CustomProperties properties;
	public static void main(String[] args) {
		ctx = SpringApplication.run(SafetyNetApplication.class, args);
		
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("Lancement de l'application");
	}

}
