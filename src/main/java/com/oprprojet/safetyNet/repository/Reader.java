package com.oprprojet.safetyNet.repository;
import java.io.File;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oprprojet.safetyNet.model.Donnees;
@Repository
public class Reader {
	public final static String NOM_FICHIER_JSON = "C:\\Users\\cleol\\Desktop\\PROJET5\\safetyNet\\data.json";
	private static final Logger logger = LogManager.getLogger(Writer.class);

	public Donnees jsonReader() throws Exception {
		
		return this.jsonReader (Reader.NOM_FICHIER_JSON);
	}
	/**
	 * méthode qui permet de lire le fichier Json transmis
	 *@param <FireStation>
	 *@param <MedicalRecords>
	 *@param <Person>
	 * @throws Exception 
	 */
	public Donnees jsonReader (String cheminDuFichier) throws Exception {
		logger.debug("jsonReader : lancement de la methode");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/mm/yyyy"));
        Donnees donnees = null;
        try {
	        donnees = objectMapper.readValue(new File(cheminDuFichier), Donnees.class);
	        logger.debug("jsonReader : la lecture du fichier s'est bien passé, fin de la methode");
	} catch (Exception e) {
			logger.error("jsonReader le fichier n'a pas été lu");
		throw new Exception("Erreur de lecture\n" + e); 
	}

        return donnees;
 }


}
