package ar.edu.utn.dds.k3003.tests.viandas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.TemperaturaDTO;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.tests.TestTP;
import java.time.LocalDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ViandasTest implements TestTP<FachadaViandas> {

  private static final Long COLABORADOR_ID = 14L;
  private static final Integer HELADERA_ID = 144;

  FachadaViandas instancia;
  ViandaDTO vianda;
  final LocalDateTime now = LocalDateTime.now();
  @Mock FachadaHeladeras fachadaHeladeras;

  @SneakyThrows
  @BeforeEach
  void setUp() {
    instancia = this.instance();
    instancia.setHeladerasProxy(fachadaHeladeras);
    vianda = new ViandaDTO("unQr", now, EstadoViandaEnum.PREPARADA, COLABORADOR_ID, HELADERA_ID);
  }

  @Test
  @DisplayName("Agregar vianda a colaborador")
  void testAgregarViandaAColaborador() {

    var viandaAgregada = instancia.agregar(vianda);
    assertNotNull(
        viandaAgregada.getId(), "Cuando se agrega una vianda a la misma se le debe asignar un id.");
    assertEquals(
        1,
        instancia.viandasDeColaborador(COLABORADOR_ID, now.getMonthValue(), now.getYear()).size());

    instancia.agregar(
        new ViandaDTO("otroQr", now, EstadoViandaEnum.RETIRADA, COLABORADOR_ID, HELADERA_ID));
    assertEquals(
        1,
        instancia.viandasDeColaborador(COLABORADOR_ID, now.getMonthValue(), now.getYear()).size());

    var viandaEncontrada = instancia.buscarXQR(vianda.getCodigoQR());

    assertEquals(
        viandaAgregada.getColaboradorId(),
        viandaEncontrada.getColaboradorId(),
        "Al buscarXQR no se retorna la vianda correcta.");
  }

  @Test
  @DisplayName("Cambiar y obtener estado y heladera de vianda")
  void testActualizarEstadoVianda() {
    instancia.agregar(vianda);

    instancia.modificarEstado(vianda.getCodigoQR(), EstadoViandaEnum.EN_TRASLADO);

    EstadoViandaEnum estado = instancia.buscarXQR(vianda.getCodigoQR()).getEstado();
    assertEquals(
        EstadoViandaEnum.EN_TRASLADO,
        estado,
        "Se cambió el estado de una vianda pero no parece haberse guardado");

    int heladeraDestino = 4;
    instancia.modificarHeladera(vianda.getCodigoQR(), heladeraDestino);

    assertEquals(
        heladeraDestino,
        instancia.buscarXQR(vianda.getCodigoQR()).getHeladeraId(),
        "No funcionó cambiar la vianda de heladera");
  }

  @Test
  @DisplayName("Ver si la vianda venció")
  void testEvaluarVencimiento() {

    var heladeraId = 14;
    instancia.setHeladerasProxy(fachadaHeladeras);
    ViandaDTO viandaAgregada = instancia.agregar(vianda);

    when(fachadaHeladeras.obtenerTemperaturas(heladeraId))
        .thenReturn(
            List.of(
                new TemperaturaDTO(5, heladeraId, now), new TemperaturaDTO(-10, heladeraId, now)));

    assertTrue(
        instancia.evaluarVencimiento(viandaAgregada.getCodigoQR()),
        "La heladera tiene una temperatura mayor a 5 grados, asi que deberia considerar a la vianda"
            + " como vencida");
  }

  @Override
  public String paquete() {
    return PAQUETE_BASE + "tests.viandas";
  }

  @Override
  public Class<FachadaViandas> clase() {
    return FachadaViandas.class;
  }
}
