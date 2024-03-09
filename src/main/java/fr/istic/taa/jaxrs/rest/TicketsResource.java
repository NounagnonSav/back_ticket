package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domaine.Projects;
import fr.istic.taa.jaxrs.domaine.Tickets;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("tickets")
@Produces({"application/json", "application/xml"})
public class TicketsResource {

    @GET
    @Path("/{ticketId}")
    public Tickets getTicketById(@PathParam("ticketId") Long ticketId)  {
        // return pet
        return new Tickets();
    }

    @GET
    @Path("/")
    public Tickets getTicket(Long ticketId)  {
        return new Tickets();
    }

    @POST
    @Consumes("application/json")
    public Response addTicket(
            @Parameter(description = "Ticket object that needs to be added to the store", required = true) Tickets tickets) {
        // add pet
        return Response.ok().entity("SUCCESS").build();
    }

}
