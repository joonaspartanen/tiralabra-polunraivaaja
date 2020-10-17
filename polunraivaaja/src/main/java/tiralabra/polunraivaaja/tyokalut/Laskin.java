package tiralabra.polunraivaaja.tyokalut;

/**
 * Apuluokka, joka tarjoaa matemaattisiin operaatioihin liittyviä staattisia
 * metodeja ja vakioita.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Laskin {

    /**
     * Luvun 2 neliöjuuren likiarvo.
     */
    public static final double SQRT_2 = 1.414213562;

    /**
     * Suurin mahdollinen double-tyyppinen liukulukuarvo.
     */
    public static final double DOUBLE_MAX_VALUE = 1.7976931348623157E308;

    /**
     * Laskee kokonaisluvun itseisarvon.
     *
     * @param luku Tarkastelun kohteena oleva kokonaisluku.
     * @return Tarkasteltavan luvun itseisarvo.
     */
    public static int laskeItseisarvo(int luku) {
        return luku > 0 ? luku : -luku;
    }

    /**
     * Laskee liukluvun itseisarvon.
     *
     * @param luku Tarkastelun kohteena oleva liukuluku.
     * @return Tarkasteltavan luvun itseisarvo.
     */
    public static double laskeItseisarvo(double luku) {
        return luku > 0 ? luku : -luku;
    }

    /**
     * Pyöristää liukuluvun lähimpään kokonaislukuun normaalien pyöristyssääntöjen
     * mukaisesti.
     *
     * @param luku Pyöristettävä liukuluku.
     * @return Pyöristyksen tuloksena saatu kokonaisluku.
     */
    public static int pyoristaKokonaislukuun(double luku) {
        int kokonaisosa = (int) luku;
        double desimaaliosa = laskeItseisarvo(luku) - laskeItseisarvo(kokonaisosa);

        if (kokonaisosa > 0) {
            return desimaaliosa < 0.5 ? kokonaisosa : kokonaisosa + 1;
        }
        return desimaaliosa < 0.5 ? kokonaisosa : kokonaisosa - 1;
    }

    /**
     * Vertaa kahta kokonaislukua ja palauttaa niistä pienemmän.
     *
     * @param luku1 Ensimmäinen verrattava luku.
     * @param luku2 Toinen verrattava luku.
     *
     * @return Verrattavista luvuista pienempi.
     */
    public static int valitsePienempi(int luku1, int luku2) {
        return luku1 <= luku2 ? luku1 : luku2;
    }
}
