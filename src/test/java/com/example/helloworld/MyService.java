package com.example.helloworld;

import java.lang.Void;
import no.bouvet.jsonclient.JsonClient;
import no.jansoren.codegen.Something;

public class MyService {
  private final JsonClient jsonClient = new JsonClient();

  public Something addSomething(Something dataToPost) {
    return jsonClient.http().post("http://localhost:8080/something/add", dataToPost).object(Something.class);
  }

  public Void deleteSomething() {
    return jsonClient.http().delete("http://localhost:8080/something/delete").object(Void.class);
  }

  public Something getSomething() {
    return jsonClient.http().get("http://localhost:8080/something/get").object(Something.class);
  }

  public Something putSomething(Something dataToPost) {
    return jsonClient.http().put("http://localhost:8080/something/put", dataToPost).object(Something.class);
  }
}
