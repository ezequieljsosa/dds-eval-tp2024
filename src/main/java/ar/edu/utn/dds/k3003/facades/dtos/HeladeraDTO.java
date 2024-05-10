package ar.edu.utn.dds.k3003.facades.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeladeraDTO {
  private Integer id;
  private String nombre;

  public HeladeraDTO(String nombre) {
    this.nombre = nombre;
  }
}
