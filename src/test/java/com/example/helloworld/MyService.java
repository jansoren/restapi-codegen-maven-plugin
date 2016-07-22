package com.example.helloworld;

import java.lang.Void;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import no.jansoren.codegen.Something;

public class MyService {
  private WebTarget target;

  public MyService() {
    Client client = ClientBuilder.newClient();
    target = client.target("https://localhost:1234").path("something");
  }

  public Something addSomething(Something dataToPost) {
    Response response = target.path("add").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(dataToPost, MediaType.APPLICATION_JSON_TYPE));
    return response.readEntity(Something);
  }

  public Void deleteSomething() {
    Response response = target.path("delete").request(MediaType.APPLICATION_JSON_TYPE).delete();
    return response.readEntity(Void);
  }

  public Something getSomething() {
    Response response = target.path("get").request(MediaType.APPLICATION_JSON_TYPE).get();
    return response.readEntity(Something);
  }

  public Something putSomething(Something dataToPut) {
    Response response = target.path("put").request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(dataToPut, MediaType.APPLICATION_JSON_TYPE));
    return response.readEntity(Something);
  }
}
