package com.oprprojet.safetyNet.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.oprprojet.safetyNet.repository.Reader;

import jakarta.persistence.Entity;
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
