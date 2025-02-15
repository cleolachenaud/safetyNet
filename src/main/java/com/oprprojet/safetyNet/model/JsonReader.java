package com.oprprojet.safetyNet.model;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
public class JsonReader {


	/**
	 * méthode qui permet de lire le fichier Json
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



/*

	 public static void jsonReaderMedicalRecords (String[] args) { // etant donné que je spécifie le chemin de mon Json, est ce que je dois passer un argument en paramètre.

	        ObjectMapper objectMapper = new ObjectMapper();
	        try {
		        List<MedicalRecords> medicalRecords = objectMapper.readValue(new File("chemin/vers/votre/fichier.json"), new TypeReference<List<MedicalRecords>>() {});
		        for (MedicalRecords medicalRecord : medicalRecords) {
		           medicalRecords.add(medicalRecord); // j'aimerai lire la liste du fichier et créer autant d'objets medicalRecord que de medicalRecord du fichier.

		        }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 }


	public static void jsonReaderPerson (String[] args) { // etant donné que je spécifie le chemin de mon Json, est ce que je dois passer un argument en paramètre.

	        ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            List<Person> persons = objectMapper.readValue(new File("chemin/vers/votre/fichier.json"), new TypeReference<List<Person>>() {});
		        for (Person person : persons) {
		           persons.add(person); // j'aimerai lire la liste du fichier et créer autant d'objets person que de personnes du fichier.
		        }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 }

	 public static void jsonReaderFireStation (String[] args) { // etant donné que je spécifie le chemin de mon Json, est ce que je dois passer un argument en paramètre.

	        ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            List<FireStation> fireStations = objectMapper.readValue(new File("chemin/vers/votre/fichier.json"), new TypeReference<List<FireStation>>() {});
	            for (FireStation fireStation : fireStations) {
	            	fireStations.add(fireStation); // j'aimerai lire le fichier et créer autant de d'objet fireStation que de firestation du fichier
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 }
*/
}
