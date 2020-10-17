package tiralabra.polunraivaaja.suorituskykytestit.algoritmit;

import tiralabra.polunraivaaja.suorituskykytestit.Suorituskykytulos;

/**
 * Luokka, johon algoritmien suorituskykytestin mittaustulos kääritään.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Mittaustulos extends Suorituskykytulos {

    /**
     * Algoritmin suoritukseen kulunut aika nanosekunteina.
     */
    private long aika;

    /**
     * Testin aikana etsittyjen reittien määrä.
     */
    private int etsittyjaReitteja;

    /**
     * Konstruktori.
     *
     * @param aika         Algoritmin suoritukseen kulunut aika nanosekunteina.
     * @param iteraatioita Suorituskykytestin aikana suoritettujen iteraatioiden
     *                     määrä.
     * @ etsittyjaReitteja Testin aikana etsittyjen reittien maara.
     */
    public Mittaustulos(long aika, int iteraatioita, int etsittyjaReitteja) {
        super(iteraatioita);
        this.aika = aika;
        this.etsittyjaReitteja = etsittyjaReitteja;
    }

    @Override
    public String toString() {
        return "Suorituskertoja: " + iteraatioita + "\n" + "Etsittyjä reittejä: " + etsittyjaReitteja + "\n" + "Kokonaissuoritusaika: "
                + aikaMillisekunteina(aika) + " ms";
    }

}
