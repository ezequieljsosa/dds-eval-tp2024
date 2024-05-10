package ar.edu.utn.dds.k3003.facades.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RutaDTO {

  private Long id;
  private Long colaboradorId;
  private Integer heladeraIdOrigen;
  private Integer heladeraIdDestino;

  public RutaDTO(Long colaboradorId, Integer heladeraIdOrigen, Integer heladeraIdDestino) {
    this.colaboradorId = colaboradorId;
    this.heladeraIdOrigen = heladeraIdOrigen;
    this.heladeraIdDestino = heladeraIdDestino;
  }
}
