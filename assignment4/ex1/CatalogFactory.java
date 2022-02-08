package ex1;


public class CatalogFactory {

  private static final int NUMBER_OF_GAMES = Parameters.getNumberGames();

  public static Catalog create() {
    String catalogClassName = System.getProperty("catalogClass", "ex1.CatalogReadWriteLock");
    try {
      Class<? extends Catalog> catalogClass;
      catalogClass = Class.forName(catalogClassName).asSubclass(Catalog.class);
      return catalogClass.getDeclaredConstructor(int.class).newInstance(NUMBER_OF_GAMES);
    } catch (ClassNotFoundException ex) {
      String err = "Cannot create Catalog. Unable to find class: \"" + catalogClassName + "\"";
      System.err.println(err);
      System.exit(1);
    } catch (Exception e) {
      System.err.println("Internal fatal error");
      System.exit(2);
    }
    return null;
  }

}
