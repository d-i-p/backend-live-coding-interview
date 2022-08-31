package com.de.hiking;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = HikingApiApplication.class)
public abstract class HikingApiApplicationTest {

    protected String API_PATH = "/api/hiking/";

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected DataSource datasource;

    protected static boolean dataLoaded = false;


    //sets up some hikers on which do the testing
    @BeforeAll
    public void setup() throws SQLException {
        if (!dataLoaded) {
            try (Connection con = datasource.getConnection()) {
                ScriptUtils.executeSqlScript(con, new ClassPathResource("add_hikers.sql"));
                dataLoaded = true;
            }
        }
    }

    //deletes the hiker samples used in testing
    @AfterAll
    public void destroy() throws SQLException {
        if (dataLoaded) {
            try (Connection con = datasource.getConnection()) {
                ScriptUtils.executeSqlScript(con, new ClassPathResource("remove_hikers_and_bookings.sql"));
                dataLoaded = false;
            }
        }
    }
}
