package ar.edu.utn.dds.k3003;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import ar.edu.utn.dds.k3003.facades.dtos.Constants;
import ar.edu.utn.dds.k3003.tests.colaboradores.ColaboradoresTest;
import ar.edu.utn.dds.k3003.tests.heladeras.HeladerasTest;
import ar.edu.utn.dds.k3003.tests.logistica.LogisticaTest;
import ar.edu.utn.dds.k3003.tests.viandas.ViandasTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

public class Evaluador {

  public static void main(String[] args) {
    var tests =
        List.of(
            new ColaboradoresTest(), new HeladerasTest(), new LogisticaTest(), new ViandasTest());
    var first = tests.stream().filter(t -> t.corresponde()).findFirst();
    if (first.isEmpty()) {
      System.err.println("No se encontró la clase Fachada");
      System.exit(3);
    }
    System.exit(runTests(first.get().paquete()));
  }

  public static int runTests(String paqueteTests) {

    var listener = new SummaryGeneratingListener();
    var request =
        LauncherDiscoveryRequestBuilder.request()
            .selectors(selectPackage(paqueteTests))
            .filters(includeClassNamePatterns(".*Test"))
            .build();
    var launcher = LauncherFactory.create();
    launcher.discover(request);
    launcher.registerTestExecutionListeners(listener);
    launcher.execute(request);

    var summary = listener.getSummary();
    summary.printTo(new PrintWriter(System.out));
    summary.getFailures().stream()
        .forEach(
            f -> {
              System.err.println(
                  f.getTestIdentifier().getDisplayName()
                      + ": "
                      + f.getException().getLocalizedMessage());
            });

    if (listener.getSummary().getTestsFoundCount() == 0) {
      System.err.println("No se encontraron pruebas...");
      return 1;
    }
    if (summary.getTestsFoundCount() != summary.getTestsSucceededCount()) {
      return 2;
    }
    return 0;
  }

  public static ObjectMapper createObjectMapper() {
    var objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    var sdf = new SimpleDateFormat(Constants.DEFAULT_SERIALIZATION_FORMAT, Locale.getDefault());
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    objectMapper.setDateFormat(sdf);
    return objectMapper;
  }
}
