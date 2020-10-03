package tiralabra.polunraivaaja.apurakenteet;

public class Laskin {

  public static final double SQRT_2 = 1.414213562;

  public static int laskeItseisarvo(int luku) {
    return luku > 0 ? luku : -luku;
  }

  public static double laskeItseisarvo(double luku) {
    return luku > 0 ? luku : -luku;
  }

  public static int pyoristaKokonaislukuun(double luku) {
    int kokonaisosa = (int) luku;
    double desimaaliosa = laskeItseisarvo(luku) - laskeItseisarvo(kokonaisosa);

    if (kokonaisosa > 0) {
      return desimaaliosa < 0.5 ? kokonaisosa : kokonaisosa + 1;
    }
    return desimaaliosa < 0.5 ? kokonaisosa : kokonaisosa - 1;
  }

  public static int valitsePienempi(int luku1, int luku2) {
    return luku1 <= luku2 ? luku1 : luku2;
  }
}
