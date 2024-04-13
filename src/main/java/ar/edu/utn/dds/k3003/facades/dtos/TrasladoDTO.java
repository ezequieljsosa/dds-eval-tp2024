package ar.edu.utn.dds.k3003.facades.dtos;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TrasladoDTO {

  public TrasladoDTO(String qrVianda, Integer heladeraOrigen, Integer heladeraDestino) {
    this(qrVianda, EstadoTrasladoEnum.CREADO, LocalDateTime.now(), heladeraOrigen, heladeraDestino);
  }

  private Long id;
  private final String qrVianda;
  private final EstadoTrasladoEnum status;
  private final LocalDateTime fechaTraslado;
  private final Integer heladeraOrigen;
  private final Integer heladeraDestino;
  private Long colaboradorId;
}
