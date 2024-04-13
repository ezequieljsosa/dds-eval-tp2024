package ar.edu.utn.dds.k3003.facades;

import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import java.util.List;
import java.util.NoSuchElementException;

public interface FachadaColaboradores {

  ColaboradorDTO agregar(ColaboradorDTO colaborador);

  ColaboradorDTO buscarXId(Long colaboradorId) throws NoSuchElementException;

  Double puntos(Long colaboradorId) throws NoSuchElementException;

  ColaboradorDTO modificar(Long colaboradorId, List<FormaDeColaborarEnum> formas)
      throws NoSuchElementException;

  void actualizarPesosPuntos(
      Double pesosDonados, // 0.5
      Double viandasDistribuidas, // 1
      Double viandasDonadas, // 1.5
      Double tarjetasRepartidas, // 2
      Double heladerasActivas); // 5

  void setLogisticaProxy(FachadaLogistica logistica);

  void setViandasProxy(FachadaViandas viandas);
}
