package ar.edu.utn.dds.k3003.tests.logistica;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.*;
import ar.edu.utn.dds.k3003.facades.exceptions.TrasladoNoAsignableException;
import ar.edu.utn.dds.k3003.tests.TestTP;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LogisticaTest implements TestTP<FachadaLogistica> {

  private static final String QR_VIANDA = "123";
  private static final int HELADERA_ORIGEN = 1;
  private static final int HELADERA_DESTINO = 2;

  FachadaLogistica instancia;
  @Mock FachadaViandas fachadaViandas;
  @Mock FachadaHeladeras fachadaHeladeras;

  @SneakyThrows
  @BeforeEach
  void setUp() {

    instancia = this.instance();
    instancia.setHeladerasProxy(fachadaHeladeras);
    instancia.setViandasProxy(fachadaViandas);
  }

  @Test
  @DisplayName("Agregar una ruta y asignar un traslado")
  void testAgregarRutaYasignarTraslado() throws Exception {

    ViandaDTO t =
        new ViandaDTO(
            QR_VIANDA,
            LocalDateTime.now(),
            EstadoViandaEnum.PREPARADA,
            15L,
            LogisticaTest.HELADERA_ORIGEN);
    when(fachadaViandas.buscarXQR(QR_VIANDA)).thenReturn(t);
    var agregar =
        instancia.agregar(new RutaDTO(14L, LogisticaTest.HELADERA_ORIGEN, HELADERA_DESTINO));
    instancia.agregar(new RutaDTO(15L, LogisticaTest.HELADERA_ORIGEN, 3));
    assertNotNull(agregar.getId(), "la ruta una vez agregada deberia tener un identificador");

    var traslado = new TrasladoDTO(QR_VIANDA, LogisticaTest.HELADERA_ORIGEN, HELADERA_DESTINO);
    var trasladoDTO = instancia.asignarTraslado(traslado);

    assertEquals(
        EstadoTrasladoEnum.ASIGNADO,
        trasladoDTO.getStatus(),
        "el estado de un traslado debe figurar como asignado luego de una asignación");
    assertEquals(14L, trasladoDTO.getColaboradorId(), "No se asigno el colaborador correcto");
    verify(
            fachadaViandas,
            VerificationModeFactory.description(
                times(1), "Nunca se verificó si el QR de la vianda existe"))
        .buscarXQR(QR_VIANDA);
  }

  @Test
  @DisplayName("Asignar un traslado a una vianda que no existe")
  void testAsignarRutaAViandaInexistente() {
    when(fachadaViandas.buscarXQR(QR_VIANDA)).thenThrow(NoSuchElementException.class);
    var agregar = instancia.agregar(new RutaDTO(14L, HELADERA_ORIGEN, HELADERA_DESTINO));
    instancia.agregar(agregar);

    var traslado = new TrasladoDTO(QR_VIANDA, HELADERA_ORIGEN, HELADERA_DESTINO);

    assertThrows(
        NoSuchElementException.class,
        () -> instancia.asignarTraslado(traslado),
        "Si el QR no existe en el traslado deberia fallar");
  }

  @Test
  @DisplayName("Asignar un traslado con una ruta que no tiene nadie asignado")
  void testTrasladoNoAsignable() {
    instancia.agregar(new RutaDTO(15L, HELADERA_ORIGEN, 3));
    var traslado = new TrasladoDTO(QR_VIANDA, HELADERA_ORIGEN, HELADERA_DESTINO);

    assertThrows(
        TrasladoNoAsignableException.class,
        () -> instancia.asignarTraslado(traslado),
        "Si no hay rutas válidas para un traslado deberia arrojar una excepcion");
  }

  @Test
  @DisplayName("Probar Traslado")
  void testTrasladoOk() throws TrasladoNoAsignableException {

    instancia.agregar(new RutaDTO(15L, HELADERA_ORIGEN, HELADERA_DESTINO));
    var traslado = new TrasladoDTO(QR_VIANDA, HELADERA_ORIGEN, HELADERA_DESTINO);
    TrasladoDTO trasladoDTO = instancia.asignarTraslado(traslado);

    instancia.trasladoRetirado(trasladoDTO.getId());
    verify(fachadaHeladeras).retirar(argThat(retiro -> retiro.getQrVianda().equals(QR_VIANDA)));
    verify(fachadaViandas).modificarEstado(QR_VIANDA, EstadoViandaEnum.EN_TRASLADO);

    instancia.trasladoDepositado(trasladoDTO.getId());
    verify(fachadaHeladeras).retirar(argThat(retiro -> retiro.getQrVianda().equals(QR_VIANDA)));
    verify(fachadaViandas).modificarHeladera(QR_VIANDA, HELADERA_DESTINO);
    verify(fachadaViandas).modificarEstado(QR_VIANDA, EstadoViandaEnum.DEPOSITADA);
  }

  @Override
  public String paquete() {
    return PAQUETE_BASE + "tests.logistica";
  }

  @Override
  public Class<FachadaLogistica> clase() {
    return FachadaLogistica.class;
  }
}
