package com.oprproject.safetyNet.webapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@Data
@Configuration
@ConfigurationProperties(prefix="com.oprproject.safetynet.webapp")
public class CustomProperties {
	private String apiUrl;

}
