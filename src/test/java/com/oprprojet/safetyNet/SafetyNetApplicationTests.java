package com.oprprojet.safetyNet;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;
import com.oprprojet.safetyNet.service.FireStationService;

@SpringBootTest
class SafetyNetApplicationTests {

	@Test
	void contextLoads() {
		Assert.assertTrue("test vide", true);
	}

}
