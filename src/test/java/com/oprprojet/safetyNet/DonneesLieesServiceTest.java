package com.oprprojet.safetyNet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
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
		Person person1 = new Person("Appa", "Paddaone", "36 rue des paddas", "paddaVille", 37100, "841-874-7462", "appa@email.com");
		FireStation station1 = new FireStation("36 rue des paddas", 1);
		MedicalRecord medicalRecord1 = new MedicalRecord("Appa", "Paddaone", birthdate1, List.of("baytril"), List.of("avocat"));
		
		Date birthdate2 = createBirthdate("2021-05-19");
		Person person2 = new Person("Moja", "Paddatwo", "36 rue des paddas", "paddaVille", 37100, "843-894-7462", "moja@email.com");
		MedicalRecord medicalRecord2 = new MedicalRecord("Moja", "Paddatwo", birthdate2, List.of(""), List.of("avocat", "chocolat"));
		
		Date birthdate3 = createBirthdate("2021-06-21");
		Person person3 = new Person("Marley", "Paddathree", "12 rue des gloutons", "lilaVille", 37100, "561-094-3928", "marley@email.com");
		FireStation station2 = new FireStation("12 rue des gloutons", 2);
		MedicalRecord medicalRecord3 = new MedicalRecord("Marley", "Paddathree", birthdate3, List.of(""), List.of("avocat"));
		
		Date birthdate4 = createBirthdate("1960-02-23");
		Person person4 = new Person("John", "Paddaone", "36 rue des paddas", "paddaVille", 37100, "811-874-7462", "john@email.com");
		MedicalRecord medicalRecord4 = new MedicalRecord("John", "Paddaone", birthdate4, List.of(""), List.of(""));
		
		Date birthdate5 = createBirthdate("1922-05-29");
		Person person5 = new Person("Steve", "Paddatwo", "15 rue des pissenlits", "paddaVille", 37100, "822-894-7462", "steve@email.com");
		FireStation station3 = new FireStation("15 rue des pissenlits", 3);
		MedicalRecord medicalRecord5 = new MedicalRecord("Steve", "Paddatwo", birthdate5, List.of(""), List.of("", ""));
		
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
		
		//Ajouter les listes dans données brutes pour avoir un fichier complet pour les tests. 
		Donnees donneesBrutes = new Donnees(personList, fireStationList, medicalRecordList);
		
		
		// Simuler le comportement du reader et writer
		Mockito.when(jsonReaderMock.jsonReader()).thenReturn(donneesBrutes);
	}
	  

	@Test
	public void getFireStationStationNumberTest() throws Exception {
		String erreurLoggee = "";
		DonneesLieesService donneesLieesServiceLocal = null;
	
		// Appelez la méthode d'ajout	
		try {
			donneesLieesServiceLocal = donneesLieesService.getFireStationStationNumber(1);
		} catch (Exception e) {
			erreurLoggee = e.toString();
			e.printStackTrace(System.out);
		}
	
		// Vérifiez que l'ajout s'est bien passé
	  
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		
		assertEquals("la liste de personnes doit être égale à 3", 3, donneesLieesServiceLocal.getListPerson().size());
		assertEquals("la liste d'enfant doit être égale à 2", 2, donneesLieesServiceLocal.getNombreEnfants());
		assertEquals("la liste d'adulte doit être égale à 1", 1, donneesLieesServiceLocal.getNombreAdultes());
	}

	@Test
	public void getChildAlertAddressTest() throws Exception {
		String erreurLoggee = "";
		DonneesLieesService donneesLieesServiceLocal = null;
	
		// Appelez la méthode d'ajout	
		try {
			donneesLieesServiceLocal = donneesLieesService.getChildAlertAddress("36 rue des paddas");
		} catch (Exception e) {
			erreurLoggee = e.toString() + e.getStackTrace();
			e.printStackTrace(System.out);
		}
	
		// Vérifiez que l'ajout s'est bien passé
	  
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
	
		assertEquals("la liste d'enfants doit être égale à 2", 2, donneesLieesServiceLocal.getEnfantMap().size());
		assertEquals("la liste d'adulte doit être égale à 1", 1, donneesLieesServiceLocal.getListPerson().size());
	  
	}	

	@Test
	public void getPhoneAlerteFireStationTest() throws Exception {
		String erreurLoggee = "";
		DonneesLieesService donneesLieesServiceLocal = null;
	
		// Appelez la méthode d'ajout	
		try {
			donneesLieesServiceLocal = donneesLieesService.getPhoneAlerteFireStation(1);
		} catch (Exception e) {
			erreurLoggee = e.toString() + e.getStackTrace();
			e.printStackTrace(System.out);
		}
	
		// Vérifiez que l'ajout s'est bien passé
	  
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		
		assertEquals("la liste doit être égale à 3", 3, donneesLieesServiceLocal.getListPerson().size());
		assertEquals("le 1er numéro de la liste doit être égal à 841-874-7462 ", "841-874-7462", donneesLieesServiceLocal.getListPerson().get(0).getPhone());
		assertEquals("le 2e numéro de la liste doit être égal à 843-894-7462 ", "843-894-7462", donneesLieesServiceLocal.getListPerson().get(1).getPhone());
		assertEquals("le 3e numéro de la liste doit être égal à 811-874-7462 ", "811-874-7462", donneesLieesServiceLocal.getListPerson().get(2).getPhone());
	  
	}	
	
	@Test
	public void getFireAddressTest() throws Exception {
		String erreurLoggee = "";
		DonneesLieesService donneesLieesServiceLocal = null;
	
		// Appelez la méthode d'ajout	
		try {
			donneesLieesServiceLocal = donneesLieesService.getFireAddress("36 rue des paddas");
		} catch (Exception e) {
			erreurLoggee = e.toString() + e.getStackTrace();
			e.printStackTrace(System.out);
		}
	
		// Vérifiez que l'ajout s'est bien passé
		
		// je créee un objet "miroir" à l'image de ce que doit me retourner la méthode pour pouvoir la comparer dans mon assert
		List<MedicalRecord> medicalRecordList3 = new ArrayList<MedicalRecord>();
		Date birthdate3 = createBirthdate("2021-05-19");
		Person person3 = new Person("Moja", "Paddatwo", "36 rue des paddas", "paddaVille", 37100, "843-894-7462", "moja@email.com");
		MedicalRecord medicalRecord3 = new MedicalRecord("Moja", "Paddatwo", birthdate3, List.of(""), List.of("avocat", "chocolat"));
		Map<Person, List<MedicalRecord>> mapTest = createMapPersonMedicalRecordList();
		medicalRecordList3.add(medicalRecord3);
		mapTest.put(person3, medicalRecordList3);
	  
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		assertEquals ("les informations doivent être identiques dans les deux maps", mapTest, donneesLieesServiceLocal.getPersonMedicalRecordMap());
	}
	
	@Test
	public void getFloodStationsTest() throws Exception {
		String erreurLoggee = "";
		DonneesLieesService donneesLieesServiceLocal = null;
	
		// Appelez la méthode d'ajout	
		try {
			donneesLieesServiceLocal = donneesLieesService.getFloodStations(1);
		} catch (Exception e) {
			erreurLoggee = e.toString() + e.getStackTrace();
			e.printStackTrace(System.out);
		}
	
		// Vérifiez que l'ajout s'est bien passé
		// je créee un objet "miroir" à l'image de ce que doit me retourner la méthode pour pouvoir la comparer dans mon assert
		List<MedicalRecord> medicalRecordList3 = new ArrayList<MedicalRecord>();
		Date birthdate3 = createBirthdate("2021-05-19");
		Person person3 = new Person("Moja", "Paddatwo", "36 rue des paddas", "paddaVille", 37100, "843-894-7462", "moja@email.com");
		MedicalRecord medicalRecord3 = new MedicalRecord("Moja", "Paddatwo", birthdate3, List.of(""), List.of("avocat", "chocolat"));
		Map<Person, List<MedicalRecord>> mapTest = createMapPersonMedicalRecordList();
		medicalRecordList3.add(medicalRecord3);
		mapTest.put(person3, medicalRecordList3);
		
		Map<String, Map<Person, List<MedicalRecord>>> floodStationsMapTest = new HashMap<>();
		floodStationsMapTest.put("36 rue des paddas", mapTest);
		
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		assertEquals ("les informations doivent être identiques dans les deux maps", floodStationsMapTest, donneesLieesServiceLocal.getFloodStationsMap());
	} 
	
	@Test
	public void getPersonInfoLastNameTest() throws Exception {
		String erreurLoggee = "";
		DonneesLieesService donneesLieesServiceLocal = null;
	
		// Appelez la méthode d'ajout	
		try {
			donneesLieesServiceLocal = donneesLieesService.getPersonInfoLastName("Paddaone");
		} catch (Exception e) {
			erreurLoggee = e.toString() + e.getStackTrace();
			e.printStackTrace(System.out);
		}
	
		// Vérifiez que l'ajout s'est bien passé
		// je créee un objet "miroir" à l'image de ce que doit me retourner la méthode pour pouvoir la comparer dans mon assert
		Map<Person, List<MedicalRecord>> mapTest = createMapPersonMedicalRecordList();
		
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);

		assertEquals ("les informations doivent être identiques dans les deux maps", mapTest, donneesLieesServiceLocal.getPersonMedicalRecordMap());
	}
	
	@Test
	public void getCommunityEmailTest() throws Exception {
		String erreurLoggee = "";
		DonneesLieesService donneesLieesServiceLocal = null;
	
		// Appelez la méthode d'ajout	
		try {
			donneesLieesServiceLocal = donneesLieesService.getCommunityEmail("paddaVille");
		} catch (Exception e) {
			erreurLoggee = e.toString();
			e.printStackTrace(System.out);
		}
	
		// Vérifiez que l'ajout s'est bien passé
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		
		assertEquals("la liste de personnes doit être égale à 4", 4, donneesLieesServiceLocal.getListPerson().size());
		assertEquals("le 1er mail de la liste doit être égal à appa@email.com ", "appa@email.com", donneesLieesServiceLocal.getListPerson().get(0).getEmail());
		assertEquals("le 2e mail de la liste doit être égal à steve@email.com ", "steve@email.com", donneesLieesServiceLocal.getListPerson().get(1).getEmail());
		assertEquals("le 3e mail de la liste doit être égal à moja@email.com ", "moja@email.com", donneesLieesServiceLocal.getListPerson().get(2).getEmail());
		assertEquals("le 4e mail de la liste doit être égal à john@email.com ", "john@email.com", donneesLieesServiceLocal.getListPerson().get(3).getEmail());
	}
	
	
