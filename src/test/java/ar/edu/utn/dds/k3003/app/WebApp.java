package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;

public class WebApp {

  public static Javalin app;
  private static List<ViandaDTO> viandas = new ArrayList<>();

  public static void main(String[] args) {
    var env = System.getenv();

    var port = Integer.parseInt(env.getOrDefault("PORT", "8080"));

    app = Javalin.create().start(port);

    // Viandas
    app.post("/viandas", WebApp::agregarVianda);
    app.get("/viandas/{qr}", WebApp::obtenerVianda);
    app.get(
        "/viandas/search/findByColaboradorIdAndAnioAndMes", WebApp::buscarViandasPorColaborador);
  }

  private static void buscarViandasPorColaborador(Context context) {
    context.json(
        viandas.stream()
            .filter(
                x -> x.getColaboradorId().toString().equals(context.queryParam("colaboradorId")))
            .findFirst()
            .get());
  }

  private static void obtenerVianda(Context context) {
    ViandaDTO vianda =
        viandas.stream()
            .filter(x -> x.getCodigoQR().equals(context.pathParam("qr")))
            .findFirst()
            .get();
    // vianda.setId(18L);
    context.json(vianda);
  }

  private static void agregarVianda(Context context) {
    ViandaDTO viandaDTO = context.bodyAsClass(ViandaDTO.class);
    viandaDTO.setId(14L);
    viandas.add(viandaDTO);
    context.json(viandaDTO);
  }
}
