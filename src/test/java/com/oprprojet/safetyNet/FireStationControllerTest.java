package com.oprprojet.safetyNet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.service.FireStationService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class FireStationControllerTest {
	@Autowired
	private MockMvc mockMvc; // Pour simuler les requêtes HTTP

	@MockitoBean
	private FireStationService fireStationService;

	@Autowired
	FireStation fireStation;

	String jsonContent = "{ \"address\": \"newValue\", \"stationNumber\": \"9\"}";

	@Test
	public void addFireStationTest() throws Exception {
		fireStation = new FireStation("newValue", 9);
		doReturn(fireStation).when(fireStationService).addFireStation(any());
		mockMvc.perform(post("http://localhost:8080/firestation", fireStation).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isOk())
				.andExpect(jsonPath("$.address").value("newValue"));
		;
	}

	@Test
	public void addFireStationTestKo() throws Exception {
		doReturn(null).when(fireStationService).addFireStation(any());
		mockMvc.perform(post("http://localhost:8080/firestation", fireStation).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isNotFound());
	}

	@Test
	public void deleteFireStationTest() throws Exception {
		String jsonContent = "{ \"address\": \"newValue\", \"stationNumber\": \"9\"}";
		fireStation = new FireStation("newValue", 9);
		doNothing().when(fireStationService).deleteFireStation(fireStation);
		mockMvc.perform(delete("http://localhost:8080/firestation").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isNoContent())
				.andExpect(jsonPath("$.address").doesNotExist());
		;
	}

	@Test
	public void majFireStationTest() throws Exception {
		fireStation = new FireStation("new", 9);
		doReturn(fireStation).when(fireStationService).updateFireStation(any());
		mockMvc.perform(put("http://localhost:8080/firestation", fireStation).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isOk())
				.andExpect(jsonPath("$.address").value("new"));
		;
	}

	@Test
	public void majFireStationTestKo() throws Exception {
		doReturn(null).when(fireStationService).updateFireStation(any());
		mockMvc.perform(put("http://localhost:8080/firestation", fireStation).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isNotFound());
	}
}
