package com.oprprojet.safetyNet;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.DonneesLiees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.service.DonneesLieesService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DonneesLieesServiceTest {

	@Mock
	private Reader jsonReaderMock;

	@Mock
	private DonneesLiees donneesLiees;

	@InjectMocks
	private DonneesLieesService donneesLieesService;

	@BeforeEach
	public void setUp() throws Exception {
		// Créez les objets Person, MedicalRecord et FireStation
		Date birthdate1 = createBirthdate("2021-04-14");
		Person person1 = new Person("Appa", "Paddaone", "36 rue des paddas", "paddaVille", 37100, "841-874-7462",
				"appa@email.com");
		FireStation station1 = new FireStation("36 rue des paddas", 1);
		MedicalRecord medicalRecord1 = new MedicalRecord("Appa", "Paddaone", birthdate1, List.of("baytril"),
				List.of("avocat"));

		Date birthdate2 = createBirthdate("2021-05-19");
		Person person2 = new Person("Moja", "Paddatwo", "36 rue des paddas", "paddaVille", 37100, "843-894-7462",
				"moja@email.com");
		MedicalRecord medicalRecord2 = new MedicalRecord("Moja", "Paddatwo", birthdate2, List.of(""),
				List.of("avocat", "chocolat"));

		Date birthdate3 = createBirthdate("2021-06-21");
		Person person3 = new Person("Marley", "Paddathree", "12 rue des gloutons", "lilaVille", 37100, "561-094-3928",
				"marley@email.com");
		FireStation station2 = new FireStation("12 rue des gloutons", 2);
		MedicalRecord medicalRecord3 = new MedicalRecord("Marley", "Paddathree", birthdate3, List.of(""),
				List.of("avocat"));

		Date birthdate4 = createBirthdate("1960-02-23");
		Person person4 = new Person("John", "Paddaone", "36 rue des paddas", "paddaVille", 37100, "811-874-7462",
				"john@email.com");
		MedicalRecord medicalRecord4 = new MedicalRecord("John", "Paddaone", birthdate4, List.of(""), List.of(""));

		Date birthdate5 = createBirthdate("1922-05-29");
		Person person5 = new Person("Steve", "Paddatwo", "15 rue des pissenlits", "paddaVille", 37100, "822-894-7462",
				"steve@email.com");
		FireStation station3 = new FireStation("15 rue des pissenlits", 3);
		MedicalRecord medicalRecord5 = new MedicalRecord("Steve", "Paddatwo", birthdate5, List.of(""), List.of(""));

		// Créer les listes personList, medicalRecordList et fireStationList
		List<Person> personList = new ArrayList<Person>();
		List<FireStation> fireStationList = new ArrayList<FireStation>();
		List<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>();
		personList.add(person1);
		personList.add(person2);
		personList.add(person3);
		personList.add(person4);
		personList.add(person5);

		fireStationList.add(station1);
		fireStationList.add(station2);
		fireStationList.add(station3);

		medicalRecordList.add(medicalRecord1);
		medicalRecordList.add(medicalRecord2);
		medicalRecordList.add(medicalRecord3);
		medicalRecordList.add(medicalRecord4);
		medicalRecordList.add(medicalRecord5);

		// Ajouter les listes dans données brutes pour avoir un fichier complet pour les
		// tests.
		Donnees donneesBrutes = new Donnees(personList, fireStationList, medicalRecordList);

		// Simuler le comportement du reader et writer
		Mockito.when(jsonReaderMock.jsonReader()).thenReturn(donneesBrutes);
	}

	@Test
	public void getFireStationStationNumberTest() throws Exception {
		String erreurLoggee = "";
		Map<String, Object> reponse = null;

		// Appelez la méthode d'ajout
		try {
			reponse = donneesLieesService.getFireStationStationNumber(1);
		} catch (Exception e) {
			erreurLoggee = e.toString();
			e.printStackTrace(System.out);
		}
		Map<String, Object> mapAttendue = Map.of(DonneesLieesService.NOMBREENFANT, 2, DonneesLieesService.NOMBREADULTE,
				1, DonneesLieesService.LISTEPERSONNE,
				List.of(Map.of(DonneesLieesService.FIRSTNAME, "Appa", DonneesLieesService.LASTNAME, "Paddaone",
						DonneesLieesService.ADDRESS, "36 rue des paddas", DonneesLieesService.PHONE, "841-874-7462"),
						Map.of(DonneesLieesService.FIRSTNAME, "Moja", DonneesLieesService.LASTNAME, "Paddatwo",
								DonneesLieesService.ADDRESS, "36 rue des paddas", DonneesLieesService.PHONE,
								"843-894-7462"),
						Map.of(DonneesLieesService.FIRSTNAME, "John", DonneesLieesService.LASTNAME, "Paddaone",
								DonneesLieesService.ADDRESS, "36 rue des paddas", DonneesLieesService.PHONE,
								"811-874-7462"

						)));
		// Vérifiez que l'ajout s'est bien passé

		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		assertEquals("Les données retournées doivent être identiques à l'attendu", mapAttendue, reponse);
	}

	@Test
	public void getChildAlertAddressTest() throws Exception {
		String erreurLoggee = "";
		Map<String, Object> reponse = null;

		// Appelez la méthode d'ajout
		try {
			reponse = donneesLieesService.getChildAlertAddress("36 rue des paddas");
		} catch (Exception e) {
			erreurLoggee = e.toString() + e.getStackTrace();
			e.printStackTrace(System.out);
		}

		// Vérifiez que l'ajout s'est bien passé

		Map<String, Object> mapAttendue = Map.of(DonneesLieesService.NOMBREENFANT, 2, DonneesLieesService.NOMBREADULTE,
				1, DonneesLieesService.ENFANTS,
				List.of(Map.of(DonneesLieesService.FIRSTNAME, "Appa", DonneesLieesService.LASTNAME, "Paddaone",
						DonneesLieesService.AGE, age(createBirthdate("2021-04-14")).toString()),
						Map.of(DonneesLieesService.FIRSTNAME, "Moja", DonneesLieesService.LASTNAME, "Paddatwo",
								DonneesLieesService.AGE, age(createBirthdate("2021-05-19")).toString())),
				DonneesLieesService.FOYER,
				List.of(Map.of(DonneesLieesService.FIRSTNAME, "John", DonneesLieesService.LASTNAME, "Paddaone")));
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		assertEquals("Les données retournées doivent être identiques à l'attendu", mapAttendue, reponse);
	}

	@Test
	public void getPhoneAlerteFireStationTest() throws Exception {
		String erreurLoggee = "";
		Map<String, Object> reponse = null;

		// Appelez la méthode d'ajout
		try {
			reponse = donneesLieesService.getPhoneAlerteFireStation(1);
		} catch (Exception e) {
			erreurLoggee = e.toString() + e.getStackTrace();
			e.printStackTrace(System.out);
		}

		Map<String, Object> mapAttendue = Map.of(DonneesLieesService.LISTEPHONE,
				List.of(Map.of(DonneesLieesService.PHONE, "841-874-7462"),
						Map.of(DonneesLieesService.PHONE, "843-894-7462"),
						Map.of(DonneesLieesService.PHONE, "811-874-7462"

						)));
		// Vérifiez que l'ajout s'est bien passé

		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		assertEquals("Les données retournées doivent être identiques à l'attendu", mapAttendue, reponse);
	}

	@Test
	public void getFireAddressTest() throws Exception {
		String erreurLoggee = "";
		Map<String, Object> reponse = null;

		// Appelez la méthode d'ajout
		try {
			reponse = donneesLieesService.getFireAddress("36 rue des paddas");
		} catch (Exception e) {
			erreurLoggee = e.toString();
			e.printStackTrace(System.out);
		}
		Map<String, Object> mapAttendue = Map.of(DonneesLieesService.STATIONNUMBER, 1,
				DonneesLieesService.LISTEPERSONNE,
				List.of(Map.of(DonneesLieesService.FIRSTNAME, "Appa", DonneesLieesService.PHONE, "841-874-7462",
						DonneesLieesService.LASTNAME, "Paddaone", DonneesLieesService.MEDICAMENTS, List.of("baytril"),
						DonneesLieesService.AGE, age(createBirthdate("2021-04-14")).toString(),
						DonneesLieesService.ALLERGIES, List.of("avocat")),
						Map.of(DonneesLieesService.FIRSTNAME, "Moja", DonneesLieesService.PHONE, "843-894-7462",
								DonneesLieesService.LASTNAME, "Paddatwo", DonneesLieesService.MEDICAMENTS, List.of(""),
								DonneesLieesService.AGE, age(createBirthdate("2021-05-19")).toString(),
								DonneesLieesService.ALLERGIES, List.of("avocat", "chocolat")),
						Map.of(DonneesLieesService.FIRSTNAME, "John", DonneesLieesService.PHONE, "811-874-7462",
								DonneesLieesService.LASTNAME, "Paddaone", DonneesLieesService.MEDICAMENTS, List.of(""),
								DonneesLieesService.AGE, age(createBirthdate("1960-02-23")).toString(),
								DonneesLieesService.ALLERGIES, List.of("")

						)));
		// Vérifiez que l'ajout s'est bien passé

		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		assertEquals("Les données retournées doivent être identiques à l'attendu", mapAttendue, reponse);
	}

	@Test
	public void getFloodStationsTest() throws Exception {
		String erreurLoggee = "";
		Map<String, Object> reponse = null;

		// Appelez la méthode d'ajout
		try {
			reponse = donneesLieesService.getFloodStations(List.of(1, 2));
		} catch (Exception e) {
			erreurLoggee = e.toString();
			e.printStackTrace(System.out);
		}
		Map<String, Object> mapAttendue = Map.of(DonneesLieesService.FOYER, List.of(
				Map.of(DonneesLieesService.LISTEPERSONNE,
						List.of(Map.of(DonneesLieesService.FIRSTNAME, "Appa", DonneesLieesService.PHONE, "841-874-7462",
								DonneesLieesService.LASTNAME, "Paddaone", DonneesLieesService.MEDICAMENTS,
								List.of("baytril"), DonneesLieesService.AGE,
								age(createBirthdate("2021-04-14")).toString(), DonneesLieesService.ALLERGIES,
								List.of("avocat")),
								Map.of(DonneesLieesService.FIRSTNAME, "Moja", DonneesLieesService.PHONE, "843-894-7462",
										DonneesLieesService.LASTNAME, "Paddatwo", DonneesLieesService.MEDICAMENTS,
										List.of(""), DonneesLieesService.AGE,
										age(createBirthdate("2021-05-19")).toString(), DonneesLieesService.ALLERGIES,
										List.of("avocat", "chocolat")),
								Map.of(DonneesLieesService.FIRSTNAME, "John", DonneesLieesService.PHONE, "811-874-7462",
										DonneesLieesService.LASTNAME, "Paddaone", DonneesLieesService.MEDICAMENTS,
										List.of(""), DonneesLieesService.AGE,
										age(createBirthdate("1960-02-23")).toString(), DonneesLieesService.ALLERGIES,
										List.of("")))),
				Map.of(DonneesLieesService.LISTEPERSONNE,
						List.of(Map.of(DonneesLieesService.FIRSTNAME, "Marley", DonneesLieesService.PHONE,
								"561-094-3928", DonneesLieesService.LASTNAME, "Paddathree",
								DonneesLieesService.MEDICAMENTS, List.of(""), DonneesLieesService.AGE,
								age(createBirthdate("2021-06-21")).toString(), DonneesLieesService.ALLERGIES,
								List.of("avocat"))))));
		// Vérifiez que l'ajout s'est bien passé

		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		assertEquals("Les données retournées doivent être identiques à l'attendu", mapAttendue, reponse);
	}

	@Test
	public void getPersonInfoLastNameTest() throws Exception {
		String erreurLoggee = "";
		Map<String, Object> reponse = null;

		// Appelez la méthode d'ajout
		try {
			reponse = donneesLieesService.getPersonInfoLastName("Paddaone");
		} catch (Exception e) {
			erreurLoggee = e.toString();
			e.printStackTrace(System.out);
		}
		Map<String, Object> mapAttendue = Map.of(DonneesLieesService.LISTEPERSONNE, List.of(Map.of(

				DonneesLieesService.ADDRESS, "36 rue des paddas", DonneesLieesService.LASTNAME, "Paddaone",
				DonneesLieesService.FIRSTNAME, "Appa", DonneesLieesService.EMAIL, "appa@email.com",
				DonneesLieesService.MEDICAMENTS, List.of("baytril"), DonneesLieesService.AGE,
				age(createBirthdate("2021-04-14")).toString(), DonneesLieesService.ALLERGIES, List.of("avocat")),
				Map.of(DonneesLieesService.ADDRESS, "36 rue des paddas", DonneesLieesService.LASTNAME, "Paddaone",
						DonneesLieesService.FIRSTNAME, "John", DonneesLieesService.EMAIL, "john@email.com",
						DonneesLieesService.MEDICAMENTS, List.of(""), DonneesLieesService.AGE,
						age(createBirthdate("1960-02-23")).toString(), DonneesLieesService.ALLERGIES, List.of("")

				)));
		// Vérifiez que l'ajout s'est bien passé

		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		assertEquals("Les données retournées doivent être identiques à l'attendu", mapAttendue, reponse);
	}

	@Test
	public void getCommunityEmailTest() throws Exception {
		String erreurLoggee = "";
		Map<String, Object> reponse = null;

		// Appelez la méthode d'ajout
		try {
			reponse = donneesLieesService.getCommunityEmail("paddaVille");
		} catch (Exception e) {
			erreurLoggee = e.toString();
			e.printStackTrace(System.out);
		}

		Map<String, Object> mapAttendue = Map.of(DonneesLieesService.LISTEMAIL,
				List.of(Map.of(DonneesLieesService.EMAIL, "appa@email.com"),
						Map.of(DonneesLieesService.EMAIL, "steve@email.com"),
						Map.of(DonneesLieesService.EMAIL, "moja@email.com"),
						Map.of(DonneesLieesService.EMAIL, "john@email.com"

						)));
		// Vérifiez que l'ajout s'est bien passé

		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		assertEquals("Les données retournées doivent être identiques à l'attendu", mapAttendue, reponse);
	}

	/*-------méthodes utiles aux tests ------------------------------------------------------------------------------------------------*/

	private Date createBirthdate(String birthdate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdateDate = simpleDateFormat.parse(birthdate);
		return birthdateDate;
	}

	private Integer age(Date birthdateDate) {
		LocalDate localDateNaissance = LocalDate.ofInstant(birthdateDate.toInstant(), ZoneId.systemDefault());
		Integer age = Period.between(localDateNaissance, LocalDate.now()).getYears();
		return age;
	}



}
