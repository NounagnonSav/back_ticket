package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.TicketsDao;
import fr.istic.taa.jaxrs.domaine.Tickets;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/tickets")
@Produces({"application/json", "application/xml"})
public class TicketsResource {

    TicketsDao dao = new TicketsDao();
    @GET
    @Path("/{ticketId}")
    public Response getTicketById(@PathParam("ticketId") Long ticketId)  {
        // return ticket
        Tickets existingTicket = dao.findOne(ticketId);
        if (existingTicket == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Ticket non trouvé").build();
        }
        return Response.ok().entity(existingTicket).build();
    }

    @GET
    @Path("/")
    public Response getTicket()  {
        List<Tickets> tickets = dao.findAll();
        if (tickets == null || tickets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Aucun ticket trouvé").build();
        } else {
            return Response.ok().entity(tickets).build();
        }
    }

    @POST
    @Consumes("application/json")
    public Response addTicket(
            @Parameter(description = "Ticket object that needs to be added to the store", required = true) Tickets tickets) {
        // add ticket
        if (tickets == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("L'objet ticket est null").build();
        }
        dao.save(tickets);

        return Response.status(Response.Status.CREATED).entity("ticket ajouté avec succès").build();
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
            return Response.status(Response.Status.NOT_FOUND).entity("Aucun ticket non trouvé").build();
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
            return Response.status(Response.Status.NOT_FOUND).entity("Aucun ticket trouvé").build();
        }

        dao.delete(existingTicket); // Delete the ticket

        return Response.ok().entity("Ticket supprimé avec succès").build();
    }
}
