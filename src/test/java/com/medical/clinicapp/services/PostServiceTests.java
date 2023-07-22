package com.medical.clinicapp.services;

import com.medical.clinicapp.ClinicappApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostServiceTests extends ClinicappApplicationTests {


    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DirtiesContext
    void shouldCreateANewService() {
        Date nowDate = new Date();
        //Create service
        Service service = new Service(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),"Servicio 1", nowDate,nowDate);
        ResponseEntity<Void> createResponseService = restTemplate.postForEntity("/services", service, Void.class);

        //Validate if exist
        ResponseEntity<Service> serviceResponse = restTemplate.getForEntity("/services/" + service.getId(), Service.class);
        assertThat(serviceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Service serviceModel = serviceResponse.getBody();
        serviceModel.setUpdatedAt(nowDate);
        serviceModel.setCreatedAt(nowDate);

        //Validate if service is same
        assertEquals(serviceModel,service);
    }
}
