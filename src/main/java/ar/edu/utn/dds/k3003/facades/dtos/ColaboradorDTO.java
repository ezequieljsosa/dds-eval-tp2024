package ar.edu.utn.dds.k3003.facades.dtos;

import java.util.List;
import lombok.Data;

@Data
public final class ColaboradorDTO {

  private Long id;
  private final String nombre;
  private final List<FormaDeColaborarEnum> formas;
}
