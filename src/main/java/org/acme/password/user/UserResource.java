package org.acme.password.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.acme.unicornrides.passenger.Passenger;
import org.acme.unicornrides.unicorn.Unicorn;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;

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
    public Response findByName(@QueryParam("email") final String email, @QueryParam("password") final String password) {
        if (email == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final boolean correct = userRepository.checkPassword(new User(), password);
        return Response.ok(correct).build();
    }
}
