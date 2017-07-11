package com.example.helloworld;

import no.jansoren.codegen.Something;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MyService {
  private WebTarget target;

  public MyService() {
    Client client = ClientBuilder.newClient();
    target = client.target("https://localhost:1234").path("something");
  }

  public Something addSomething(Something something) {
    Response response = target.path("add").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(something, MediaType.APPLICATION_JSON_TYPE));
    return response.readEntity(Something.class);
  }

  public Void deleteSomething() {
    Response response = target.path("delete").request(MediaType.APPLICATION_JSON_TYPE).delete();
    return response.readEntity(Void.class);
  }

  public Something getSomething() {
    Response response = target.path("get").request(MediaType.APPLICATION_JSON_TYPE).get();
    return response.readEntity(Something.class);
  }

  public Something putSomething(Something something) {
    Response response = target.path("put").request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(something, MediaType.APPLICATION_JSON_TYPE));
    return response.readEntity(Something.class);
  }

  public String putSomething2(String id, Something something) {
    Response response = target.path("put/" + id + "").request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(something, MediaType.APPLICATION_JSON_TYPE));
    return response.readEntity(String.class);
  }
}
