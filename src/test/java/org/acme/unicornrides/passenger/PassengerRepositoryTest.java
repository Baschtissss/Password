package org.acme.unicornrides.passenger;

import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PassengerRepositoryTest {
    @Inject
    PassengerRepository passengerRepository;

    @Test
    @Order(10)
    public void testFindByName() {
        Collection<Passenger> passengers = passengerRepository.findByLastName("Customer2");

        Assertions.assertThat(passengers.size()).isEqualTo(1);
        Assertions.assertThat(passengers.stream().findFirst().get().getId()).isEqualTo(102);

    }

    @Test
    @Order(11)
    public void testFindAll() {
        Collection<Passenger> passengers = passengerRepository.findAll();
        Assertions.assertThat(passengers.size()).isEqualTo(5);
    }

    @Test
    @Order(12)
    public void testFindById() {
        Passenger passenger = passengerRepository.findById(101);

        Assertions.assertThat(passenger).isNotNull();
        Assertions.assertThat(passenger.getId()).isEqualTo(101);
    }

    @Test
    @Order(13)
    public void testRegister() {
        Passenger passenger = new Passenger(0, "Anton", "Maier",
                LocalDate.parse("1999-02-19"),
                LocalDate.parse("2023-01-01"),
                Arrays.asList());

        Passenger savedPassenger = passengerRepository.registerPassenger(passenger);

        Assertions.assertThat(savedPassenger.getId()).isEqualTo(1);

    }
}
