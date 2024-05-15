package ar.edu.utn.dds.k3003.tests.web.heladeras;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.dds.k3003.Evaluador;
import ar.edu.utn.dds.k3003.EvaluadorAPI;
import ar.edu.utn.dds.k3003.facades.dtos.HeladeraDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class HeladeraWebTest {

  @Test
  @SneakyThrows
  void testAPIHeladera() {
    var mapper = Evaluador.createObjectMapper();
    var client = HttpClient.newHttpClient();

    var heladeraDTO = new HeladeraDTO("unaHeladera");
    var request =
        createRequest("/heladeras")
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(heladeraDTO)))
            .build();
    var send = client.send(request, HttpResponse.BodyHandlers.ofString());
    var heladera = mapper.readValue(send.body(), HeladeraDTO.class);

    // ----------------------------------------

    var request2 = createRequest("/heladeras/" + heladera.getId().toString()).GET().build();
    var send2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
    var heladera2 = mapper.readValue(send2.body(), HeladeraDTO.class);
    assertEquals(
        heladera,
        heladera2,
        "La heladera creada con el POST no es igual a la recuperada con el GET");
  }

  private HttpRequest.Builder createRequest(String path) throws URISyntaxException {
    var baseUrl = EvaluadorAPI.BASE_URL;
    return HttpRequest.newBuilder().uri(new URI(baseUrl + path));
  }
}
