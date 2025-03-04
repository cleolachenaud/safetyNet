package com.oprprojet.safetyNet.repository;


import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oprprojet.safetyNet.model.Donnees;
public class Writer {

	public static void jsonWriter(Donnees donnees) throws Exception{
		Writer.jsonWriter(Reader.NOM_FICHIER_JSON, donnees);
	}
	
	/**
	 * méthode qui permet de écrire le fichier Json
	 *@param <FireStation>
	 *@param <MedicalRecords>
	 *@param <Person>
	 * @throws Exception 
	 */
	public static void jsonWriter(String cheminDuFichier, Donnees donnees) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File (cheminDuFichier), donnees);
		} catch (Exception e) {
			throw new Exception("Erreur d'ecriture"); 
		}
	}

}
