package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.TagsDao;
import fr.istic.taa.jaxrs.domaine.Tags;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/tags")
@Produces({"application/json", "application/xml"})
public class TagsResource {

    TagsDao dao = new TagsDao();
    @GET
    @Path("/{tagId}")
    public Response getTagsById(@PathParam("tagId") Long tagId)  {
        // return Tag
        Tags existingTag = dao.findOne(tagId);
        if (existingTag == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Tag non trouvé").build();
        }
        return Response.ok().entity(existingTag).build();
    }

    @GET
    @Path("/")
    public Response getTags()  {
        List<Tags> tags = dao.findAll();
        if (tags == null || tags.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Aucun tags trouvé").build();
        } else {
            return Response.ok().entity(tags).build();
        }
    }


    @POST
    @Consumes("application/json")
    public Response addTag(
            @Parameter(description = "Tag object that needs to be added to the store", required = true) Tags tags) {
        // add pet
        if (tags == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("L'objet tags est null").build();
        }
        dao.save(tags);

        return Response.status(Response.Status.CREATED).entity("tag ajouté avec succès").build();

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
            return Response.status(Response.Status.NOT_FOUND).entity("Tag non trouvé").build();
        }

        // Update existing fields with new values only if they are non-null in the object being updated
        if (updatedTag.getName() != null) {
            existingTag.setName(updatedTag.getName());
        }
        if (updatedTag.getDescription() != null) {
            existingTag.setName(updatedTag.getName());
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
            return Response.status(Response.Status.NOT_FOUND).entity("Tag non trouvé").build();
        }

        dao.delete(existingTag); // Delete tag

        return Response.ok().entity("Le tag a ete supprimé avec succès").build();
    }

}
