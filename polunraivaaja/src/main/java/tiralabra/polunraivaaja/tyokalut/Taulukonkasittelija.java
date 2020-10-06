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
}
