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

import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.service.PersonService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class PersonControllerTest {
	@Autowired
	private MockMvc mockMvc; // Pour simuler les requêtes HTTP

	@MockitoBean
	private PersonService personService;

	@Autowired
	Person person;

	String jsonContent = "{ \"firstName\": \"Appa\", \"lastName\": \"Paddaone\", \"address\": \"36 rue des paddas\", \"city\": \"paddaVille\", \"zip\": \"37100\", \"phone\": \"841-874-7462\", \"email\": \"appa@email.fr\"}";

	@Test
	public void addPersonTest() throws Exception {
		person = new Person("Appa", "Paddaone", "36 rue des paddas", "paddaVille", 37100, "841-874-7462",
				"appa@email.com");
		doReturn(person).when(personService).addPerson(any());
		mockMvc.perform(post("http://localhost:8080/person", person).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isOk())
				.andExpect(jsonPath("$.lastName").value("Paddaone"));
	}

	@Test
	public void addPersonTestKo() throws Exception {
		doReturn(null).when(personService).addPerson(any());
		mockMvc.perform(post("http://localhost:8080/person", person).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isNotFound());
	}

	@Test
	public void deletePersonTest() throws Exception {
		String firstName = "Appa";
		String lastName = "Paddaone";

		doNothing().when(personService).deletePerson(firstName, lastName);
		mockMvc.perform(delete("http://localhost:8080/person/{firstName}/{lastName}", firstName, lastName)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isNoContent())
				.andExpect(jsonPath("$.lastName").doesNotExist());
	}

	@Test
	public void majPersonTest() throws Exception {
		person = new Person("Moja", "Paddaone", "36 rue des paddas", "paddaVille", 37100, "841-874-7462",
				"appa@email.com");
		doReturn(person).when(personService).updatePerson(any());
		mockMvc.perform(put("http://localhost:8080/person", person).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Moja"));
	}

	@Test
	public void majPersonTestKo() throws Exception {
		doReturn(null).when(personService).updatePerson(any());
		mockMvc.perform(put("http://localhost:8080/person", person).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isNotFound());
	}

}
