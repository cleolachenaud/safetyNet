package com.oprprojet.safetyNet.controller;


import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.oprprojet.safetyNet.service.DonneesLieesService;

@RestController
public class DonneesLieesController {
	 private static final Logger logger = LogManager.getLogger(DonneesLieesController.class);
	@Autowired
	private DonneesLieesService donneesLieesService = new DonneesLieesService();
/**
 * Controller de l'url fireStationNumber
 * @param stationNumber
 * @return
 * @throws Exception
 */
	@GetMapping("/firestation/{stationNumber}")
    public String fireStationStationNumber(@PathVariable(required = true, name = "stationNumber") int stationNumber) throws Exception {
		logger.debug("lancement fireStationStationNumber URL ");
		Map<String, Object> reponse = donneesLieesService.getFireStationStationNumber(stationNumber);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		//String[] champsConsideresPersonJsonFilter = new String[] {"firstName", "lastName", "address", "phone"};
		//FilterProvider filterProvider = new SimpleFilterProvider()
			//	.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter));
	    try {
	    	logger.info("reponse OK fireStationStationNumber URL ");
			String reponseString = mapper.writer()
					.writeValueAsString(reponse);
			return reponseString;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    logger.error("reponse KO fireStationStationNumber URL ");
	    return "Error";
	}
/**
 * Controller de l'url childAlert	
 * @param address
 * @return
 * @throws Exception
 */	
	@GetMapping("/childAlert/{address}")
	public String childAlertAddress (@PathVariable(required = true, name = "address") String address) throws Exception {
		logger.debug("lancement chilAlertAddress URL ");
		Map<String, Object> reponse = donneesLieesService.getChildAlertAddress(address);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	    try {
	    	logger.info("reponse OK chilAlertAddress URL ");
	    	String reponseString = mapper.writer()
					.writeValueAsString(reponse);
			return reponseString;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    logger.error("reponse KO chilAlertAddress URL ");
	    return "Error";
	}

/**
 * Controller de l'url phoneAlert	
 * @param station
 * @return
 * @throws Exception
 */
	@GetMapping("/phoneAlert/{stationNumber}")
	public String phoneAlerteFireStation (@PathVariable(required = true, name = "stationNumber") int stationNumber) throws Exception {
		logger.debug("lancement phoneAlerte URL ");
		Map<String, Object> reponse = donneesLieesService.getPhoneAlerteFireStation(stationNumber);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	    try {
	    	logger.info("reponse OK phoneAlerteFireStation URL ");
			String reponseString = mapper.writer()
					.writeValueAsString(reponse);
			return reponseString;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    logger.error("reponse KO phoneAlerteFireStation URL ");
	    return "Error";
	}
/**
 * Controller de fireAddress
 * @param address
 * @return
 * @throws Exception
 */
	@GetMapping("/fire/{address}")
	public String fireAddress (@PathVariable(required = true, name = "address") String address) throws Exception {
		logger.debug("lancement fireAddress URL ");
		Map<String, Object> reponse = donneesLieesService.getFireAddress(address);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	    try {
	    	logger.info("reponse OK fireAddress URL ");
			String reponseString = mapper.writer()
					.writeValueAsString(reponse);
			return reponseString;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    logger.error("reponse KO fireAddress URL ");
	    return "Error";
	}
/**
 * Controller de l'url floodstation
 * @param stationNumber
 * @return
 * @throws Exception
 */
	@GetMapping("/flood/{listStationNumber}")
	public String floodStation (@PathVariable(required = true, name = "listStationNumber") List<Integer> ListstationNumber) throws Exception {
		logger.debug("lancement floodStation URL ");
		Map<String, Object> reponse = donneesLieesService.getFloodStations(ListstationNumber);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	    try {
	    	logger.info("reponse OK floodStation URL ");
			String reponseString = mapper.writer()
					.writeValueAsString(reponse);
			return reponseString;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    logger.error("reponse KO floodStation URL ");
	    return "Error";
	}
/**
 * Controller de personInfoLastName	
 * @param lastName
 * @return
 * @throws Exception
 */
	
	@GetMapping("/personInfoLastName/{lastName}")
	public String personInfoLastName (@PathVariable(required = true, name = "lastName") String lastName) throws Exception {
		logger.debug("lancement personInfoLastName URL ");
		Map<String, Object> reponse = donneesLieesService.getPersonInfoLastName(lastName);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	    try {
	    	logger.info("reponse OK personInfoLastName URL ");
			String reponseString = mapper.writer()
					.writeValueAsString(reponse);
			return reponseString;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    logger.error("reponse KO personInfoLastName URL ");
	    return "Error";
	}
/**
 * Controller de l'url communityEmail
 * @param city
 * @return
 * @throws Exception
 */
	@GetMapping("/communityEmail/{city}")
	public String communityEmail (@PathVariable(required = true, name = "city") String city) throws Exception {
		logger.debug("lancement communityEmail URL ");
		Map<String, Object> reponse = donneesLieesService.getCommunityEmail(city);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	    try {
	    	logger.info("reponse OK communityEmail URL ");
			String reponseString = mapper.writer()
					.writeValueAsString(reponse);
			return reponseString;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    logger.error("reponse KO communityEmail URL ");
	    return "Error";
	}
}
