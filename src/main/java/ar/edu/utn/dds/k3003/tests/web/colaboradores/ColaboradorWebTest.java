package ar.edu.utn.dds.k3003.tests.web.colaboradores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import ar.edu.utn.dds.k3003.Evaluador;
import ar.edu.utn.dds.k3003.EvaluadorAPI;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class ColaboradorWebTest {

  @Test
  @SneakyThrows
  void testAPIColaborador() {
    var mapper = Evaluador.createObjectMapper();
    var client = HttpClient.newHttpClient();

    var donadorDTO = new ColaboradorDTO("pepe", List.of(FormaDeColaborarEnum.DONADOR));
    var request =
        createRequest("/colaboradores")
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(donadorDTO)))
            .build();
    var send = client.send(request, HttpResponse.BodyHandlers.ofString());
    if ((200 > send.statusCode()) || (send.statusCode() > 300)) {
      System.err.println(String.format("Error %s : %s ", send.statusCode(), send.body()));
      fail();
    }
    var donador = mapper.readValue(send.body(), ColaboradorDTO.class);

    // ----------------------------------------

    var request2 = createRequest("/colaboradores/" + donador.getId().toString()).GET().build();
    var send2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
    if ((200 > send2.statusCode()) || (send2.statusCode() > 300)) {
      System.err.println(String.format("Error %s : %s ", send2.statusCode(), send2.body()));
      fail();
    }
    var donador2 = mapper.readValue(send2.body(), ColaboradorDTO.class);
    assertEquals(
        donador,
        donador2,
        "El colaborador creada con el POST no es igual al recuperado con el GET");
  }

  private HttpRequest.Builder createRequest(String path) throws URISyntaxException {
    var baseUrl = EvaluadorAPI.BASE_URL;
    String urlCompleta = baseUrl + path;
    System.err.println("Creando request a: " + urlCompleta);
    return HttpRequest.newBuilder().uri(new URI(urlCompleta));
  }
}
