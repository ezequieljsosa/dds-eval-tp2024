package ar.edu.utn.dds.k3003.tests.test3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConErrorTest {

  @Test
  void testAgregarColaborador() {

    assertTrue(true);
  }

  @Test
  @DisplayName("Estas comiendo tus verduras?")
  void testAgregarColaboradorError() {

    assertTrue(false, "Error!!!");
  }
}
