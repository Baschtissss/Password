package org.acme.unicornrides.passenger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;

@Path("api/passengers")
public class PassengerResource {

    @Inject
    PassengerRepository passengerRepository;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") final String id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Passenger passenger = passengerRepository.findById(Long.parseLong(id));
        return Response.ok(passenger).build();
    }

    @GET
    @Path("/filter")
    public Response findByName(@QueryParam("name") final String name) {
        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Collection<Passenger> passengers = passengerRepository.findByLastName(name);
        return Response.ok(passengers).build();
    }

    @POST
    public Response register(final Passenger passenger) {
        if (passenger == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Passenger createdPassenger = passengerRepository.registerPassenger(passenger);
        return Response.created(URI.create("/api/passengers/" + createdPassenger.getId())).build();
    }


}
