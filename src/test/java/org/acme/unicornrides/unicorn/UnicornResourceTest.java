package org.acme.unicornrides.unicorn;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UnicornResourceTest {

    @InjectMock
    UnicornRepository unicornRepository;

    @Test
    public void testFindById() {
        Mockito.when(unicornRepository.findById(1)).thenReturn(new Unicorn(1, "TestUnicorn", 44, 55, Arrays.asList()));
        RestAssured.given().when()
                .get("/api/unicorns/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is("{\"id\":1,\"name\":\"TestUnicorn\",\"weightKg\":44,\"maxSpeedKmpH\":55,\"reservations\":[]}"));
    }

    @Test
    public void testAddNewUnicorn() {
        Mockito.when(unicornRepository.create(ArgumentMatchers.any(Unicorn.class))).thenReturn(new Unicorn(1, "TestUnicorn", 44, 55, Arrays.asList()));
        RestAssured.given()
                .header("Content-type", "application/json")
                .body("{\"name\":\"TestUnicorn\",\"weightKg\":44,\"maxSpeedKmpH\":55,\"reservations\":[]}")
                .when()
                .post("/api/unicorns")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("Location", "http://localhost:8081/api/unicorns/1");
    }

    @Test
    public void testUpdateWeight() {

        Mockito.when(unicornRepository.findById(1)).thenReturn(new Unicorn(1, "TestUnicorn", 44, 55, Arrays.asList()));
        Mockito.when(unicornRepository.update(ArgumentMatchers.any(Unicorn.class))).thenReturn(new Unicorn(1, "TestUnicorn", 66, 55, Arrays.asList()));
        RestAssured.given()
                .header("Content-type", "application/json")
                .body("{\"weightKg\": 66}")
                .when()
                .patch("/api/unicorns/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is("{\"id\":1,\"name\":\"TestUnicorn\",\"weightKg\":66,\"maxSpeedKmpH\":55,\"reservations\":[]}"));
    }

}