/*-------méthodes utiles aux tests ------------------------------------------------------------------------------------------------*/

	private Date createBirthdate(String birthdate) throws ParseException {
	 SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
         Date birthdateDate = simpleDateFormat.parse(birthdate);
         return birthdateDate;
    }
    
	private Map<Person, List<MedicalRecord>> createMapPersonMedicalRecordList() throws ParseException{
		List<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>();
		List<MedicalRecord> medicalRecordList2 = new ArrayList<MedicalRecord>();
		Map<Person, List<MedicalRecord>> mapTestPersonMedicalRecord = new HashMap<Person, List<MedicalRecord>>();
		Date birthdate1 = createBirthdate("2021-04-14");
		Person person1 = new Person("Appa", "Paddaone", "36 rue des paddas", "paddaVille", 37100, "841-874-7462", "appa@email.com");
		MedicalRecord medicalRecord1 = new MedicalRecord("Appa", "Paddaone", birthdate1, List.of("baytril"), List.of("avocat"));
		medicalRecordList.add(medicalRecord1);
		mapTestPersonMedicalRecord.put(person1, medicalRecordList);
		
		
		Date birthdate2 = createBirthdate("1960-02-23");
		Person person2 = new Person("John", "Paddaone", "36 rue des paddas", "paddaVille", 37100, "811-874-7462", "john@email.com");
		MedicalRecord medicalRecord2 = new MedicalRecord("John", "Paddaone", birthdate2, List.of(""), List.of(""));
		medicalRecordList2.add(medicalRecord2);
		mapTestPersonMedicalRecord.put(person2, medicalRecordList2);
		return mapTestPersonMedicalRecord;
	}

}
