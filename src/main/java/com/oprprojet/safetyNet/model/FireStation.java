package com.oprprojet.safetyNet.model;



import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//@JsonFilter("FireStationJsonFilter")
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class FireStation {
	private String address;
	private int station;

}
