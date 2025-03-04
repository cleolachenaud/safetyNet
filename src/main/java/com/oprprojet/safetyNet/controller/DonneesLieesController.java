package com.oprprojet.safetyNet.controller;

import java.util.logging.Logger;

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
    static final Logger logger = Logger.getLogger(Logger.class.getName());
	@Autowired
	private DonneesLieesService donneesLieesService = new DonneesLieesService();
	/*
	@Autowired
	private FireStationReponse fireStationReponse = new FireStationReponse();
	@Autowired
	private ChildAlertReponse childAlertReponse = new ChildAlertReponse();
	*/
	@GetMapping("/firestation")
    public String fireStationStationNumber(@PathVariable(required = true, name = "stationNumber") int stationNumber) {
		logger.info("fireStation URL ");
		DonneesLieesService reponse = donneesLieesService.getFireStationStationNumber(stationNumber);
		ObjectMapper mapper = new ObjectMapper();
		String[] champsConsideresPersonJsonFilter = new String[] {"firstName", "lastName", "address", "phone"};
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter));
		ObjectWriter writer = mapper.writer().with(filterProvider);
		
	    try {
			return writer.writeValueAsString(reponse);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    return "Error";
	}
	
	@GetMapping("/childAlert")
	public String childAlertAddress (@PathVariable(required = true, name = "address") String address) {
		logger.info("chilAlert URL ");
		DonneesLieesService reponse = donneesLieesService.getChildAlertAddress(address);
		ObjectMapper mapper = new ObjectMapper();
		String[] champsConsideresPersonJsonFilter = new String[] {"firstName", "lastName"};
		String[] champsConsideresMedicalRecordJsonFilter = new String[] {"birthdate"};
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter))
				.addFilter("MedicalRecordJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresMedicalRecordJsonFilter));
		ObjectWriter writer = mapper.writer().with(filterProvider);
		
	    try {
			return writer.writeValueAsString(reponse);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    return "Error";
	}
	
/*
	@GetMapping("/firestation")
    public String fireStationHabitants(@PathVariable(required = true, name = "stationNumber") int stationNumber) {
		logger.info("fireStation URL ");
		FireStationReponse reponse = fireStationReponse.getReponse(stationNumber);
		ObjectMapper mapper = new ObjectMapper();
		String[] champsConsideresPersonJsonFilter = new String[] {"firstName", "lastName", "address", "phone"};
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter));
		ObjectWriter writer = mapper.writer().with(filterProvider);
		
	    try {
			return writer.writeValueAsString(reponse);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    return "Error";
	}
	
	
	@GetMapping("/childAlert")
	public String childAlert (@PathVariable(required = true, name = "address") String address) {
		logger.info("chilAlert URL ");
		ChildAlertReponse reponse = childAlertReponse.getReponse(address);
		ObjectMapper mapper = new ObjectMapper();
		String[] champsConsideresPersonJsonFilter = new String[] {"firstName", "lastName"};
		String[] champsConsideresMedicalRecordJsonFilter = new String[] {"birthdate"};
		FilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("PersonJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresPersonJsonFilter))
				.addFilter("MedicalRecordJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(champsConsideresMedicalRecordJsonFilter));
		ObjectWriter writer = mapper.writer().with(filterProvider);
		
	    try {
			return writer.writeValueAsString(reponse);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    return "Error";
	}
	*/
}
