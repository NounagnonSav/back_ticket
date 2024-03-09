package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domaine.Comments;
import fr.istic.taa.jaxrs.domaine.Projects;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("comment")
@Produces({"application/json", "application/xml"})
public class CommentsResource {

    @GET
    @Path("/{commentId}")
    public Comments getCommentById(@PathParam("commentId") Long commentId)  {
        // return pet
        return new Comments();
    }

    @GET
    @Path("/")
    public Comments getComment(Long commentId)  {
        return new Comments();
    }


    @POST
    @Consumes("application/json")
    public Response addComment(
            @Parameter(description = "Comment object that needs to be added to the store", required = true) Comments comments) {
        // add pet
        return Response.ok().entity("SUCCESS").build();
    }

}
