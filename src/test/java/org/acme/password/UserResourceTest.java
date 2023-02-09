package org.acme.password;

import io.quarkus.test.Mock;
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

    @Test
    public void testForgotPassword(){
        Mockito.when(repository.forgotPassword(ArgumentMatchers.anyString())).thenReturn("TWDK");

        RestAssured.given().when()
                .post("/api/user/resetCode?email=josef@gmail.com")
                .then()
                .statusCode(Response.Status.OK.getStatusCode()).body(CoreMatchers.is("TWDK"));
    }

    @Test
    public void testCheckCode(){
        Mockito.when(repository.checkCode(ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn("TWKE");

        RestAssured.given().when()
                .get("/api/user/resetCode?email=josef@gmail.com&code=TWDK")
                .then()
                .statusCode(Response.Status.OK.getStatusCode()).body(CoreMatchers.is("TWKE"));
    }

    @Test
    public void testChangePassword(){
        Mockito.when(repository.changePassword(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(true);

        RestAssured.given().when()
                .put("/api/user/changePassword?email=josef@gmail.com&oldPW=TWKE&newPw=Sicher123")
                .then()
                .statusCode(Response.Status.OK.getStatusCode()).body(CoreMatchers.is("true"));
    }

    @Test
    public void testChangePasswordWithMissingParam(){
        RestAssured.given().when()
                .put("/api/user/changePassword?email=josef@gmail.com&oldPW=TWKE")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }
}
