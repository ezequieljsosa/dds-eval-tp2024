package ar.edu.utn.dds.k3003.tests.heladeras;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.*;
import ar.edu.utn.dds.k3003.tests.TestTP;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HeladerasTest implements TestTP<FachadaHeladeras> {

  private static final Integer HELADERA_ID = 18;

  FachadaHeladeras instancia;

  final String qr1 = "123";

  @Mock FachadaViandas viandas;

  @SneakyThrows
  @BeforeEach
  void setUp() {
    instancia = this.instance();
    instancia.agregar(new HeladeraDTO(HELADERA_ID, "Una Heladera"));
    instancia.setViandasProxy(viandas);
  }

  @Test
  @DisplayName("Depositar vianda")
  void testDepositarVianda() {

    long colaboradorId = 14L;

    when(viandas.buscarXQR(qr1))
        .thenReturn(
            new ViandaDTO(
                qr1, LocalDateTime.now(), EstadoViandaEnum.PREPARADA, colaboradorId, HELADERA_ID));
    String qr2 = "321";
    when(viandas.buscarXQR(qr2))
        .thenReturn(
            new ViandaDTO(
                qr2, LocalDateTime.now(), EstadoViandaEnum.PREPARADA, colaboradorId, HELADERA_ID));
    instancia.depositar(HELADERA_ID, qr1);
    instancia.depositar(HELADERA_ID, qr2);

    verify(viandas, description("no se marco la vianda como depositada"))
        .modificarEstado(qr1, EstadoViandaEnum.DEPOSITADA);

    assertEquals(
        2, instancia.cantidadViandas(HELADERA_ID), "Las viandas no se agregaron correctamente");
  }

  @Test
  @DisplayName("Retirar vianda")
  void testRetirarVianda() {
    when(viandas.buscarXQR(qr1))
        .thenReturn(
            new ViandaDTO(qr1, LocalDateTime.now(), EstadoViandaEnum.PREPARADA, 15L, HELADERA_ID));
    instancia.depositar(HELADERA_ID, qr1);

    instancia.retirar(new RetiroDTO(qr1, "14L", HELADERA_ID));

    verify(viandas, description("no se marco la vianda como retirada"))
        .modificarEstado(qr1, EstadoViandaEnum.RETIRADA);
    assertEquals(
        0, instancia.cantidadViandas(HELADERA_ID), "Las viandas no se agregaron correctamente");
  }

  @Test
  @DisplayName("Guardar y obtener temperaturas")
  void testTemperaturas() {

    instancia.temperatura(new TemperaturaDTO(14, 14, LocalDateTime.now()));
    instancia.temperatura(new TemperaturaDTO(15, 15, LocalDateTime.now()));
    instancia.temperatura(new TemperaturaDTO(16, 14, LocalDateTime.now()));

    List<TemperaturaDTO> temperaturaDTOS = instancia.obtenerTemperaturas(14);
    assertEquals(
        2,
        temperaturaDTOS,
        "Las temperaturas de una heladera no se guardan/recuperan correctamente");
    List<TemperaturaDTO> sortedTemperatures =
        temperaturaDTOS.stream()
            .sorted((f1, f2) -> f2.getFechaMedicion().compareTo(f2.getFechaMedicion()))
            .collect(Collectors.toList());
    assertEquals(
        16,
        sortedTemperatures.get(0).getTemperatura(),
        "Las temperaturas de una heladera no se guardan/recuperan correctamente");
  }

  @Override
  public String paquete() {
    return PAQUETE_BASE + "heladeras";
  }

  @Override
  public Class<FachadaHeladeras> clase() {
    return FachadaHeladeras.class;
  }
}
