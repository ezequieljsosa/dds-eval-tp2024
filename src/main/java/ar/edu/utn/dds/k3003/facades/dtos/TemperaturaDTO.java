package ar.edu.utn.dds.k3003.facades.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TemperaturaDTO {

  private Integer temperatura;
  private Integer heladeraId;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_SERIALIZATION_FORMAT)
  private LocalDateTime fechaMedicion;
}
