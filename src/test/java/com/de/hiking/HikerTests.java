package com.de.hiking;

import com.de.hiking.models.Hiker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HikerTests extends HikingApiApplicationTest {

    @DisplayName("Checks that the registration of a Hiker works")
    @Test
    void checkCreateHiker() {

        Hiker hiker = new Hiker();
        hiker.setAge(22);
        hiker.setMail("randommail@random.com");
        hiker.setName("John");
        hiker.setSurname("Doe");

        ResponseEntity<Hiker> result = this.restTemplate.postForEntity(API_PATH + "/hiker", hiker, Hiker.class);

        assert (result.getBody().getHikerId() != null);

    }

    @DisplayName("Checks that the deletion a Hiker works")
    @Test
    void checkDeleteHiker() {

        Hiker hiker = new Hiker();
        hiker.setAge(22);
        hiker.setMail("randommail@random.com");
        hiker.setName("John");
        hiker.setSurname("Doe");
        ResponseEntity<Hiker> result = this.restTemplate.postForEntity(API_PATH + "/hiker", hiker, Hiker.class);
        assert (result.getBody().getHikerId() != null);

        Hiker hikertToDelete = result.getBody();

        ResponseEntity<Object> resultDelete = this.restTemplate.exchange(
                API_PATH + "/hiker/" + hikertToDelete.getHikerId(),
                HttpMethod.DELETE,
                new HttpEntity<>(""),
                Object.class);

        assertEquals(HttpStatus.OK, resultDelete.getStatusCode());
    }

    @DisplayName("Checks that the deletion of a non existing hiker does not work")
    @Test
    void checkDeleteNonExistingHiker() {

        ResponseEntity<Object> result = this.restTemplate.exchange(
                API_PATH + "/hiker/" + UUID.randomUUID(),
                HttpMethod.DELETE,
                new HttpEntity<>(""),
                Object.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }

    @DisplayName("Checks that retrieval of all hikers works")
    @Test
    void checkGetHikers() {

        ResponseEntity<Hiker[]> result = this.restTemplate.getForEntity(API_PATH + "/hikers", Hiker[].class);
        List<Hiker> hikers = Arrays.asList(result.getBody());
        assert (hikers.size() > 0);
    }

    @DisplayName("Checks that retrieving a non existing hiker returns an error message")
    @Test
    void checkGetNonExistingHiker() {
        ResponseEntity<Object> resultGet = this.restTemplate.getForEntity(API_PATH + "/hiker/" + UUID.randomUUID(), Object.class);
        assertEquals(HttpStatus.NOT_FOUND, resultGet.getStatusCode());
    }

    @DisplayName("Checks that retrieving an existing hiker works")
    @Test
    void checkGetHiker() {
        ResponseEntity<Hiker> resultGet = this.restTemplate.getForEntity(API_PATH + "/hiker/3c8097ef-ecef-43ea-ae43-3cb88cd7ab7e", Hiker.class);
        assertEquals(HttpStatus.OK, resultGet.getStatusCode());
    }

}
