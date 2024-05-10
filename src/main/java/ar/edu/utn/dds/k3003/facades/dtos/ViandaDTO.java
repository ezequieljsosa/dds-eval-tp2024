package ar.edu.utn.dds.k3003.facades.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViandaDTO {

  private Long id;
  private String codigoQR;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_SERIALIZATION_FORMAT)
  private LocalDateTime fechaElaboracion;

  private EstadoViandaEnum estado;
  private Long colaboradorId;
  private Integer heladeraId;

  public ViandaDTO(
      String codigoQR,
      LocalDateTime fechaElaboracion,
      EstadoViandaEnum estado,
      Long colaboradorId,
      Integer heladeraId) {
    this.codigoQR = codigoQR;
    this.fechaElaboracion = fechaElaboracion;
    this.estado = estado;
    this.colaboradorId = colaboradorId;
    this.heladeraId = heladeraId;
  }
}
