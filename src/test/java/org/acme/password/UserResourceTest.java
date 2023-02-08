package org.acme.password;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import org.acme.password.user.User;
import org.acme.password.user.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

@QuarkusTest
public class UserResourceTest {

    @InjectMock
    UserRepository repository;

    @Test
    public void testRegister(){
        User user = new User("josef@gmail.com", "Sicher123", "+43 667667");
        Mockito.when(repository.registerUser(ArgumentMatchers.any(User.class))).thenReturn(user);

        RestAssured.given()
                .body("{\"email\":\"josef@gmail.com\",\"password\":\"Sicher123\",\"telephoneNumber\":\"+43 667667\"}")
                .header("Content-type", "application/json")
                .when()
                .post("/api/user")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void testCheckPassword(){
        User user = new User("josef@gmail.com", "Sicher123", "+43 667667");
        Mockito.when(repository.checkPassword(ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(true);
        Mockito.when(repository.getUserByEmail(ArgumentMatchers.anyString())).thenReturn(user);

        RestAssured.given().when()
                .get("/api/user/check?email=josef@gmail.com&password=Sicher123")
                .then()
                .statusCode(Response.Status.OK.getStatusCode()).body(CoreMatchers.is("true"));
    }

    @Test
    public void testCheckPasswordWrongPw(){
        User user = new User("josef@gmail.com", "Sicher123", "+43 667667");
        Mockito.when(repository.checkPassword(ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(repository.getUserByEmail(ArgumentMatchers.anyString())).thenReturn(user);

        RestAssured.given().when()
                .get("/api/user/check?email=josef@gmail.com&password=Sicher1234")
                .then()
                .statusCode(Response.Status.OK.getStatusCode()).body(CoreMatchers.is("false"));
    }

    @Test
    public void testCheckPasswordWithNotExistingUser(){
        Mockito.when(repository.checkPassword(ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(repository.getUserByEmail(ArgumentMatchers.anyString())).thenReturn(null);

        RestAssured.given().when()
                .get("/api/user/check?email=josef@gmail.com&password=Sicher1234")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }


}
