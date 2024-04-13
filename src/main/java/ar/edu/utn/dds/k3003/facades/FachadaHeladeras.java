package ar.edu.utn.dds.k3003.facades;

import ar.edu.utn.dds.k3003.facades.dtos.HeladeraDTO;
import ar.edu.utn.dds.k3003.facades.dtos.RetiroDTO;
import ar.edu.utn.dds.k3003.facades.dtos.TemperaturaDTO;
import java.util.List;
import java.util.NoSuchElementException;

public interface FachadaHeladeras {

  HeladeraDTO agregar(HeladeraDTO heladera);

  void depositar(Integer heladeraId, String qrVianda) throws NoSuchElementException;

  Integer cantidadViandas(Integer heladeraId) throws NoSuchElementException;

  void retirar(RetiroDTO retiro) throws NoSuchElementException;

  void temperatura(TemperaturaDTO temperatura);

  List<TemperaturaDTO> obtenerTemperaturas(Integer heladeraId);

  void setViandasProxy(FachadaViandas viandas);
}
