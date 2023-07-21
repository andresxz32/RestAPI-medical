package com.medical.clinicapp;

import com.medical.clinicapp.admissions.Admission;
import com.medical.clinicapp.patients.Patient;
import com.medical.clinicapp.services.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClinicappApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	@DirtiesContext
	void shouldCreateANewPatient() {
		UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
		Patient newPatient = new Patient(id,"Sarah","Smith",new Date(),new Date());

		System.out.println(newPatient.toString());
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/patients", newPatient, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		//Validate if really exist

	}

	@Test
	@DirtiesContext
	void shouldCreateANewAdmission() {
		ResponseEntity<Patient> response = restTemplate.getForEntity("/patients/550e8400-e29b-41d4-a716-446655440000",Patient.class);
		Patient patient = response.getBody();


		Service service = new Service(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),"Servicio 1", new Date(),new Date());
		ResponseEntity<Void> createResponseService = restTemplate.postForEntity("/services", service, Void.class);

		UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
		List<Service> services = Arrays.asList(service);
		Admission newAdmission = new Admission(id,new Date(),new Date(), patient,services);

		ResponseEntity<Void> createResponseAdmission = restTemplate.postForEntity("/admissions", newAdmission, Void.class);
		assertThat(createResponseAdmission.getStatusCode()).isEqualTo(HttpStatus.CREATED);

	}


}
