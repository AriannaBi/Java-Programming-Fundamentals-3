package ex1;

public class Parameters {

  private static int propertyOrDefault(String property, int def) {
    String value = System.getProperty(property);
    if(value == null) {
      return def;
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ex) {
      System.err.println("NumberFormatException reading property " + property);
      throw ex;
    }
  }

  public static int getNumberGames() {
    return propertyOrDefault("nGames", 3);
  }

  public static int getNumberGamers() {
    return propertyOrDefault("nGamers", 1000);
  }

  public static int getNumberDevelopers() {
    return propertyOrDefault("nDevelopers", 200);
  }

}
