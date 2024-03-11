package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.ProjectsDao;
import fr.istic.taa.jaxrs.domaine.Projects;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/project")
@Produces({"application/json", "application/xml"})
public class ProjectsResource {

  ProjectsDao dao = new ProjectsDao();

  //endpoint to display projects
  @GET
  @Path("/{projectId}")
  public Response getProjectsById(@PathParam("projectId") Long projectId)  {
    // return user
    Projects existingProjects = dao.findOne(projectId);
    if (existingProjects == null) {
      return Response.status(Response.Status.NOT_FOUND).entity("Projet non trouvé").build();
    }
    return Response.ok().entity(existingProjects).build();
  }

  @GET
  @Path("/")
  public Response getProject()  {
    List<Projects> projects = dao.findAll();
    if (projects == null || projects.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).entity("Aucun projet trouvé").build();
    } else {
      return Response.ok().entity(projects).build();
    }
  }


  //endpoint to create a new project
  @POST
  @Consumes("application/json")
  public Response addProject(
          @Parameter(description = "Project object that needs to be added to the store", required = true) Projects projects)
        {
          // add project
          if (projects == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("L'objet project est null").build();
          }
          dao.save(projects);

          return Response.status(Response.Status.CREATED).entity("Le projet a été ajouté avec succès").build();
        }

  // endpoint to update project
  @PUT
  @Path("/{id}")
  @Consumes("application/json")
  @Produces("application/json")
  public Response updateProject(
          @Parameter(description = "ID of the project to be updated", required = true) @PathParam("id") Long projectId,
          @Parameter(description = "Project object that needs to be updated", required = true) Projects updatedProject) {
    Projects existingProjects = dao.findOne(projectId);
    if (existingProjects == null) {
      return Response.status(Response.Status.NOT_FOUND).entity("Projet non trouvé").build();
    }

    // Update the existing field with the new value only if they are non-null in the object being updated
    if (updatedProject.getName() != null) {
      existingProjects.setName(updatedProject.getName());
    }

    // Update the project in the database
    dao.update(existingProjects);

    // Return the updated project
    return Response.ok().entity(existingProjects).build();
  }

  // endpoint to delete a user
  @DELETE
  @Path("/{id}")
  public Response deleteProject(
          @Parameter(description = "ID of the project to be deleted", required = true) @PathParam("id") Long projectId) {
    Projects existingProjects = dao.findOne(projectId);
    if (existingProjects == null) {
      return Response.status(Response.Status.NOT_FOUND).entity("Projet non trouvé").build();
    }

    dao.delete(existingProjects); // delete the project in the BD

    return Response.ok().entity("Le projet a été supprimé avec succès").build();
  }
}