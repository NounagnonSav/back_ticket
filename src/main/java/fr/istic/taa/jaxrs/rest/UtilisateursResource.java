package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domaine.Utilisateur;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("utilisateur")
@Produces({"application/json", "application/xml"})
public class UtilisateursResource {

    @GET
    @Path("/{utilisateurId}")
    public Utilisateur getUtilisateurById(@PathParam("utilisateurId") Long utilisateurId)  {
        // return pet
        return new Utilisateur();
    }

    @GET
    @Path("/")
    public Utilisateur getUtilisateur(Long projectId)  {
        return new Utilisateur();
    }

    @POST
    @Consumes("application/json")
    public Response addUtilisateur(
            @Parameter(description = "Utilisateur object that needs to be added to the store", required = true) Utilisateur utilisateur) {
        // add pet
        return Response.ok().entity("SUCCESS").build();
    }
}
