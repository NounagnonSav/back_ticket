package fr.istic.taa.jaxrs.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.istic.taa.jaxrs.dao.UtilisateurDao;
import fr.istic.taa.jaxrs.domaine.Utilisateur;
import fr.istic.taa.jaxrs.utils.Constants;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/utilisateur")
@Produces({"application/json", "application/xml"})
public class UtilisateursResource {

    UtilisateurDao dao = new UtilisateurDao();

    // endpoint pour afficher un utilisateur
    @GET
    @Path("/{id}")
    public Response getUtilisateurById(@PathParam("id") Long utilisateurId)  {
        // return user
        Utilisateur existingUtilisateur = dao.findOne(utilisateurId);
        if (existingUtilisateur == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.USER_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();        }
        return Response.ok().entity(existingUtilisateur).build();
    }

    // endpoint pour lister tout utilisateur
    @GET
    @Path("/")
    public Response getUtilisateur() {
        List<Utilisateur> utilisateurs = dao.findAll();
        if (utilisateurs == null || utilisateurs.isEmpty()) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.USER_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        } else {
            JsonArray jsonArray = new Gson().toJsonTree(utilisateurs).getAsJsonArray();
            // Créer l'objet JSON principal
            JsonObject json = new JsonObject();
            json.add("results", jsonArray);
            json.addProperty("total_results", jsonArray.size());

            // Convertir l'objet JSON en une chaîne de caractères
            String jsonString = json.toString();
            return Response.ok().entity(jsonString).build();
        }
    }

    // endpoint pour créér un nouvel utilisateur
    @POST
    @Consumes("application/json")
    public Response addUtilisateur(
            @Parameter(description = "Utilisateur object that needs to be added to the store", required = true) Utilisateur utilisateur) {
        // add user
        if (utilisateur == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, "L'objet Utilisateur est null");
            return Response.status(Response.Status.BAD_REQUEST).entity(json.toString()).build();
        }
        dao.save(utilisateur);
        JsonObject json = new JsonObject();
        json.addProperty(Constants.MESSAGE, "Utilisateur ajouté avec succès");
        return Response.status(Response.Status.CREATED).entity(json.toString()).build();

    }

    // endpoint pour mettre à jour un utilisateur
    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateUtilisateur(
            @Parameter(description = "ID of the utilisateur to be updated", required = true) @PathParam("id") Long utilisateurId,
            @Parameter(description = "Utilisateur object that needs to be updated", required = true) Utilisateur updatedUtilisateur) {
        Utilisateur existingUtilisateur = dao.findOne(utilisateurId);
        if (existingUtilisateur == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.USER_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }

        // Mettre à jour les champs existants avec les nouvelles valeurs uniquement s'ils sont non null dans l'objet mis à jour
        if (updatedUtilisateur.getUsername() != null) {
            existingUtilisateur.setUsername(updatedUtilisateur.getUsername());
        }
        if (updatedUtilisateur.getName() != null) {
            existingUtilisateur.setName(updatedUtilisateur.getName());
        }
        if (updatedUtilisateur.getRole() != null) {
            existingUtilisateur.setRole(updatedUtilisateur.getRole());
        }

        // Mettre à jour l'utilisateur dans la base de données
        dao.update(existingUtilisateur);

        // Retourner l'utilisateur mis à jour
        return Response.ok().entity(existingUtilisateur).build();
    }

    // endpoint pour supprimer un utilisateur
    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteUtilisateur(
            @Parameter(description = "ID of the utilisateur to be deleted", required = true) @PathParam("id") Long utilisateurId) {
        Utilisateur existingUtilisateur = dao.findOne(utilisateurId);
        if (existingUtilisateur == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.USER_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }

        dao.delete(existingUtilisateur); // Supprimer l'utilisateur de la base de données
        JsonObject json = new JsonObject();
        json.addProperty(Constants.MESSAGE, "Utilisateur supprimé avec succès");

        return Response.ok().entity(json.toString()).build();
    }
}
