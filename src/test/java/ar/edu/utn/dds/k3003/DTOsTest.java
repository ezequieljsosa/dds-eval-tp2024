package ar.edu.utn.dds.k3003;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.dtos.RetiroDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DTOsTest {

  @Mock FachadaHeladeras fachadaHeladeras;

  @Test
  void testRetiroDTOEquals() {
    RetiroDTO retiro1 = new RetiroDTO("333", "321", 14);
    RetiroDTO retiro2 = new RetiroDTO("333", "321", 14);
    assertEquals(retiro1, retiro2);
    fachadaHeladeras.retirar(retiro2);
    verify(fachadaHeladeras).retirar(eq(retiro1));
  }
}
