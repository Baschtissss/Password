package org.acme.unicornrides.unicorn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("api/unicorns")
public class UnicornResource {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Inject
    UnicornRepository unicornRepository;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") final String id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Unicorn unicorn = unicornRepository.findById(Long.parseLong(id));
        return Response.ok(unicorn).build();
    }

    @POST
    public Response add(final Unicorn unicorn) {
        final Unicorn createdUnicorn = unicornRepository.create(unicorn);
        final URI resourceUri = URI.create("/api/unicorns/" + createdUnicorn.getId());
        return Response.created(resourceUri).build();
    }

    @PATCH
    @Path("/{id}")
    public Response updateWeight(@PathParam("id") final String id, final String body) throws JsonProcessingException {
        final Unicorn unicorn = unicornRepository.findById(Long.parseLong(id));
        final int weight = OBJECT_MAPPER.readTree(body).get("weightKg").asInt();
        unicorn.setWeightKg(weight);
        return Response.ok(unicornRepository.update(unicorn)).build();
    }
}
