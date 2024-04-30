package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.TicketsDao;
import fr.istic.taa.jaxrs.domaine.Tickets;
import fr.istic.taa.jaxrs.utils.Constants;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

@Path("/api/tickets")
@Produces({"application/json", "application/xml"})
public class TicketsResource {

    TicketsDao dao = new TicketsDao();
    @GET
    @Path("/{ticketId}")
    public Response getTicketById(@PathParam("ticketId") Long ticketId)  {
        // return ticket
        Tickets existingTicket = dao.findOne(ticketId);
        if (existingTicket == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.TICKETS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }
        return Response.ok().entity(existingTicket).build();
    }

    @GET
    @Path("/")
    public Response getTicket()  {
        List<Tickets> tickets = dao.findAll();
        if (tickets == null || tickets.isEmpty()) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.TICKETS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        } else {
            JsonArray jsonArray = new Gson().toJsonTree(tickets).getAsJsonArray();
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
    public Response addTicket(
            @Parameter(description = "Ticket object that needs to be added to the store", required = true) Tickets tickets) {
        // add ticket
        if (tickets == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, "L'objet ticket est null");
            return Response.status(Response.Status.BAD_REQUEST).entity(json.toString()).build();
        }
        dao.save(tickets);

        JsonObject json = new JsonObject();
        json.addProperty(Constants.MESSAGE, "Le ticket a été ajouté avec succès");
        return Response.status(Response.Status.CREATED).entity(json.toString()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateTicket(
            @Parameter(description = "ID of the utilisateur to be updated", required = true) @PathParam("id") Long ticketId,
            @Parameter(description = "Utilisateur object that needs to be updated", required = true) Tickets updateTicket) {
        Tickets existingTicket = dao.findOne(ticketId);
        if (existingTicket == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.TICKETS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }

        // Update existing fields with new values only if they are non-null in the object being updated
        if (updateTicket.getTitle() != null) {
            existingTicket.setTitle(updateTicket.getTitle());
        }
        if (updateTicket.getContent() != null) {
            existingTicket.setContent(updateTicket.getContent());
        }
        if (updateTicket.getAssign_to() != null) {
            existingTicket.setAssign_to(updateTicket.getAssign_to());
        }

        // Update ticket
        dao.update(existingTicket);

        // Return ticket
        return Response.ok().entity(existingTicket).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteTicket(
            @Parameter(description = "ID of the ticket to be deleted", required = true) @PathParam("id") Long ticketId) {
        Tickets existingTicket = dao.findOne(ticketId);
        if (existingTicket == null) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.ERROR, Constants.TICKETS_NOT_FOUND);
            return Response.status(Response.Status.NOT_FOUND).entity(json.toString()).build();
        }

        dao.delete(existingTicket); // Delete the ticket
        JsonObject json = new JsonObject();
        json.addProperty(Constants.MESSAGE, "Ticket supprimé avec succès");
        return Response.ok().entity(json.toString()).build();
    }
}
