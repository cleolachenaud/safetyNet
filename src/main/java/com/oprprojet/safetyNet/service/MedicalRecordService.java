package com.oprprojet.safetyNet.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;

@Service
public class MedicalRecordService {
	@Autowired
	MedicalRecord medicalRecord;
	
	@Autowired
	private Reader reader;
	
	@Autowired
	private Writer writer;
	
	 private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);
	 
	 
	/**
	 * Permet d'ajouter un medicalRecord
	 */
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) throws Exception {
    	logger.debug("methode addMedicalRecord : lancement de la methode");
    	// Lire les données
    	Donnees donneesBrute = reader.jsonReader();
    	// Ajouter le medicalRecord transmise dedans en vérifiant qu'elle n'existe pas déjà.
    	logger.debug("methode addMedicalRecord : debut du traitement");
    	List<MedicalRecord> medicalRecordList = donneesBrute.getMedicalRecords();
    	Boolean medicalRecordDejaExistante = false;
    	for(MedicalRecord medicalRecordElement : medicalRecordList) {
    		if(medicalRecordElement == medicalRecord) {
    			medicalRecordDejaExistante = true;
    			logger.warn("methode addMedicalRecord : le MedicalRecord que vous essayez d'ajouter existe déjà, il ne sera pas ajouté");
    			break;
				
    		}	
    	}
		if(!medicalRecordDejaExistante) {
			medicalRecordList.add(medicalRecord);
			logger.debug("methode addMedicalRecord : le MedicalRecord a bien été ajouté");
			// Ecrire les données.
	    	writer.jsonWriter(donneesBrute);
	    	logger.debug("methode addMedicalRecord : fin de la methode");
		} 
		return medicalRecord;
    }
    /**
     * Permet de supprimer un medicalRecord selon le firstName lastName
     */
    public void deleteMedicalRecord(String firstName, String lastName) throws Exception {
    	logger.debug("methode deleteMedicalRecord : lancement de la methode");
    	// Lire les données
    	Donnees donneesBrute = reader.jsonReader();
    	logger.debug("methode deleteMedicalRecord : debut du traitement");
    	// Supprimer la fireStation transmise.
    	List<MedicalRecord> toRemoveMedicalRecord = new ArrayList<MedicalRecord>();
    	List<MedicalRecord> medicalRecordList = donneesBrute.getMedicalRecords();
    	for(MedicalRecord medicalRecordElement : medicalRecordList) {
    		if(medicalRecordElement.getFirstName().equals(firstName) && medicalRecordElement.getLastName().equals(lastName)) {    	   
    			toRemoveMedicalRecord.add(medicalRecordElement);
    		}
    	}
    	if(!toRemoveMedicalRecord.isEmpty()) { //si la liste à supprimer n'est PAS nulle alors je fais un traitement
    		medicalRecordList.removeAll(toRemoveMedicalRecord);
    		logger.debug("methode deleteMedicalRecord : le medicalRecord a bien été supprimé");
    		// Ecrire les données.
	    	writer.jsonWriter(donneesBrute);
    	}
    	logger.debug("methode deleteMedicalRecord : fin de la methode");
    }
    
    
    /**
     * Permet de modifier le medicalRecord selon le firstName lastName
     */
    public MedicalRecord updateMedicalRecord (MedicalRecord medicalRecord) throws Exception {
    	logger.debug("methode updateMedicalRecord : lancement de la methode");
    	// Lire les données
    	Donnees donneesBrute = reader.jsonReader();
    	logger.debug("methode updateMedicalRecord : debut du traitement");
    	// modifie la stationNumber transmise.
    	MedicalRecord medicalRecordMisAJour = new MedicalRecord();
    	List<MedicalRecord> medicalRecordList = donneesBrute.getMedicalRecords();
    	for(MedicalRecord medicalRecordElement : medicalRecordList) {
    		if(!medicalRecordElement.getFirstName().equals(medicalRecord.getFirstName()) && !medicalRecordElement.getLastName().equals(medicalRecord.getLastName())){
    			continue;
    		}
    	// modifie birthdate / allergies / medications uniquement si ces infos sont passées en paramètres. 
    	// Dans le cas contraire on garde les infos déjà présentes sur l'élément
    	if(medicalRecord.getBirthdate() !=null) {
    		medicalRecordElement.setBirthdate(medicalRecord.getBirthdate());
    		medicalRecordMisAJour.setBirthdate(medicalRecordElement.getBirthdate());
    	}
    	if(medicalRecord.getAllergies()!=null) {
    		medicalRecordElement.setAllergies(medicalRecord.getAllergies());
    		medicalRecordMisAJour.setAllergies(medicalRecordElement.getAllergies());
    	}
    	if(medicalRecord.getMedications()!=null) {
    		medicalRecordElement.setMedications(medicalRecord.getMedications());
    		medicalRecordMisAJour.setMedications(medicalRecordElement.getMedications());
    	}
    	medicalRecordMisAJour.setFirstName(medicalRecordElement.getFirstName());
    	medicalRecordMisAJour.setLastName(medicalRecordElement.getLastName());
    	logger.debug("le MedicalRecord a bien été modifié");
    	} 
    	
    	// Ecrire les données.
    	writer.jsonWriter(donneesBrute);
    	logger.debug("methode updateMedicalRecord : fin de la methode");
    	return medicalRecordMisAJour;
    }
}
