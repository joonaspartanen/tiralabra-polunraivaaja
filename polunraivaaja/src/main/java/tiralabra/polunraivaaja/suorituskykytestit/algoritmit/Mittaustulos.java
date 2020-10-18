package tiralabra.polunraivaaja.suorituskykytestit.algoritmit;

import tiralabra.polunraivaaja.tyokalut.Laskin;

/**
 * Luokka, johon algoritmien suorituskykytestin mittaustulos kääritään.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Mittaustulos {

    /**
     * Algoritmin suoritukseen kulunut aika nanosekunteina.
     */
    private long aika;

    /**
     * Testin aikana etsittyjen reittien määrä.
     */
    private int etsittyjaReitteja;

    /**
     * Määrittää, kuinka monta kertaa testiskenaarion reittien haku tehdään.
     * Lopullisessa mittaustuloksessa käytetään aikana eri suorituskertojen kestojen
     * mediaania.
     */
    private int suorituskertoja;

    /**
     * Konstruktori.
     *
     * @param aika            Algoritmin suoritukseen kulunut aika nanosekunteina.
     * @param suorituskertoja Suorituskykytestin aikana suoritettujen iteraatioiden
     *                        määrä. @ etsittyjaReitteja Testin aikana etsittyjen
     *                        reittien maara.
     */
    public Mittaustulos(long aika, int etsittyjaReitteja, int suorituskertoja) {
        this.aika = aika;
        this.etsittyjaReitteja = etsittyjaReitteja;
        this.suorituskertoja = suorituskertoja;
    }

    @Override
    public String toString() {
        return "Suorituskertoja: " + suorituskertoja + "\n" + "Etsittyjä reittejä: " + etsittyjaReitteja + "\n"
                + "Kokonaissuoritusaika: " + Laskin.aikaMillisekunteina(aika) + " ms";
    }

}
