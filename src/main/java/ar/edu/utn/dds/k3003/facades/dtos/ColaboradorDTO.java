package ar.edu.utn.dds.k3003.facades.dtos;

import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ColaboradorDTO {

  private Long id;
  private String nombre;
  private List<FormaDeColaborarEnum> formas;

  public ColaboradorDTO(String nombre, List<FormaDeColaborarEnum> formas) {
    this.nombre = nombre;
    this.formas = formas;
  }
}
