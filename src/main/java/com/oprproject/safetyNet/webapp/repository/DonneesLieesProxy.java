package com.oprproject.safetyNet.webapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.oprproject.safetyNet.webapp.CustomProperties;
import com.oprprojet.safetyNet.model.DonneesLiees;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DonneesLieesProxy {
	
	@Autowired
	private CustomProperties properties;
	
	public Iterable<DonneesLiees> fireStationHabitants(){
		
	String baseApiUrl = properties.getApiUrl(); // récupère l'URL de l'Api
	String getFirestationUrl = baseApiUrl + "/firestation";
	
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<Iterable<DonneesLiees>> reponse = restTemplate.exchange(
			getFirestationUrl, 
			HttpMethod.GET, 
			null,
			new ParameterizedTypeReference<Iterable<DonneesLiees>>() {}
			);
	log.debug("obtenir l'appel de firestation" + reponse.getStatusCode().toString());
	return reponse.getBody();
	}
	
}
