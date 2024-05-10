package ar.edu.utn.dds.k3003.facades.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RetiroDTO {
  @Setter private Long id;
  private String qrVianda;
  private String tarjeta;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_SERIALIZATION_FORMAT)
  @EqualsAndHashCode.Exclude
  private LocalDateTime fechaRetiro;

  private Integer heladeraId;

  public RetiroDTO(String qrVianda, String tarjeta, Integer heladeraId) {
    this.qrVianda = qrVianda;
    this.tarjeta = tarjeta;
    this.heladeraId = heladeraId;
    this.fechaRetiro = LocalDateTime.now();
  }

  public RetiroDTO(String qrVianda, String tarjeta, LocalDateTime fechaRetiro, Integer heladeraId) {
    this.qrVianda = qrVianda;
    this.tarjeta = tarjeta;
    this.heladeraId = heladeraId;
    this.fechaRetiro = fechaRetiro;
  }
}
