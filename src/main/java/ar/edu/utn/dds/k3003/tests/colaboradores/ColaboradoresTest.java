package ar.edu.utn.dds.k3003.tests.colaboradores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import ar.edu.utn.dds.k3003.facades.FachadaColaboradores;
import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.*;
import ar.edu.utn.dds.k3003.tests.TestTP;
import java.time.LocalDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ColaboradoresTest implements TestTP<FachadaColaboradores> {

  FachadaColaboradores instancia;

  final String nombre1 = "pepe";
  ColaboradorDTO colaborador1;
  @Mock FachadaLogistica logistica;
  @Mock FachadaViandas viandas;

  @SneakyThrows
  @BeforeEach
  void setUp() {
    instancia = this.instance();
    colaborador1 = new ColaboradorDTO(this.nombre1, List.of(FormaDeColaborarEnum.DONADOR));
    instancia.setLogisticaProxy(logistica);
    instancia.setViandasProxy(viandas);
  }

  @Test
  @DisplayName("Agregar y buscar colaborador")
  void testAgregarColaborador() {

    var colaboradorRta = instancia.agregar(colaborador1);
    assertNotNull(
        colaboradorRta.getId(),
        "FachadaColaboradores#agregar debe retornar un ColaboradorDTO con un id inicializado.");
    instancia.agregar(new ColaboradorDTO("juan", List.of(FormaDeColaborarEnum.DONADOR)));

    ColaboradorDTO colaborador3 = instancia.buscarXId(colaborador1.getId());
    assertEquals(
        this.nombre1,
        colaborador3.getNombre(),
        "No se esta recuperando el nombre del colaborador correctamente.");
  }

  @Test
  @DisplayName("Modificando forma de colaborar")
  void testModificarFormaDeColaborar() {
    var colaboradorRta = instancia.agregar(colaborador1);
    ColaboradorDTO colaboradorRta2 =
        instancia.modificar(colaboradorRta.getId(), List.of(FormaDeColaborarEnum.TRANSPORTADOR));
    assertEquals(
        FormaDeColaborarEnum.TRANSPORTADOR,
        colaboradorRta2.getFormas().get(0),
        "No se actualizó la forma de colaborar.");
    var colaborador3 = instancia.buscarXId(colaboradorRta.getId());

    assertEquals(
        FormaDeColaborarEnum.TRANSPORTADOR,
        colaborador3.getFormas().get(0),
        "No se esta guardando la forma de colaborar.");
  }

  @Test
  @DisplayName("Calculando Puntos")
  void testPuntos() {
    var colaboradorRta = instancia.agregar(colaborador1);

    instancia.actualizarPesosPuntos(0.5, 1.0, 1.5, 2.0, 5.0);

    when(logistica.trasladosDeColaborador(colaboradorRta.getId(), 1, 2024)).thenReturn(List.of());
    when(viandas.viandasDeColaborador(colaboradorRta.getId(), 1, 2024)).thenReturn(List.of());
    var puntos = instancia.puntos(colaboradorRta.getId());
    assertEquals(
        0, puntos, "Si logistica y viandas no responden nada el puntaje deberia ser cero.");

    var trasladoDTO = new TrasladoDTO("x", 18, 19);
    when(logistica.trasladosDeColaborador(colaboradorRta.getId(), 1, 2024))
        .thenReturn(List.of(trasladoDTO));

    var viandaDTO =
        new ViandaDTO(
            "y", LocalDateTime.now(), EstadoViandaEnum.EN_TRASLADO, colaboradorRta.getId(), 20);
    when(viandas.viandasDeColaborador(colaboradorRta.getId(), 1, 2024))
        .thenReturn(List.of(viandaDTO));
    puntos = instancia.puntos(colaboradorRta.getId());
    assertEquals(1.5, puntos, "Si hay un traslado y una vianda el puntaje deberia ser 1.5.");

    when(logistica.trasladosDeColaborador(colaboradorRta.getId(), 1, 2024)).thenReturn(List.of());
    when(viandas.viandasDeColaborador(colaboradorRta.getId(), 1, 2024))
        .thenReturn(List.of(viandaDTO));
    puntos = instancia.puntos(colaboradorRta.getId());
    assertEquals(1.0, puntos, "Si hay una vianda y nada mas el puntaje deberia ser 1.");
  }

  @Override
  public String paquete() {
    return PAQUETE_BASE + "colaboradores";
  }

  @Override
  public Class<FachadaColaboradores> clase() {
    return FachadaColaboradores.class;
  }
}
