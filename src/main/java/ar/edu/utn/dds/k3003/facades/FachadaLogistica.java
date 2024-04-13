package ar.edu.utn.dds.k3003.facades;

import ar.edu.utn.dds.k3003.facades.dtos.RutaDTO;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.facades.exceptions.TrasladoNoAsignableException;
import java.util.List;
import java.util.NoSuchElementException;

public interface FachadaLogistica {

  RutaDTO agregar(RutaDTO ruta);

  TrasladoDTO buscarXId(Long trasladoId) throws NoSuchElementException;

  TrasladoDTO asignarTraslado(TrasladoDTO traslado) throws TrasladoNoAsignableException;

  List<TrasladoDTO> trasladosDeColaborador(Long id, Integer mes, Integer anio);

  void setHeladerasProxy(FachadaHeladeras fachadaHeladeras);

  void setViandasProxy(FachadaViandas fachadaViandas);

  void trasladoRetirado(Long id);

  void trasladoDepositado(Long id);
}
