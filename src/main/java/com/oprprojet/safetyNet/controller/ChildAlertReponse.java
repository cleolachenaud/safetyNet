package com.oprprojet.safetyNet.controller;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.oprprojet.safetyNet.model.DonneesLiees;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.service.DonneesLieesService;

import lombok.Data;

@Data
@Component
public class ChildAlertReponse {
	
	private List<Person> membresDuFoyerList;
	private Map<Person, Integer> enfantMap;
	
	
	public ChildAlertReponse getReponse (String address) {
		DonneesLiees donneesEntree = DonneesLiees.conversionDonneesBrutDonneesLiees(Reader.jsonReader("data.json"));
		List<Person> membreDuFoyerList = new ArrayList<Person>();
		Map<Person, Integer> enfantMap = new HashMap<Person, Integer>();
				
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			List<MedicalRecord> value = entry.getValue();
			if(key.getAddress() != address) {
				continue;
			}

			if (value.get(0).isAdulte()) {
				membreDuFoyerList.add(key);
			} else {
				enfantMap.put(key, value.get(0).getAge());
						
			}
		}
		
		ChildAlertReponse childAlertReponse = new ChildAlertReponse();
		childAlertReponse.setMembresDuFoyerList(membreDuFoyerList);
		childAlertReponse.setEnfantMap(enfantMap);
		return childAlertReponse;
	}
	
}
