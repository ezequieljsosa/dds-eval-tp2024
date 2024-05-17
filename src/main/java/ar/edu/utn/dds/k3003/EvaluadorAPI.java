package ar.edu.utn.dds.k3003;

import lombok.SneakyThrows;

public class EvaluadorAPI {

  private static final String APPWEB = "ar.edu.utn.dds.k3003.app.WebApp";
  public static String BASE_URL = null;

  @SneakyThrows
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Uso: [URL] [APP]");
      System.out.println("Ejemplo URL: http://localhost:8080 --> no poner '/' al final");
      System.out.println("APPs uno de los siguientes: logistica heladeras viandas colaboradores");
      System.exit(2);
    }
    BASE_URL = args[0];
    if (args[1].equalsIgnoreCase("colaboradores")) {
      System.exit(Evaluador.runTests("ar.edu.utn.dds.k3003.tests.web.colaboradores"));
    }
    if (args[1].equalsIgnoreCase("heladeras")) {
      System.exit(Evaluador.runTests("ar.edu.utn.dds.k3003.tests.web.heladeras"));
    }
    if (args[1].equalsIgnoreCase("logistica")) {
      System.exit(Evaluador.runTests("ar.edu.utn.dds.k3003.tests.web.logistica"));
    }
    if (args[1].equalsIgnoreCase("viandas")) {
      System.exit(Evaluador.runTests("ar.edu.utn.dds.k3003.tests.web.viandas"));
    }
    System.exit(1);
  }
}
