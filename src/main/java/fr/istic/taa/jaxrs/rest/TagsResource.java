package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domaine.Tags;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("Tags")
@Produces({"application/json", "application/xml"})
public class TagsResource {
    @GET
    @Path("/{tagId}")
    public Tags getTagsById(@PathParam("tagId") Long tagId)  {
        // return pet
        return new Tags();
    }

    @GET
    @Path("/")
    public Tags getTagId(Long projectId)  {
        return new Tags();
    }


    @POST
    @Consumes("application/json")
    public Response addTag(
            @Parameter(description = "Tag object that needs to be added to the store", required = true) Tags tags) {
        // add pet
        return Response.ok().entity("SUCCESS").build();
    }

}
