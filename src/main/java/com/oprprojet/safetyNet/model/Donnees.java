package com.oprprojet.safetyNet.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.oprprojet.safetyNet.repository.Reader;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Stocke le fichier Json sous forme d'objet Java
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donnees {
	@JsonAlias({ "persons" })
	private List<Person> persons;

	@JsonAlias({ "firestations" })
	private List<FireStation> fireStations ;
	
	@JsonAlias({ "medicalrecords" })
	private List<MedicalRecord> medicalRecords;
	
	
	@JsonProperty("firestations")
	public void setFireStations(List<FireStation> fireStations) {
		this.fireStations = fireStations;
	}

	public List<Person> getPersons() {
		return persons;
	}
	@JsonProperty("persons")
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<MedicalRecord> getMedicalRecords() {
		return medicalRecords;
	}
	@JsonProperty("medicalrecords")
	public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}
}
