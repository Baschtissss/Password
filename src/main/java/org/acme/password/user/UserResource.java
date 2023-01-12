package org.acme.password.user;

import org.acme.unicornrides.passenger.Passenger;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
}
