package com.oprprojet.safetyNet.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.oprprojet.safetyNet.repository.Reader;

import lombok.Data;

@Data
@Repository
public class DonneesLiees {	
	
	@Autowired
	private Reader reader;
	
	private static final Logger logger = LogManager.getLogger(DonneesLiees.class);
	
	private Map<FireStation, List<Person>> personByFirestation = new HashMap<FireStation, List<Person>>();
	private Map<Person, List<MedicalRecord>> medicalRecordByPerson = new HashMap<Person, List<MedicalRecord>>();
	
	public void getDonneesLiees() throws Exception {
		this.conversionDonneesBrutDonneesLiees(reader.jsonReader());
	}
	
 /**
  *  retourne un objet de type DonneesLiees. 
  * un objet de type DonneesLiees contient une map de firestation/person et person/medicalRecord
  * @param donneesBrutes
  * @return
  */
	public void conversionDonneesBrutDonneesLiees(Donnees donneesBrutes) {

		for(FireStation fireStation : donneesBrutes.getFireStations()) {
			List<Person> listePerson = new ArrayList<Person>();
			for(Person person : donneesBrutes.getPersons()){
				if(fireStation.getAddress().equals(person.getAddress())) {
					listePerson.add(person);
				}
			}
			logger.debug("les FireStations ont bien été associées avec les List<Person> correspondantes");
			this.getPersonByFirestation().put(fireStation, listePerson);
		}

		for(Person person: donneesBrutes.getPersons()) {
			List<MedicalRecord> listeMedicalRecord = new ArrayList<MedicalRecord>();
			for(MedicalRecord medicalRecord : donneesBrutes.getMedicalRecords()){
				if(person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())) {
					listeMedicalRecord.add(medicalRecord);
				}
			}
			logger.debug("les Person ont bien été associées avec les List<MedicalRecord> correspondantes");
			this.getMedicalRecordByPerson().put(person, listeMedicalRecord);
		}
		logger.debug("les donneesBrutes ont bien été assemblées en donneesLiees");
	}
}
