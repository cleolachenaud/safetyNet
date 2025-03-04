package com.oprprojet.safetyNet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.DonneesLiees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;



@Service

public class FireStationService {

	/**
	 * Permet d'ajouter une FireStation (stationNumber, address)
	 */
    public void addFireStation(FireStation fireStation) throws Exception {
    	// Lire les données
    	Donnees donneesBrute = Reader.jsonReader();
    	// Ajouter la FireStation transmise dedans en vérifiant qu'elle n'existe pas déjà.
    	List<FireStation> fireStationList = donneesBrute.getFireStationList();
    	Boolean fireStationDejaExistante = false;
    	for(FireStation fireStationElement : fireStationList) {
    		if(fireStationElement == fireStation) {
    			fireStationDejaExistante = true;
    			break;
				//TODO mettre un logger ici
    		}	
    	}
		if(!fireStationDejaExistante) {
			fireStationList.add(fireStation);
			// Ecrire les données.
	    	Writer.jsonWriter(donneesBrute);
		}  	
    }
    /**
     * Permet de supprimer une FireStation selon l'address ou la stationNumber 
     */
    public void deleteFireStation(FireStation fireStation) throws Exception {
    	// Lire les données
    	Donnees donneesBrute = Reader.jsonReader();
    	// Supprimer la fireStation transmise.
    	List<FireStation> fireStationList = donneesBrute.getFireStationList();
    	for(FireStation fireStationElement : fireStationList) {
    		if(fireStationElement.getAddress() == fireStation.getAddress() || fireStationElement.getStation() == fireStation.getStation()) {
    			fireStationList.remove(fireStationElement);
    		}
    	}
    	// Ecrire les données.
    	Writer.jsonWriter(donneesBrute);
    }
    
    
    /**
     * Permet de changer le numéro (stationNumber) d'une fireStation (stationNumber, address)
     */
    public void updateFireStation(FireStation fireStation) throws Exception {
    	// Lire les données
    	Donnees donneesBrute = Reader.jsonReader();
    	// modifie la stationNumber transmise.
    	List<FireStation> fireStationList = donneesBrute.getFireStationList();
    	for(FireStation fireStationElement : fireStationList) {
    		if(fireStationElement.getAddress() != fireStation.getAddress()) {
    			continue;
    		}
    		fireStationElement = fireStation;
    	} 
    	
    	// Ecrire les données.
    	Writer.jsonWriter(donneesBrute);
    }
}
