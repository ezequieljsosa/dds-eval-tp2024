package ar.edu.utn.dds.k3003.facades.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TemperaturaDTO {
  private final Integer temperatura;
  private final Integer heladeraId;
  private final LocalDateTime fechaMedicion;
}
