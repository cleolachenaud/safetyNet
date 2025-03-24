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
import java.util.List;
import java.util.Locale;

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
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;
import com.oprprojet.safetyNet.service.MedicalRecordService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MedicalRecordServiceTest {
    @Mock
    private Reader jsonReaderMock;

    @Mock
    private Writer jsonWriterMock;

    @InjectMocks
    private MedicalRecordService medicalRecordService;    
    
    @Captor
    ArgumentCaptor<Donnees> argumentCaptorDonnees;
    
    
    
    @BeforeEach
    public void setUp() throws Exception {
    	
        // Créez un objet MedicalRecord à ajouter
    	Donnees donneesBrutes = new Donnees();
    	donneesBrutes.setFireStations(new ArrayList<FireStation>());
    	donneesBrutes.setPersons(new ArrayList<Person>());
    	List<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>();
    	Date birthdate = createBirthdate("2021-04-14");
    	MedicalRecord medicalRecord1 = new MedicalRecord("Appa", "Paddaone", birthdate, List.of("baytril"), List.of("avocat"));
    	Date birthdate2 = createBirthdate("2021-05-19");
    	MedicalRecord medicalRecord2 = new MedicalRecord("Moja", "Paddatwo", birthdate2, List.of(""), List.of("avocat", "chocolat"));
 
    	medicalRecordList.add(medicalRecord1);
    	medicalRecordList.add(medicalRecord2);
    	donneesBrutes.setMedicalRecords(medicalRecordList);
    	

        // Simuler le comportement du reader et writer
        Mockito.when(jsonReaderMock.jsonReader()).thenReturn(donneesBrutes);

    }

    @Test
    public void addMedicalRecordTest() throws Exception {
    	String erreurLoggee = "";
    	Date birthdate = createBirthdate("2021-06-21");
    	MedicalRecord medicalRecord3 = new MedicalRecord("Marley", "Paddathree", birthdate, List.of(""), List.of("avocat"));
     	try {
     		medicalRecordService.addMedicalRecord(new MedicalRecord("Marley", "Paddathree", birthdate, List.of(""), List.of("avocat")));
     	} catch (Exception e) {
     		erreurLoggee = e.toString();
     	}

        // Vérifiez que l'ajout s'est bien passé
        Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
        assertEquals("la liste de données doit être égale à 3", 3, argumentCaptorDonnees.getValue().getMedicalRecords().size());
        assertEquals("les éléments de la variable station doivent être identiques à ceux capturées par le mock", medicalRecord3, argumentCaptorDonnees.getValue().getMedicalRecords().get(2));
    }
   
    @Test
    public void updateMedicalRecordTest() throws Exception {
    	String erreurLoggee = "";

     	// Appelez la méthode d'ajout
    	Date birthdate = createBirthdate("2021-04-14");
    	MedicalRecord medicalRecord1 = new MedicalRecord("Appa", "Paddaone", birthdate, List.of("baytril"), List.of("avocat"));
    	
     	try {
     		medicalRecordService.updateMedicalRecord(new MedicalRecord("Appa", "Paddaone", birthdate, List.of("baytril"), List.of("avocat")));
     	} catch (Exception e) {
     		erreurLoggee = e.toString();
     	}

        // Vérifiez que l'ajout s'est bien passé
        Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
        assertEquals("la liste de données doit être égale à 2", 2, argumentCaptorDonnees.getValue().getMedicalRecords().size());
        assertEquals("les éléments de la variable station doivent être identiques à ceux capturées par le mock", medicalRecord1, argumentCaptorDonnees.getValue().getMedicalRecords().get(0));
    }
  
    @Test
    public void deleteMedicalRecordTest() throws Exception {
    	String erreurLoggee = "";

     	// Appelez la méthode d'ajout
                
     	try {
     		medicalRecordService.deleteMedicalRecord("Moja", "Paddatwo");
     	} catch (Exception e) {
     		erreurLoggee = e.toString();
     	}

        // Vérifiez que l'ajout s'est bien passé
        Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
        assertEquals("la liste de données doit être égale à 1", 1, argumentCaptorDonnees.getValue().getMedicalRecords().size());
    }
    private Date createBirthdate(String birthdate) throws ParseException {
    	 SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
         Date birthdateDate = simpleDateFormat.parse(birthdate);
         return birthdateDate;
    }
}
