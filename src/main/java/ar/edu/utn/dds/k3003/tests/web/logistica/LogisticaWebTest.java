package ar.edu.utn.dds.k3003.tests.web.logistica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import ar.edu.utn.dds.k3003.Evaluador;
import ar.edu.utn.dds.k3003.EvaluadorAPI;
import ar.edu.utn.dds.k3003.facades.dtos.RutaDTO;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.webmocks.ViandaTestServer;
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
    ViandaTestServer.main(new String[0]);
    Thread.sleep(2000); // Espera a que levante el server de pruebas

    var rutaDTO = new RutaDTO(14L, 1, 2);
    var request4 =
        createRequest("/rutas")
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(rutaDTO)))
            .build();
    var send4 = client.send(request4, HttpResponse.BodyHandlers.ofString());
    if ((200 > send4.statusCode()) || (send4.statusCode() > 300)) {
      System.err.println(String.format("Error %s : %s ", send4.statusCode(), send4.body()));
      fail();
    }
    mapper.readValue(send4.body(), RutaDTO.class);

    // -----------------------------------------------------

    var trasladoDTO = new TrasladoDTO("unQRQueExiste", 1, 2);
    var request =
        createRequest("/traslados")
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(trasladoDTO)))
            .build();
    var send = client.send(request, HttpResponse.BodyHandlers.ofString());
    if ((200 > send.statusCode()) || (send.statusCode() > 300)) {
      System.err.println(String.format("Error %s : %s ", send.statusCode(), send.body()));
      fail();
    }
    var traslado = mapper.readValue(send.body(), TrasladoDTO.class);

    // ----------------------------------------

    var request2 = createRequest("/traslados/" + traslado.getId().toString()).GET().build();
    var send2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
    if ((200 > send2.statusCode()) || (send2.statusCode() > 300)) {
      System.err.println(String.format("Error %s : %s ", send2.statusCode(), send2.body()));
      fail();
    }
    var traslado2 = mapper.readValue(send2.body(), TrasladoDTO.class);
    assertEquals(
        traslado, traslado2, "El traslado creada con el POST no es igual al recuperado con el GET");
    ViandaTestServer.app.stop();
  }

  private HttpRequest.Builder createRequest(String path) throws URISyntaxException {
    var baseUrl = EvaluadorAPI.BASE_URL;
    String urlCompleta = baseUrl + path;
    System.err.println("Creando request a: " + urlCompleta);
    return HttpRequest.newBuilder().uri(new URI(urlCompleta));
  }
}
