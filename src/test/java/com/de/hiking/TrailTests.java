package com.de.hiking;

import com.de.hiking.models.Trail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;


class TrailTests extends HikingApiApplicationTest {

    @DisplayName("Checks that retrieval of all trails works")
    @Test
    void checkGetTrails() {

        ResponseEntity<Trail[]> result = this.restTemplate.getForEntity(API_PATH + "/trails", Trail[].class);
        List<Trail> trails = Arrays.asList(result.getBody());
        assert (trails.size() > 0);
    }

}
