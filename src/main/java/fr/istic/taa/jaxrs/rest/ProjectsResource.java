package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domaine.Projects;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("project")
@Produces({"application/json", "application/xml"})
public class ProjectsResource {

  @GET
  @Path("/{projectId}")
  public Projects getProjectsById(@PathParam("projectId") Long projectId)  {
      // return pet
      return new Projects();
  }

  @GET
  @Path("/")
  public Projects getProject(Long projectId)  {
      return new Projects();
  }

  
  @POST
  @Consumes("application/json")
  public Response addProject(
      @Parameter(description = "Project object that needs to be added to the store", required = true) Projects projects) {
    // add pet
    return Response.ok().entity("SUCCESS").build();
  }
}