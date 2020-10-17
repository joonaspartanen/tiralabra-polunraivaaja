package tiralabra.polunraivaaja.suorituskykytestit.algoritmit;

import tiralabra.polunraivaaja.mallit.Ruutu;

/**
 * Tuple-tyyppinen apuluokka, joka sisältää haettavan reitin alku- ja
 * loppupisteet.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Reittikuvaus {

    /**
     * Haettavan reitin alkupiste.
     */
    private final Ruutu alku;

    /**
     * Haettavan reitin loppupiste.
     */
    private final Ruutu loppu;

    /**
     * Konstruktori.
     *
     * @param alku  Haettavan reitin alkupiste.
     * @param loppu Haettavan reitin loppupiste.
     *
     */
    public Reittikuvaus(Ruutu alku, Ruutu loppu) {
        this.alku = alku;
        this.loppu = loppu;
    }

    public Ruutu getAlku() {
        return alku;
    }

    public Ruutu getLoppu() {
        return loppu;
    }

    @Override
    public String toString() {
        return "Alku: " + alku + ", loppu: " + loppu;
    }
}