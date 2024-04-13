package ar.edu.utn.dds.k3003.facades;

import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import java.util.List;
import java.util.NoSuchElementException;

public interface FachadaViandas {

  ViandaDTO agregar(ViandaDTO vianda);

  ViandaDTO modificarEstado(String qr, EstadoViandaEnum estado) throws NoSuchElementException;

  List<ViandaDTO> viandasDeColaborador(Long colaboradorId, Integer mes, Integer anio)
      throws NoSuchElementException;

  ViandaDTO buscarXQR(String qr) throws NoSuchElementException;

  void setHeladerasProxy(FachadaHeladeras fachadaHeladeras);

  boolean evaluarVencimiento(String QR) throws NoSuchElementException;

  ViandaDTO modificarHeladera(String qrVianda, int heladeraDestino);
}
