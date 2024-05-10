package ar.edu.utn.dds.k3003.facades.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrasladoDTO {

  private Long id;
  private String qrVianda;
  private EstadoTrasladoEnum status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_SERIALIZATION_FORMAT)
  private LocalDateTime fechaTraslado;

  private Integer heladeraOrigen;
  private Integer heladeraDestino;
  private Long colaboradorId;

  public TrasladoDTO(
      String qrVianda,
      EstadoTrasladoEnum status,
      LocalDateTime fechaTraslado,
      Integer heladeraOrigen,
      Integer heladeraDestino) {
    this.qrVianda = qrVianda;
    this.status = status;
    this.fechaTraslado = fechaTraslado;
    this.heladeraOrigen = heladeraOrigen;
    this.heladeraDestino = heladeraDestino;
  }

  public TrasladoDTO(String qrVianda, Integer heladeraOrigen, Integer heladeraDestino) {
    this(qrVianda, EstadoTrasladoEnum.CREADO, LocalDateTime.now(), heladeraOrigen, heladeraDestino);
  }
}
