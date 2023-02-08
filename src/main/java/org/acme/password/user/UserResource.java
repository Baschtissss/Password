package org.acme.password.user;


import org.acme.password.ResetCode;

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
        if(createdUser == null){
            System.out.println("Nutzer existiert bereits!");
            return Response.status(Response.Status.CONFLICT).build();
        }
        System.out.println("Nutzer: "+createdUser.getEmail()+" wurde erstellt!");
        return Response.ok(createdUser).build();
    }

    @GET
    @Path("/check")
    public Response checkPassword(@QueryParam("email") final String email, @QueryParam("password") final String password) {
        if (email == null || password == null) {
            System.out.println("Fehlender Parameter");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User user = userRepository.getUserByEmail(email);
        if(user == null) {
            System.out.println("Kein Nutzer gefunden!");
            return Response.status(Response.Status.NOT_FOUND).build();
        }


        final boolean correct = userRepository.checkPassword(email, password);

        if(correct)
            System.out.println("Passwort stimmt überein!");
        else
            System.out.println("Falsches Passwort!");

        return Response.ok(correct).build();
    }

    @Path("/resetCode")
    @POST
    public Response forgotPassword(@QueryParam("email") final String email){
        if(email == null){
            System.out.println("Fehlender Parameter!");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String code = userRepository.forgotPassword(email);

        if(code == null){
            System.out.println("Kein User wurde gefunden!");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        System.out.println("Code "+code+" wurde für "+ email+" erstellt!");

        return Response.ok(code).build();
    }

    @Path("/resetCode")
    @GET
    public Response checkCode(@QueryParam("email") String email,@QueryParam("code") String code){
        if(email == null || code == null){
            System.out.println("Fehlender Parameter!");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String check = userRepository.checkCode(email, code);
        if(check == null)
            System.out.println("Falscher Code!");
        else
            System.out.println("Neues Passwort: "+check);

        return Response.ok(check).build();
    }

    @Path("/changePassword")
    @PUT
    public Response changePassword(@QueryParam("email") String email, @QueryParam("oldPw") String oldPw, @QueryParam("newPw") String newPw){
        if(email == null || oldPw == null || newPw == null){
            System.out.println("Fehlender Parameter!");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        boolean check = userRepository.changePassword(email,oldPw,newPw);

        if(check)
            System.out.println("Passwort von "+email+" wurde geändert!");
        else
            System.out.println("Passwort konnte nicht geändert werden!");

        return Response.ok(check).build();
    }

}
