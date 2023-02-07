package org.acme.password.user;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("api/user")
public class UserResource {

    @Inject
    UserRepository userRepository;

    @POST
    public Response register(final User user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final User createdUser = userRepository.registerUser(user);
        return Response.ok(createdUser).build();
    }

    @GET
    @Path("/check")
    public Response checkPassword(@QueryParam("email") final String email, @QueryParam("password") final String password) {
        if (email == null || password == null) {
            System.out.println("Missing param");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User user = userRepository.getUserByEmail(email);
        if(user == null) {
            System.out.println("No user found!");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        System.out.println("User found!");

        final boolean correct = userRepository.checkPassword(user, password);
        return Response.ok(correct).build();
    }
}
