package no.jansoren.codegen;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/something")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    @PUT
    @Path("/update/{id}")
    public String updateSomething2(@PathParam("id") String id, Something something) {
        return id;
    }
}
