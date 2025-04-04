package com.oprprojet.safetyNet.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
	private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthdate;
    private List<String> medications;
    private List<String> allergies;

    
	/**
	 * permet d'obtenir l'age de la personne grâce à sa date d'anniversaire
	 * @return
	 */
    @JsonIgnore
	public Integer getAge() {
		LocalDate dateNaissance = LocalDate.ofInstant(this.birthdate.toInstant(), ZoneId.systemDefault());
		Integer age = Period.between(dateNaissance, LocalDate.now()).getYears();
		return age;
	}

    @JsonIgnore
	public Boolean isAdulte() {
		LocalDate dateAdulte = LocalDate.now().minusYears(18);
		Date aujourdhuiAdulte = Date.from(dateAdulte.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		return(this.birthdate.before(aujourdhuiAdulte));	// renvoie vrai si la personne est adulte
	}


}




