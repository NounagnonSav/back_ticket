package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.CommentsDao;
import fr.istic.taa.jaxrs.domaine.Comments;
import fr.istic.taa.jaxrs.domaine.Utilisateur;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/comments")
@Produces({"application/json", "application/xml"})
public class CommentsResource {

    CommentsDao dao = new CommentsDao();

    @GET
    @Path("/{commentId}")
    public Response getCommentById(@PathParam("commentId") Long commentId)  {
        // return comment
        Comments existingComments = dao.findOne(commentId);
        if (existingComments == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Commentaire non trouvé").build();
        }
        return Response.ok().entity(existingComments).build();
    }

    @GET
    @Path("/")
    public Response getComment()  {
        List<Comments> comments = dao.findAll();
        if (comments == null || comments.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Aucun commentaire trouvé").build();
        }
        else {
            return Response.ok().entity(comments).build();
        }
    }


    @POST
    @Consumes("application/json")
    public Response addComment(
            @Parameter(description = "Comment object that needs to be added to the store", required = true) Comments comments) {
        // add comment
        if (comments == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("L'objet comments est null").build();
        }
        dao.save(comments);

        return Response.status(Response.Status.CREATED).entity("commentaire ajouté avec succès").build();
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
            return Response.status(Response.Status.NOT_FOUND).entity("commentaire non trouvé").build();
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
            return Response.status(Response.Status.NOT_FOUND).entity("Commentaire non trouvé").build();
        }

        dao.delete(existingComments); // Delete comment

        return Response.ok().entity("Le commentaire est supprimé avec succès").build();
    }

}
