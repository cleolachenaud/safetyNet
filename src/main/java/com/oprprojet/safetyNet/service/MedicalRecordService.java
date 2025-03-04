package com.oprprojet.safetyNet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;

@Service
public class MedicalRecordService {
	
	/**
	 * Permet d'ajouter un medicalRecord
	 */
    public void addMedicalRecord(MedicalRecord medicalRecord) throws Exception {
    	// Lire les données
    	Donnees donneesBrute = Reader.jsonReader();
    	// Ajouter le medicalRecord transmise dedans en vérifiant qu'elle n'existe pas déjà.
    	List<MedicalRecord> medicalRecordList = donneesBrute.getMedicalRecordList();
    	Boolean medicalRecordDejaExistante = false;
    	for(MedicalRecord medicalRecordElement : medicalRecordList) {
    		if(medicalRecordElement == medicalRecord) {
    			medicalRecordDejaExistante = true;
    			break;
				//TODO mettre un logger ici
    		}	
    	}
		if(!medicalRecordDejaExistante) {
			medicalRecordList.add(medicalRecord);
			// Ecrire les données.
	    	Writer.jsonWriter(donneesBrute);
		}  	
    }
    /**
     * Permet de supprimer un medicalRecord selon le firstName lastName
     */
    public void deleteMedicalRecord(MedicalRecord medicalRecord) throws Exception {
    	// Lire les données
    	Donnees donneesBrute = Reader.jsonReader();
    	// Supprimer la fireStation transmise.
    	List<MedicalRecord> medicalRecordList = donneesBrute.getMedicalRecordList();
    	for(MedicalRecord medicalRecordElement : medicalRecordList) {
    		if(medicalRecordElement.getFirstName() == medicalRecord.getFirstName() && medicalRecordElement.getLastName() == medicalRecord.getLastName()) {
    			medicalRecordList.remove(medicalRecordElement);
    		}
    	}
    	// Ecrire les données.
    	Writer.jsonWriter(donneesBrute);
    }
    
    
    /**
     * Permet de modifier le medicalRecord selon le firstName lastName
     */
    public void updateMedicalRecord (MedicalRecord medicalRecord) throws Exception {
    	// Lire les données
    	Donnees donneesBrute = Reader.jsonReader();
    	// modifie la stationNumber transmise.
    	List<MedicalRecord> medicalRecordList = donneesBrute.getMedicalRecordList();
    	for(MedicalRecord medicalRecordElement : medicalRecordList) {
    		if(medicalRecordElement.getFirstName() != medicalRecord.getFirstName() && medicalRecordElement.getLastName() != medicalRecord.getLastName()){
    			continue;
    		}
    		medicalRecordElement = medicalRecord;
    	} 
    	
    	// Ecrire les données.
    	Writer.jsonWriter(donneesBrute);
    }
}
