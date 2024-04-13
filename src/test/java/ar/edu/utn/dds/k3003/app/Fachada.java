package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.facades.FachadaColaboradores;
import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import java.util.List;

public class Fachada implements FachadaColaboradores {

  @Override
  public ColaboradorDTO agregar(ColaboradorDTO colaborador) {
    return null;
  }

  @Override
  public ColaboradorDTO buscarXId(Long colaboradorId) {
    return null;
  }

  @Override
  public ColaboradorDTO modificar(Long colaboradorId, List<FormaDeColaborarEnum> formas) {
    return null;
  }

  @Override
  public void actualizarPesosPuntos(
      Double pesosDonados,
      Double viandasDistribuidas,
      Double viandasDonadas,
      Double tarjetasRepartidas,
      Double heladerasActivas) {}

  @Override
  public void setLogisticaProxy(FachadaLogistica logistica) {}

  @Override
  public void setViandasProxy(FachadaViandas viandas) {}

  @Override
  public Double puntos(Long colaboradorId) {
    return null;
  }
}
