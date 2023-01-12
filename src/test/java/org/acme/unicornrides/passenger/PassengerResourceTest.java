package org.acme.unicornrides.passenger;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Arrays;

@QuarkusTest
public class PassengerResourceTest {
    @InjectMock
    PassengerRepository passengerRepository;

    @Test
    public void testFindById() {
        Passenger passenger = new Passenger(1, "FirstName", "LastName",
                LocalDate.parse("1999-08-07"),
                LocalDate.parse("2022-05-17"),
                Arrays.asList());
        Mockito.when(passengerRepository.findById(1)).thenReturn(passenger);

        RestAssured.given().when()
                .get("api/passengers/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(CoreMatchers.is("{\"id\":1,\"firstName\":\"FirstName\",\"lastName\":\"LastName\",\"birthday\":\"1999-08-07\",\"registrationDate\":\"2022-05-17\",\"reservations\":[]}"));
    }

    @Test
    public void testFindByName() {
        Passenger passenger = new Passenger(1, "FirstName", "LastName",
                LocalDate.parse("1999-08-07"),
                LocalDate.parse("2022-05-17"),
                Arrays.asList());
        Mockito.when(passengerRepository.findByLastName("LastName")).thenReturn(Arrays.asList(passenger));

        RestAssured.given().when()
                .get("api/passengers/filter?name=LastName")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(CoreMatchers.is("[{\"id\":1,\"firstName\":\"FirstName\",\"lastName\":\"LastName\",\"birthday\":\"1999-08-07\",\"registrationDate\":\"2022-05-17\",\"reservations\":[]}]"));
    }

    @Test
    public void testCreatePassenger() {
        Passenger passenger = new Passenger(1, "FirstName", "LastName",
                LocalDate.parse("1999-08-07"),
                LocalDate.parse("2022-05-17"),
                Arrays.asList());
        Mockito.when(passengerRepository.registerPassenger(ArgumentMatchers.any(Passenger.class))).thenReturn(passenger);

        RestAssured.given()
                .body("{\"firstName\":\"FirstName\",\"lastName\":\"LastName\",\"birthday\":\"1999-08-07\",\"registrationDate\":\"2022-05-17\",\"reservations\":[]}")
                .header("Content-type", "application/json")
                .when()
                .post("/api/passengers")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("Location", "http://localhost:8081/api/passengers/1");


    }
}
