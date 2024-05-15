package ar.edu.utn.dds.k3003.tests.web.viandas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.dds.k3003.Evaluador;
import ar.edu.utn.dds.k3003.EvaluadorAPI;
import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class ViandaWebTest {

  @Test
  @SneakyThrows
  void testAPIVianda() {
    var mapper = Evaluador.createObjectMapper();
    var client = HttpClient.newHttpClient();

    var value = new ViandaDTO("codigoQR", LocalDateTime.now(), EstadoViandaEnum.PREPARADA, 1L, 2);
    var request =
        createRequest("/viandas")
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(value)))
            .build();
    var send = client.send(request, HttpResponse.BodyHandlers.ofString());
    var vianda = mapper.readValue(send.body(), ViandaDTO.class);

    // ----------------------------------------

    var request2 = createRequest("/viandas/" + vianda.getCodigoQR()).GET().build();
    var send2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
    var vianda2 = mapper.readValue(send2.body(), ViandaDTO.class);
    assertEquals(
        vianda, vianda2, "La vianda creada con el POST no es igual a la recuperada con el GET");

    // -----------------------------------------
    var request3 =
        createRequest(
                String.format(
                    "/viandas/search/findByColaboradorIdAndAnioAndMes?colaboradorId=%s&anio=%s&mes=%s",
                    1L, LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue()))
            .GET()
            .build();
    var send3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
    var vianda3 = mapper.readValue(send3.body(), ViandaDTO.class);
    assertEquals(
        vianda,
        vianda3,
        "La vianda creada con el POST no es igual a la recuperada con el findByColaborador");
  }

  private HttpRequest.Builder createRequest(String path) throws URISyntaxException {
    var baseUrl = EvaluadorAPI.BASE_URL;
    return HttpRequest.newBuilder().uri(new URI(baseUrl + path));
  }
}
