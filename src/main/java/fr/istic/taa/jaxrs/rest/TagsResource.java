package fr.istic.taa.jaxrs.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.istic.taa.jaxrs.dao.TagsDao;
import fr.istic.taa.jaxrs.domaine.Tags;
import fr.istic.taa.jaxrs.utils.Constants;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/tags")
@Produces({"application/json", "application/xml"})
public class TagsResource {

    TagsDao dao = new TagsDao();
    @GET
    @Path("/{tagId}")
    public Response getTagsById(@PathParam("tagId") Long tagId)  {
        // return Tag
        Tags existingTag = dao.findOne(tagId);
        if (existingTag == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.TAGS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }
        return Response.ok().entity(existingTag).build();
    }

    @GET
    @Path("/")
    public Response getTags()  {
        List<Tags> tags = dao.findAll();
        if (tags == null || tags.isEmpty()) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.TAGS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        } else {
            JsonArray jsonArray = new Gson().toJsonTree(tags).getAsJsonArray();
            // Créer l'objet JSON principal
            JsonObject json = new JsonObject();
            json.add("results", jsonArray);
            json.addProperty("total_results", jsonArray.size());

            // Convertir l'objet JSON en une chaîne de caractères
            String jsonString = json.toString();
            return Response.ok().entity(jsonString).build();
        }
    }


    @POST
    @Consumes("application/json")
    public Response addTag(
            @Parameter(description = "Tag object that needs to be added to the store", required = true) Tags tags) {
        // add pet
        if (tags == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, "L'objet tags est null");
            return Response.status(Response.Status.BAD_REQUEST).entity(json.toString()).build();
        }
        dao.save(tags);
        JsonObject json = new JsonObject();
        json.addProperty(Constants.MESSAGE, "Le tag a été ajouté avec succès");
        return Response.status(Response.Status.CREATED).entity(json.toString()).build();

    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateTag(
            @Parameter(description = "ID of the utilisateur to be updated", required = true) @PathParam("id") Long tagId,
            @Parameter(description = "Utilisateur object that needs to be updated", required = true) Tags updatedTag) {
        Tags existingTag = dao.findOne(tagId);
        if (existingTag == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.TAGS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();        }

        // Update existing fields with new values only if they are non-null in the object being updated
        if (updatedTag.getName() != null) {
            existingTag.setName(updatedTag.getName());
        }
        if (updatedTag.getDescription() != null) {
            existingTag.setDescription(updatedTag.getDescription());
        }

        // Update the tag
        dao.update(existingTag);

        // Return the tag update
        return Response.ok().entity(existingTag).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTags(
            @Parameter(description = "ID of the tag to be deleted", required = true) @PathParam("id") Long tagId) {
        Tags existingTag = dao.findOne(tagId);
        if (existingTag == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.TAGS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }

        dao.delete(existingTag); // Delete tag
        JsonObject json = new JsonObject();
        json.addProperty(Constants.MESSAGE, "Le tag a ete supprimé avec succès");
        return Response.ok().entity(json.toString()).build();
    }

}
