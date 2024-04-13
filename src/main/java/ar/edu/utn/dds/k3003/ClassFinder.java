package ar.edu.utn.dds.k3003;

import java.util.Objects;

public class ClassFinder {

  public static <T> T findAndInstantiateClassImplementingInterface(
      String fullClassName, Class<T> interfaceClass) throws Exception {
    Objects.requireNonNull(fullClassName, "Package/Class name cannot be null");
    Objects.requireNonNull(interfaceClass, "Interface class cannot be null");

    var classLoader = Thread.currentThread().getContextClassLoader();

    var clazz = Class.forName(fullClassName, true, classLoader);

    if (interfaceClass.isAssignableFrom(clazz)) {
      var constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      return (T) constructor.newInstance();
    }
    throw new ClassNotFoundException(
        "No class implementing the interface found in: " + fullClassName);
  }
}
