package com.oprprojet.safetyNet.repository;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oprprojet.safetyNet.model.Donnees;
@Repository
public class Reader {
	public final static String NOM_FICHIER_JSON = "data.json";
	

	public static Donnees jsonReader () {
		return jsonReader (Reader.NOM_FICHIER_JSON);
	}
	/**
	 * méthode qui permet de lire le fichier Json transmis
	 *@param <FireStation>
	 *@param <MedicalRecords>
	 *@param <Person>
	 */
	public static Donnees jsonReader (String cheminDuFichier) {

        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        objectMapper.setDateFormat(dateFormat);
        Donnees donnees = null;
        try {
	        donnees = objectMapper.readValue(new File(cheminDuFichier), Donnees.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return donnees;
 }


}
