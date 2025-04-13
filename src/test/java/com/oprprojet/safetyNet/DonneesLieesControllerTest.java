package com.oprprojet.safetyNet;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.oprprojet.safetyNet.model.DonneesLiees;
import com.oprprojet.safetyNet.service.DonneesLieesService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc

public class DonneesLieesControllerTest {

	@Autowired
	private MockMvc mockMvc; // Pour simuler les requêtes HTTP
	@MockitoBean
	private DonneesLieesService donneesLieesService;
	@Autowired
	DonneesLiees donneesLiees;

	public Map<String, Object> creationMap() {
		Map<String, Object> reponse = new HashMap<>();
		List<Map<String, String>> listPerson = new ArrayList<>();
		Map<String, String> person = new HashMap<>();

		person.put(DonneesLieesService.FIRSTNAME, "Appa");
		person.put(DonneesLieesService.LASTNAME, "Paddaone");
		person.put(DonneesLieesService.ADDRESS, "36 rue des paddas");
		person.put("city", "PaddaVille");
		person.put(DonneesLieesService.PHONE, "841-874-7462");
		person.put(DonneesLieesService.AGE, "2021-04-14");
		person.put(DonneesLieesService.ALLERGIES, "avocat");
		person.put(DonneesLieesService.MEDICAMENTS, "baytril");
		Map<String, String> person2 = new HashMap<>();
		person2.put(DonneesLieesService.FIRSTNAME, "Moja");
		person2.put(DonneesLieesService.LASTNAME, "Paddatwo");
		person2.put(DonneesLieesService.ADDRESS, "36 rue des paddas");
		person2.put("city", "PaddaVille");
		person2.put(DonneesLieesService.PHONE, "843-894-7462");
		person2.put(DonneesLieesService.AGE, "2021-05-19");
		person2.put(DonneesLieesService.ALLERGIES, "avocat");
		person.put(DonneesLieesService.MEDICAMENTS, "baytril");
		listPerson.add(person);
		listPerson.add(person2);

		Integer nombreAdultes = 5;
		Integer nombreEnfants = 1;
		Integer stationNumber = 1;
		Integer stationNumber2 = 2;
		reponse.put(DonneesLieesService.STATIONNUMBER, stationNumber);
		reponse.put(DonneesLieesService.STATIONNUMBER, stationNumber2);
		reponse.put(DonneesLieesService.LISTEPERSONNE, listPerson);
		reponse.put(DonneesLieesService.FOYER, listPerson);
		reponse.put(DonneesLieesService.LISTEPHONE, listPerson);
		reponse.put(DonneesLieesService.LISTEMAIL, listPerson);
		reponse.put(DonneesLieesService.NOMBREADULTE, nombreAdultes);
		reponse.put(DonneesLieesService.NOMBREENFANT, nombreEnfants);
		return reponse;
	}

	@Test
	public void fireStationStationNumberTest() throws Exception {
		Map<String, Object> reponse = creationMap();
		doReturn(reponse).when(donneesLieesService).getFireStationStationNumber(1);
		mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=1")).andExpect(status().isOk())
				// Vérifiez le statut de la réponse

				.andExpect(jsonPath("$.NombreAdultes").value(5)).andExpect(jsonPath("$.NombreEnfants").value(1))
				.andExpect(jsonPath("$.ListePersonnes[0].FirstName").value("Appa"));
		// Vérifiez une valeur dans la réponse JSON

	}

	@Test
	public void childAlertTest() throws Exception {
		Map<String, Object> reponse = creationMap();
		doReturn(reponse).when(donneesLieesService).getChildAlertAddress("36 rue des paddas");
		mockMvc.perform(get("http://localhost:8080/childAlert?address=36 rue des paddas")).andExpect(status().isOk())
				.andExpect(jsonPath("$.NombreEnfants").value(1))
				.andExpect(jsonPath("$.Foyer[0].FirstName").value("Appa"));

	}

	@Test
	public void phoneAlertTest() throws Exception {
		Map<String, Object> reponse = creationMap();
		doReturn(reponse).when(donneesLieesService).getPhoneAlerteFireStation(1);
		mockMvc.perform(get("http://localhost:8080/phoneAlert?firestation=1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.ListePhone[1].Phone").value("843-894-7462"));

	}

	@Test
	public void fireAddressTest() throws Exception {
		Map<String, Object> reponse = creationMap();
		doReturn(reponse).when(donneesLieesService).getFireAddress("36 rue des paddas");
		mockMvc.perform(get("http://localhost:8080/fire?address=36 rue des paddas")).andExpect(status().isOk());

	}

	@Test
	public void floodStationTest() throws Exception {
		Map<String, Object> reponse = creationMap();
		doReturn(reponse).when(donneesLieesService).getFloodStations(List.of(1, 2));
		mockMvc.perform(get("http://localhost:8080/flood/stations?stations=1,2")).andExpect(status().isOk());

	}

	@Test
	public void personInfoLastNameTest() throws Exception {
		Map<String, Object> reponse = creationMap();
		doReturn(reponse).when(donneesLieesService).getPersonInfoLastName("Paddaone");
		mockMvc.perform(get("http://localhost:8080/personInfo?lastName=Paddaone")).andExpect(status().isOk())

				.andExpect(jsonPath("$.ListePersonnes[0].FirstName").value("Appa"));
	}

	@Test
	public void communityEmailTest() throws Exception {
		Map<String, Object> reponse = creationMap();
		doReturn(reponse).when(donneesLieesService).getCommunityEmail("PaddaVille");
		mockMvc.perform(get("http://localhost:8080/communityEmail?city=PaddaVille")).andExpect(status().isOk());

	}

}
