package no.jansoren.codegen;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/something")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    @GET
    @Path("/get")
    public Something getSomething() {
        return new Something();
    }

    @POST
    @Path("/add")
    public Something addSomething(Something something) {
        return something;
    }

    @PUT
    @Path("/put")
    public Something putSomething(Something something) {
        return something;
    }

    @DELETE
    @Path("/delete")
    public void deleteSomething(int id) {

    }

    @PUT
    @Path("/put/{id}")
    public String putSomething2(@PathParam("id") String id, Something something) {
        return id;
    }

    @PUT
    @Path("/put/{id}")
    public String putSomething3(@PathParam("id") String id, int count) {
        return id;
    }
}
