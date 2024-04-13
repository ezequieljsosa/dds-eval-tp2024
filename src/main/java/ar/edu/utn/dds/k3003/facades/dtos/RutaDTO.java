package ar.edu.utn.dds.k3003.facades.dtos;

import lombok.Data;

@Data
public class RutaDTO {

  private Long id;
  private final Long colaboradorId;
  private final Integer heladeraIdOrigen;
  private final Integer heladeraIdDestino;
}
