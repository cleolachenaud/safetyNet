package com.oprprojet.safetyNet.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Data;
@Data
@JsonFilter("MedicalRecordJsonFilter") 
public class MedicalRecord {
	private String firstName;
    private String lastName;
    private Date birthdate;
    private List<String> medications;
    private List<String> allergies;

/**
 * permet d'obtenir l'age de la personne grâce à sa date d'anniversaire
 * @return
 */
	public int getAge() {
		LocalDate dateNaissance = LocalDate.from(this.birthdate.toInstant());
		int age = Period.between(dateNaissance, LocalDate.now()).getYears();
		return age;
		
	}


	public Boolean isAdulte() {
		LocalDate dateAdulte = LocalDate.now().minusYears(18);
		Date aujourdhuiAdulte = Date.from(dateAdulte.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		return(this.birthdate.before(aujourdhuiAdulte));	// renvoie vrai si la personne est adulte
	}


}




