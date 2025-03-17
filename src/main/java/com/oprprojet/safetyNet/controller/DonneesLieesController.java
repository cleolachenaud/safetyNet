package com.oprprojet.safetyNet.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
	@GetMapping("/firestation")
    public String fireStationStationNumber(@PathVariable(required = true, name = "stationNumber") int stationNumber) throws Exception {
		logger.debug("lancement fireStationStationNumber URL ");
		DonneesLieesService reponse = donneesLieesService.getFireStationStationNumber(stationNumber);
		ObjectMapper mapper = new ObjectMapper();
		String[] champsConsideresPersonJsonFilter = new String[] {"firstName", "lastName", "address", "phone"};
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter));
		ObjectWriter writer = mapper.writer().with(filterProvider);
		
	    try {
	    	logger.info("reponse OK fireStationStationNumber URL ");
			return writer.writeValueAsString(reponse);
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
	@GetMapping("/childAlert")
	public String childAlertAddress (@PathVariable(required = true, name = "address") String address) throws Exception {
		logger.debug("lancement chilAlertAddress URL ");
		DonneesLieesService reponse = donneesLieesService.getChildAlertAddress(address);
		ObjectMapper mapper = new ObjectMapper();
		String[] champsConsideresPersonJsonFilter = new String[] {"firstName", "lastName"};
		String[] champsConsideresMedicalRecordJsonFilter = new String[] {"birthdate"};
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter))
				.addFilter("MedicalRecordJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresMedicalRecordJsonFilter));
		ObjectWriter writer = mapper.writer().with(filterProvider);
		
	    try {
	    	logger.info("reponse OK childAlertAddress URL ");
			return writer.writeValueAsString(reponse);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    logger.error("reponse KO childAlertAddress URL ");
	    return "Error";
	}
/**
 * Controller de l'url phoneAlert	
 * @param station
 * @return
 * @throws Exception
 */
	@GetMapping("/phoneAlert")
	public String phoneAlerteFireStation (@PathVariable(required = true, name = "firestation") int station) throws Exception {
		logger.debug("lancement phoneAlerte URL ");
		DonneesLieesService reponse = donneesLieesService.getPhoneAlerteFireStation(station);
		ObjectMapper mapper = new ObjectMapper();
		String[] champsConsideresPersonJsonFilter = new String[] {"phone"};
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter));
		ObjectWriter writer = mapper.writer().with(filterProvider);
		
	    try {
	    	logger.info("reponse OK phoneAlerte URL ");
			return writer.writeValueAsString(reponse);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    logger.error("reponse KO phoneAlerte URL ");
	    return "Error";
	}
/**
 * Controller de fireAddress
 * @param address
 * @return
 * @throws Exception
 */
	@GetMapping("/fire")
	public String fireAddress (@PathVariable(required = true, name = "address") String address) throws Exception {
		logger.debug("lancement fireAddress URL ");
		DonneesLieesService reponse = donneesLieesService.getFireAddress(address);
		ObjectMapper mapper = new ObjectMapper();
		String[] champsConsideresPersonJsonFilter = new String[] {"lastName", "firstName", "phone"};
		String[] champsConsideresMedicalRecordJsonFilter = new String[] {"birthdate", "medications", "allergies"};
		String[] champsConsideresFireStationJsonFilter = new String[] {"station"};
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter))
				.addFilter("MedicalRecordJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresMedicalRecordJsonFilter))
				.addFilter("FireStationJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresFireStationJsonFilter));
		ObjectWriter writer = mapper.writer().with(filterProvider);
		
	    try {
	    	logger.info("reponse OK fireAddress URL ");
			return writer.writeValueAsString(reponse);
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
	@GetMapping("/station")
	public String floodStation (@PathVariable(required = true, name = "stationNumber") int stationNumber) throws Exception {
		logger.debug("lancement floodStation URL ");
		DonneesLieesService reponse = donneesLieesService.getFloodStations(stationNumber);
		ObjectMapper mapper = new ObjectMapper();
		String[] champsConsideresPersonJsonFilter = new String[] {"lastName", "firstName", };
		String[] champsConsideresMedicalRecordJsonFilter = new String[] {"birthdate", "medications", "allergies"};
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter))
				.addFilter("MedicalRecordJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresMedicalRecordJsonFilter));
		ObjectWriter writer = mapper.writer().with(filterProvider);
		
	    try {
	    	logger.info("reponse OK floodStation URL ");
			return writer.writeValueAsString(reponse);
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
	
	@GetMapping("/personInfoLastName")
	public String personInfoLastName (@PathVariable(required = true, name = "lastName") String lastName) throws Exception {
		logger.debug("lancement personInfoLastName URL ");
		DonneesLieesService reponse = donneesLieesService.getPersonInfoLastName(lastName);
		ObjectMapper mapper = new ObjectMapper();
		String[] champsConsideresPersonJsonFilter = new String[] {"lastName", "firstName", "address", "email"};
		String[] champsConsideresMedicalRecordJsonFilter = new String[] {"birthdate", "medications", "allergies"};
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter))
				.addFilter("MedicalRecordJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresMedicalRecordJsonFilter));
		ObjectWriter writer = mapper.writer().with(filterProvider);
		
	    try {
	    	logger.info("reponse OK personInfoLastName URL ");
			return writer.writeValueAsString(reponse);
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
	@GetMapping("/communityEmail")
	public String communityEmail (@PathVariable(required = true, name = "city") String city) throws Exception {
		logger.debug("lancement communityEmail URL ");
		DonneesLieesService reponse = donneesLieesService.getCommunityEmail(city);
		ObjectMapper mapper = new ObjectMapper();
		String[] champsConsideresPersonJsonFilter = new String[] {"email"};
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter));
		ObjectWriter writer = mapper.writer().with(filterProvider);
		
	    try {
	    	logger.info("reponse OK communityEmail URL ");
			return writer.writeValueAsString(reponse);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    logger.error("reponse KO communityEmail URL ");
	    return "Error";
	}
}
