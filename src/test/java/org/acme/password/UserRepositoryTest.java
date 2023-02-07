package org.acme.password;

import io.quarkus.test.TestTransaction;
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
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Arrays;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    @Test
    @TestTransaction
    @Order(13)
    public void testRegister() {
        User user = new User("peter@gmail.com","12345678","+43667667");

        User savedUser = userRepository.registerUser(user);

        Assertions.assertThat(savedUser.getEmail()).isEqualTo("peter@gmail.com");
    }

    @Test
    @TestTransaction
    @Order(14)
    public void testRegisterAlreadyExistingUser() {

        User user = new User("peter2@gmail.com","12345678","+43667667");
        User savedUser = userRepository.registerUser(user);
        Assertions.assertThat(savedUser.getEmail()).isEqualTo("peter2@gmail.com");
        User savedUser2 = userRepository.registerUser(user);
        Assertions.assertThat(savedUser2).isEqualTo(null);
    }
    @Test
    @TestTransaction
    public void testRegisterUserWithWrongEmail() {
        User user = new User("peter2@gmailcom","12345678","+43667667");
        try {
            userRepository.registerUser(user);
        }catch(ConstraintViolationException e){
            Assertions.assertThatExceptionOfType(ConstraintViolationException.class);
        }

        User user2 = new User("peter2gmail.com","12345678","+43667667");
        try {
            userRepository.registerUser(user2);
        }catch(ConstraintViolationException e){
            Assertions.assertThatExceptionOfType(ConstraintViolationException.class);
        }
    }

    @Test
    @TestTransaction
    public void testRegisterUserWithNoEmail() {
        User user = new User(null,"12345678","+43667667");
        try {
            userRepository.registerUser(user);
        }catch(IllegalArgumentException e){
            Assertions.assertThatExceptionOfType(IllegalArgumentException.class);
        }

    }

    @Test
    @TestTransaction
    public void testRegisterUserWithNoPassword() {
        User user = new User("jona@gmail.com",null,"+43667667");
        try {
            userRepository.registerUser(user);
        }catch(IllegalArgumentException e){
            Assertions.assertThatExceptionOfType(IllegalArgumentException.class);
        }

    }

    @Test
    @TestTransaction
    @Order(15)
    public void testCheckPassword(){
        User user = new User("hans@gmail.com","1234567B",null);
        user = userRepository.registerUser(user);
        Assertions.assertThat(userRepository.checkPassword(user, "1234567B")).isEqualTo(true);
        Assertions.assertThat(userRepository.checkPassword(user, "1234457b")).isEqualTo(false);
    }

    @Test
    @TestTransaction
    @Order(16)
    public void testCheckPasswordWithNotExistingUser(){
        Assertions.assertThat(userRepository.checkPassword(null, "12345")).isEqualTo(false);
    }


}
