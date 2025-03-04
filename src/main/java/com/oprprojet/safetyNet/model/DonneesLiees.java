package com.oprprojet.safetyNet.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.oprprojet.safetyNet.repository.Reader;

import jakarta.persistence.Entity;
import lombok.Data;
@Data
@Component

public class DonneesLiees {	
	private Map<FireStation, List<Person>> personByFirestation = new HashMap<FireStation, List<Person>>();
	private Map<Person, List<MedicalRecord>> medicalRecordByPerson = new HashMap<Person, List<MedicalRecord>>();
	
	public static DonneesLiees getDonneesLiees() {
		return DonneesLiees.conversionDonneesBrutDonneesLiees(Reader.jsonReader());
	}
	
 /**
  * TODO retourne un objet de type DonneesLiees. 
  * un objet de type DonneesLiees contient une map de firestation/person et person/medicalRecord
  * @param donneesBrutes
  * @return
  */
	public static DonneesLiees conversionDonneesBrutDonneesLiees(Donnees donneesBrutes) {
		DonneesLiees donneesLiees = new DonneesLiees();

		for(FireStation fireStation : donneesBrutes.getFireStationList()) {
			List<Person> listePerson = new ArrayList<Person>();
			for(Person person : donneesBrutes.getPersonList()){
				if(fireStation.getAddress().equals(person.getAddress())) {
					listePerson.add(person);
				}
			}
			donneesLiees.getPersonByFirestation().put(fireStation, listePerson);
		}

		for(Person person: donneesBrutes.getPersonList()) {
			List<MedicalRecord> listeMedicalRecord = new ArrayList<MedicalRecord>();
			for(MedicalRecord medicalRecord : donneesBrutes.getMedicalRecordList()){
				if(person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())) {
					listeMedicalRecord.add(medicalRecord);
				}
			}
			donneesLiees.getMedicalRecordByPerson().put(person, listeMedicalRecord);
		}
		
		return donneesLiees;
	}
}
