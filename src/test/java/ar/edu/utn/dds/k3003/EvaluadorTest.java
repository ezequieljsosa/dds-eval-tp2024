package ar.edu.utn.dds.k3003;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EvaluadorTest {

  public static final String PACKAGE_ROOT = "ar.edu.utn.dds.k3003";

  @Test
  public void testEvaluador() {

    int i = Evaluador.runTests(PACKAGE_ROOT + ".tests.test2");
    assertEquals(0, i);
    i = Evaluador.runTests(PACKAGE_ROOT + ".tests.test3");
    assertEquals(2, i);
    i = Evaluador.runTests(PACKAGE_ROOT + ".tests.test4");
    assertEquals(1, i);

    i = Evaluador.runTests(PACKAGE_ROOT + ".app");
    assertEquals(0, i);
  }
}
