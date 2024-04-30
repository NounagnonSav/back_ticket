package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.ProjectsDao;
import fr.istic.taa.jaxrs.domaine.Projects;
import fr.istic.taa.jaxrs.utils.Constants;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



@Path("/api/project")
@Consumes("application/json")
@Produces("application/json")
public class ProjectsResource {

  ProjectsDao dao = new ProjectsDao();

  //endpoint to display projects
  @GET
  @Path("/{projectId}")
  @Consumes("application/json")
  @Produces("application/json")
  public Response getProjectsById(@PathParam("projectId") Long projectId)  {
    // return user
    Projects existingProjects = dao.findOne(projectId);
    if (existingProjects == null) {
      JsonObject json = new JsonObject();
      json.addProperty(Constants.ERROR, Constants.PROJECT_NOT_FOUND);
      return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
    }

    return Response.ok().entity(existingProjects).build();
  }

  @GET
  @Path("/")
  @Consumes("application/json")
  @Produces("application/json")
  public Response getProject()  {
    List<Projects> projects = dao.findAll();
    if (projects == null || projects.isEmpty()) {
      JsonObject json = new JsonObject();
      json.addProperty(Constants.ERROR, Constants.PROJECT_NOT_FOUND);
      return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
    } else {
      JsonArray jsonArray = new Gson().toJsonTree(projects).getAsJsonArray();
      // Créer l'objet JSON principal
      JsonObject json = new JsonObject();
      json.add("results", jsonArray);
      json.addProperty("total_results", jsonArray.size());

      // Convertir l'objet JSON en une chaîne de caractères
      String jsonString = json.toString();

      return Response.ok().entity(jsonString).build();
    }
  }


  //endpoint to create a new project
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  public Response addProject(
          @Parameter(description = "Project object that needs to be added to the store", required = true) Projects projects)
        {
          // add project
          if (projects == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, "L'objet project est null, veuillez renseigné les champs");
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
          }
          dao.save(projects);
          JsonObject json = new JsonObject();
          json.addProperty(Constants.MESSAGE, "Le projet a été ajouté avec succès");
          return Response.status(Response.Status.CREATED).entity(json.toString()).build();
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
      JsonObject json = new JsonObject();
      json.addProperty(Constants.ERROR, Constants.PROJECT_NOT_FOUND);
      return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
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
  @Consumes("application/json")
  @Produces("application/json")
  public Response deleteProject(
          @Parameter(description = "ID of the project to be deleted", required = true) @PathParam("id") Long projectId) {
    Projects existingProjects = dao.findOne(projectId);
    if (existingProjects == null) {
      JsonObject json = new JsonObject();
      json.addProperty(Constants.ERROR, Constants.PROJECT_NOT_FOUND);
      return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
    }

    dao.delete(existingProjects); // delete the project in the BD
    JsonObject json = new JsonObject();
    json.addProperty(Constants.MESSAGE, "Le projet a été supprimé avec succès");
    return Response.ok().entity(json.toString()).build();
  }
}