package ar.edu.utn.dds.k3003.tests.web.logistica;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.dds.k3003.Evaluador;
import ar.edu.utn.dds.k3003.EvaluadorAPI;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class LogisticaWebTest {

  @Test
  @SneakyThrows
  void testAPILogistica() {
    var mapper = Evaluador.createObjectMapper();
    var client = HttpClient.newHttpClient();

    var trasladoDTO = new TrasladoDTO("123", 1, 2);
    var request =
        createRequest("/traslados")
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(trasladoDTO)))
            .build();
    var send = client.send(request, HttpResponse.BodyHandlers.ofString());
    var traslado = mapper.readValue(send.body(), TrasladoDTO.class);

    // ----------------------------------------

    var request2 = createRequest("/traslados/" + traslado.getId().toString()).GET().build();
    var send2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
    var traslado2 = mapper.readValue(send2.body(), TrasladoDTO.class);
    assertEquals(
        traslado, traslado2, "El traslado creada con el POST no es igual al recuperado con el GET");
  }

  private HttpRequest.Builder createRequest(String path) throws URISyntaxException {
    var baseUrl = EvaluadorAPI.BASE_URL;
    return HttpRequest.newBuilder().uri(new URI(baseUrl + path));
  }
}
