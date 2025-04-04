package com.oprprojet.safetyNet;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.oprproject.safetyNet.webapp.CustomProperties;
import com.oprprojet.safetyNet.controller.DonneesLieesController;
import com.oprprojet.safetyNet.controller.FireStationController;
import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;
@EnableConfigurationProperties(CustomProperties.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

public class SafetyNetApplication implements CommandLineRunner {	
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
		exitApplication();
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		Donnees donnees = reader.jsonReader(nomDeFichier);
		List <Integer> floodStationList = new ArrayList<>();
		floodStationList.add(1);
		floodStationList.add(2);
			//Writer.jsonWriter("C:\\Users\\cleol\\Desktop\\PROJET5\\safetyNet\\donneesSorties\\fichierSortie.json", donnees);
		//System.out.println(properties.getApiUrl());
		//System.out.println(donneesLieesController.childAlertAddress("1509 Culver St"));
		//System.out.println(donneesLieesController.fireStationStationNumber(1));
		//System.out.println(donneesLieesController.phoneAlerteFireStation(1));
		//System.out.println(donneesLieesController.fireAddress("1509 Culver St"));
		//System.out.println(donneesLieesController.floodStation(floodStationList));
		//System.out.println(donneesLieesController.personInfoLastName("Boyd"));
		//System.out.println(donneesLieesController.communityEmail("Culver"));
	}
	
	public static void exitApplication() {   
       int staticExitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {   
            
           @Override   
           public int getExitCode() {   
           // no errors   
           return 0;   
           }   
          }
       );   
          
       System.exit(staticExitCode );   
	} 
}
