package ar.edu.utn.dds.k3003.webmocks;

import ar.edu.utn.dds.k3003.facades.FachadaColaboradores;
import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.EstadoTrasladoEnum;
import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import com.fasterxml.jackson.databind.JsonNode;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.time.LocalDateTime;

public class ViandaTestServer {

  public static Javalin app;
  public static FachadaHeladeras fachadaHeladeras;
  public static FachadaViandas fachadaViandas;
  public static FachadaColaboradores fachadaColaboradores;
  public static FachadaLogistica fachadaLogistica;

  public static void main(String[] args) throws Exception {

    var env = System.getenv();

    var port = Integer.parseInt(env.getOrDefault("PORT", "8081"));

    app = Javalin.create().start(port);

    // test
    app.get("/test", ViandaTestServer::test);

    // Viandas
    app.post("/viandas", ViandaTestServer::agregarVianda);
    app.get("/viandas/{qr}", ViandaTestServer::obtenerVianda);
    app.get(
        "/viandas/search/findByColaboradorIdAndAnioAndMes",
        ViandaTestServer::buscarViandasPorColaborador);
    app.get("/viandas/{qr}/vencida", ViandaTestServer::viandaVencida);
    app.patch("/viandas/{qr}", ViandaTestServer::modificarVianda);

    // Colaboradores
    app.post("/colaboradores", ViandaTestServer::agregarColaborador);
    app.get("/colaboradores/{colId}", ViandaTestServer::obtenerColaborador);
    app.patch("/colaboradores/{colId}", ViandaTestServer::modificarVianda);
    app.get("/colaboradores/{colaboradorId}/puntos", ViandaTestServer::puntosColaborador);
    app.put("/formula", ViandaTestServer::editarFormula);

    // Heladeras
    app.post("/heladeras", ViandaTestServer::agregarHeladera);
    app.get("/heladeras/{heladeraId}", ViandaTestServer::obtenerHeladera);
    app.post("/depositos", ViandaTestServer::depositarEnHeladera);
    app.post("/retiros", ViandaTestServer::retirarDeHeladera);
    app.post("/temperaturas", ViandaTestServer::agregarTemperatura);
    app.get("/heladeras/{heladeraId}/temperaturas", ViandaTestServer::obtenerTemperaturas);

    // Logistica
    app.post("/rutas", ViandaTestServer::agregarRuta);
    app.post("/traslados", ViandaTestServer::agregarTraslado);
    app.get("/traslados/{id}", ViandaTestServer::obtenerTraslado);
    app.patch("/traslados/{id}", ViandaTestServer::modificarTraslado);

    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  app.stop();
                }));
  }

  private static void test(Context context) {
    context.result("Funcionando");
  }

  private static void modificarTraslado(Context context) {

    var trasladoId = Long.parseLong(context.pathParam("id"));

    var statusChange = context.bodyAsClass(JsonNode.class);

    var trasladoDTO =
        new TrasladoDTO(
            "qrVianda",
            EstadoTrasladoEnum.valueOf(statusChange.get("status").asText()),
            null,
            null,
            null);
    trasladoDTO.setId(trasladoId);
    context.json(trasladoDTO);
  }

  private static void obtenerTraslado(Context context) {}

  private static void agregarTraslado(Context context) {}

  private static void agregarRuta(Context context) {}

  private static void obtenerTemperaturas(Context context) {}

  private static void agregarTemperatura(Context context) {}

  private static void retirarDeHeladera(Context context) {}

  private static void depositarEnHeladera(Context context) {}

  private static void obtenerHeladera(Context context) {}

  private static void agregarHeladera(Context context) {}

  private static void editarFormula(Context context) {}

  private static void puntosColaborador(Context context) {}

  private static void obtenerColaborador(Context context) {}

  private static void agregarColaborador(Context context) {}

  private static void modificarVianda(Context context) {

    var qr = context.pathParam("qr");

    var statusChange = context.bodyAsClass(JsonNode.class);

    var vianda =
        new ViandaDTO(
            qr,
            LocalDateTime.now(),
            EstadoViandaEnum.valueOf(statusChange.get("estado").textValue()),
            null,
            null);

    context.json(vianda);
  }

  private static void viandaVencida(Context context) {}

  private static void buscarViandasPorColaborador(Context context) {}

  private static void agregarVianda(Context context) {
    var viandaDTO = context.bodyAsClass(ViandaDTO.class);
    fachadaViandas.agregar(viandaDTO);
    context.status(HttpStatus.CREATED);
  }

  private static void obtenerVianda(Context context) {

    var qr = context.pathParam("qr");
    if (qr.equals("unQRQueExiste")) {
      var viandaDTO1 = new ViandaDTO(qr, LocalDateTime.now(), EstadoViandaEnum.PREPARADA, 2L, 1);
      viandaDTO1.setId(14L);
      context.json(viandaDTO1);
    } else {
      context.result("Vianda no encontrada: " + qr);
      context.status(HttpStatus.NOT_FOUND);
    }
  }
}
