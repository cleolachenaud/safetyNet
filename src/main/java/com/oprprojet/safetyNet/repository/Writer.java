package com.oprprojet.safetyNet.repository;


import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.oprprojet.safetyNet.model.Donnees;
@Component
@Repository
public class Writer {
	private static final Logger logger = LogManager.getLogger(Writer.class);
	@Value ("${fichier.entree}")
	private String nomDeFichier;
	public void jsonWriter(Donnees donnees) throws Exception{
		this.jsonWriter(nomDeFichier, donnees);
	}
	
	/**
	 * méthode qui permet de écrire le fichier Json
	 *@param <FireStation>
	 *@param <MedicalRecords>
	 *@param <Person>
	 * @throws Exception 
	 */
	public void jsonWriter(String cheminDuFichier, Donnees donnees) throws Exception{
		logger.debug("jsonWriter : lancement de la methode");
		ObjectMapper objectMapper = new ObjectMapper();
		/*
	    SimpleFilterProvider filterProvider = new SimpleFilterProvider()
    			.addFilter("PersonJsonFilter" ,SimpleBeanPropertyFilter.serializeAll()) 
    			.addFilter("MedicalRecordJsonFilter" ,SimpleBeanPropertyFilter.serializeAll())
    			.addFilter("FireStationJsonFilter" ,SimpleBeanPropertyFilter.serializeAll())
    			;
	    objectMapper.setFilterProvider(filterProvider);
	    */

	    
		try {
			objectMapper.writeValue(new File (cheminDuFichier), donnees);
			logger.debug("jsonWriter : l'ecriture du fichier s'est bien passé, fin de la methode");
		} catch (Exception e) {
			logger.error("jsonWriter : aucune écriture dans le fichier");
			throw new Exception("Erreur d'ecriture\n" + e); 
		}
	}

}
