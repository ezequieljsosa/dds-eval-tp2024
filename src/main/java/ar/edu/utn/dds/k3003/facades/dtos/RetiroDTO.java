package ar.edu.utn.dds.k3003.facades.dtos;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetiroDTO {

  private Long id;
  private final String qrVianda;
  private final String tarjeta;
  private final LocalDateTime fechaRetiro;
  private final Integer heladeraId;

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
