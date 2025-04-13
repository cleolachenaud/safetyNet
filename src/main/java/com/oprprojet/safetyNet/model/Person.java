package com.oprprojet.safetyNet.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Person {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	@JsonSerialize(using = ToStringSerializer.class)
	private int zip;
	private String phone;
	private String email;
}
