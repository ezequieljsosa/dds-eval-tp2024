package ar.edu.utn.dds.k3003.webtest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class UnWebTest {

  @Test
  @SneakyThrows
  void testAgregarColaborador() {
    HttpClient client = HttpClient.newHttpClient();
    var request = HttpRequest.newBuilder().uri(new URI("http://localhost:8081/test")).GET().build();
    HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(send.body());
  }
}
