package ar.edu.utn.dds.k3003.facades.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ViandaDTO {

  private Long id;
  private final String codigoQR;
  private final LocalDateTime fechaElaboracion;
  private final EstadoViandaEnum estado;
  private final Long colaboradorId;
  private final Integer heladeraId;
}
