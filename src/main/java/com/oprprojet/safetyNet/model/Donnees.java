package com.oprprojet.safetyNet.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

/**
 *  Stocke le fichier Json sous forme d'objet Java
 */

@Data

public class Donnees {
	@JsonAlias({ "firestations" })
	private List<FireStation> fireStationList ;
	@JsonAlias({ "persons" })
	private List<Person> personList;
	@JsonAlias({ "medicalrecords" })
	private List<MedicalRecord> medicalRecordList;


}
