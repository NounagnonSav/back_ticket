package fr.istic.taa.jaxrs.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.istic.taa.jaxrs.dao.CommentsDao;
import fr.istic.taa.jaxrs.domaine.Comments;
import fr.istic.taa.jaxrs.domaine.Utilisateur;
import fr.istic.taa.jaxrs.utils.Constants;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/comments")
@Produces({"application/json", "application/xml"})
public class CommentsResource {

    CommentsDao dao = new CommentsDao();

    @GET
    @Path("/{commentId}")
    public Response getCommentById(@PathParam("commentId") Long commentId)  {
        // return comment
        Comments existingComments = dao.findOne(commentId);
        if (existingComments == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.COMMENTS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }
        return Response.ok().entity(existingComments).build();
    }

    @GET
    @Path("/")
    public Response getComment()  {
        List<Comments> comments = dao.findAll();
        if (comments == null || comments.isEmpty()) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.COMMENTS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }
        else {
            JsonArray jsonArray = new Gson().toJsonTree(comments).getAsJsonArray();
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
    public Response addComment(
            @Parameter(description = "Comment object that needs to be added to the store", required = true) Comments comments) {
        // add comment
        if (comments == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, "L'objet comments est null, veuillez renseigné les champs");
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();        }
        dao.save(comments);
        JsonObject json = new JsonObject();
        json.addProperty(Constants.MESSAGE, "commentaire ajouté avec succès");
        return Response.status(Response.Status.CREATED).entity(json.toString()).build();
    }


    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateComment(
            @Parameter(description = "ID of the comment to be updated", required = true) @PathParam("id") Long commentId,
            @Parameter(description = "comment object that needs to be updated", required = true) Comments updateComment) {
        Comments existingComments = dao.findOne(commentId);
        if (existingComments == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.COMMENTS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }

        // Update existing fields with new values only if they are non-null in the object being updated
        if (updateComment.getContent() != null) {
            existingComments.setContent(updateComment.getContent());
        }
        // Update comment
        dao.update(existingComments);

        // Return comment
        return Response.ok().entity(existingComments).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteComment(
            @Parameter(description = "ID of the comments to be deleted", required = true) @PathParam("id") Long commentId) {
        Comments existingComments = dao.findOne(commentId);
        if (existingComments == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.COMMENTS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }

        dao.delete(existingComments); // Delete comment

        JsonObject json = new JsonObject();
        json.addProperty(Constants.MESSAGE, "Le commentaire est supprimé avec succès");
        return Response.ok().entity(json.toString()).build();
    }

}
