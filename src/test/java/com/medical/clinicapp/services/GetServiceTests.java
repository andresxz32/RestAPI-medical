package com.medical.clinicapp.services;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.medical.clinicapp.ClinicappApplicationTests;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GetServiceTests extends ClinicappApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnAPageOfServices() {
        ResponseEntity<String> response = restTemplate.getForEntity("/services?page=0&size=1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnASortedPageOfServices() {
        ResponseEntity<String> response = restTemplate.getForEntity("/services?page=0&size=1&sort=id,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray read = documentContext.read("$[*]");
        assertThat(read.size()).isEqualTo(1);

        String id = documentContext.read("$[0].id");
        assertThat(id).isEqualTo("550e8400-e29b-41d4-a716-446655440003");
    }

    @Test
    void shouldReturnASortedPageOfServicesWithNoParametersAndUseDefaultValues() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/services", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(2);

        JSONArray amounts = documentContext.read("$..id");
        assertThat(amounts).containsExactly("550e8400-e29b-41d4-a716-446655440002","550e8400-e29b-41d4-a716-446655440003");
    }

    @Test
    void shouldReturnACashCardWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/services/550e8400-e29b-41d4-a716-446655440002", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(response.getBody());

        String id = documentContext.read("$.id");
        assertThat(id).isEqualTo("550e8400-e29b-41d4-a716-446655440002");

    }
}

