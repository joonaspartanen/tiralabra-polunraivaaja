package tiralabra.polunraivaaja.tyokalut;

import tiralabra.polunraivaaja.mallit.Ruutu;

/**
 * Tarjoaa apumetodeja taulukoiden käsittelyyn.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Taulukonkasittelija {

    /**
     * Luo parametrina annetusta taulukosta kopion, jonka kapasiteetti on
     * kaksinkertainen.
     *
     * @param taulukko Alkuperäinen taulukko.
     * @return Kapasiteetiltaan kaksinkertainen kopio taulukosta.
     */
    public static Ruutu[] kasvataTaulukko(Ruutu[] taulukko) {
        int uusiKoko = taulukko.length * 2;
        Ruutu[] uusiTaulukko = new Ruutu[uusiKoko];

        kopioiTaulukko(taulukko, uusiTaulukko);

        return uusiTaulukko;
    }

    private static void kopioiTaulukko(Ruutu[] alkuperainen, Ruutu[] kopio) {
        for (int i = 0; i < alkuperainen.length; i++) {
            kopio[i] = alkuperainen[i];
        }
    }

    /**
     * Tarkistaa, onko taulukossa vielä tilaa uusille ruuduille.
     *
     * @param taulukko Tarkasteltava taulukko.
     * @param ruutuja  Taulukossa olevien ruutujen määrä.
     * @return true jos taulukko täynnä; false jos taulukossa on vielä tilaa.
     */
    public static boolean taulukkoTaynna(Ruutu[] taulukko, int ruutuja) {
        return ruutuja == taulukko.length;
    }

    /**
     * Alustaa parametrina saamansa liukulukutaulukon jokaisen solun arvoksi
     * suurimman liukulukuarvon.
     *
     * @param taulukko Alustettava taulukko.
     */
    public static void alustaLiukulukuTaulukko(double[][] taulukko) {
        for (int i = 0; i < taulukko.length; i++) {
            for (int j = 0; j < taulukko[0].length; j++) {
                taulukko[i][j] = Laskin.DOUBLE_MAX_VALUE;
            }
        }
    }

    /**
     * Alustaa parametrina saamansa boolean-taulukon jokaisen solun arvoksi false.
     *
     * @param taulukko Alustettava taulukko.
     */
    public static void alustaBooleanTaulukko(boolean[][] taulukko) {
        for (int i = 0; i < taulukko.length; i++) {
            for (int j = 0; j < taulukko[0].length; j++) {
                taulukko[i][j] = false;
            }
        }
    }

    /**
     * Alustaa parametrina saamansa Ruutu-taulukon jokaisen solun arvoksi null.
     *
     * @param taulukko Alustettava taulukko.
     */
    public static void alustaRuutuTaulukko(Ruutu[][] taulukko) {
        for (int i = 0; i < taulukko.length; i++) {
            for (int j = 0; j < taulukko[0].length; j++) {
                taulukko[i][j] = null;
            }
        }
    }
}
