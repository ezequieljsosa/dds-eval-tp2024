package ar.edu.utn.dds.k3003.facades.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class HeladeraDTO {
  private Integer id;
  private final String nombre;
}
