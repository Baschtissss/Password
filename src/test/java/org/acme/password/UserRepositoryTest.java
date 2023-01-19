package org.acme.password;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.Hash.HashMethods;
import org.acme.password.user.User;
import org.acme.password.user.UserRepository;
import org.acme.unicornrides.passenger.Passenger;
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
        User user = new User("peter@gmail.com","1234","+43667667");

        User savedUser = userRepository.registerUser(user);

        Assertions.assertThat(savedUser.getEmail()).isEqualTo("peter@gmail.com");
    }

    @Test
    @Order(14)
    public void testRegisterAlreadyExistingUser() {

        User user = new User("peter@gmail.com","1234","+43667667");

        User savedUser = userRepository.registerUser(user);

        Assertions.assertThat(savedUser.getEmail()).isEqualTo("peter@gmail.com");

        User savedUser2 = userRepository.registerUser(user);

        Assertions.assertThat(savedUser2).isEqualTo(null);
    }

    @Test
    @Order(15)
    public void testCheckPassword(){
        User user = new User("peter@gmail.com","1234","+43667667");

        Assertions.assertThat(userRepository.checkPassword(user, "1234")).isEqualTo(true);
        Assertions.assertThat(userRepository.checkPassword(user, "12345")).isEqualTo(false);
    }


}
