package org.acme.password;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.password.user.User;
import org.acme.password.user.UserRepository;
import org.acme.unicornrides.passenger.Passenger;
import org.acme.unicornrides.passenger.PassengerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Arrays;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    @Inject
    UserRepository userRepository;


    @Test
    @Order(13)
    public void testRegister() {
        Passenger passenger = new Passenger(0, "Anton", "Maier",
                LocalDate.parse("1999-02-19"),
                LocalDate.parse("2023-01-01"),
                Arrays.asList());

        User user = new User("peter@gmail.com","1234","+43667667");

        User savedUser = userRepository.registerUser(user);

        Assertions.assertThat(savedUser.getEmail()).isEqualTo("peter@gmail.com");

    }
}
