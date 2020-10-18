package tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet;

import tiralabra.polunraivaaja.tyokalut.Laskin;

/**
 * Omia ja Javan valmiita tietorakenteita testaavien suorituskykytestien
 * tulokset kääritään Vertailutulos-luokkaan.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Vertailutulos {

    /**
     * Oman tietorakenteen suoritusaika nanosekunteina.
     */
    private long omanRakenteenAika;

    /**
     * Javan valmiin tietorakenteen suoritusaika nanosekunteina.
     */
    private long javaRakenteenAika;

    /**
     * Kertoo, kuinka monta tietorakennetta rasittavaa operaatiota testin aikana suoritettiin.
     */
    private int operaatioita;

    public Vertailutulos(long omanRakenteenAika, long javaRakenteenAika, int operaatioita) {
        this.omanRakenteenAika = omanRakenteenAika;
        this.javaRakenteenAika = javaRakenteenAika;
        this.operaatioita = operaatioita;
    }

    @Override
    public String toString() {
        return "Suoritettuja operaatioita: " + operaatioita + "\nOman rakenteen aika: "
                + Laskin.aikaMillisekunteina(omanRakenteenAika) + " ms\nJavan valmiin tietorakenteen aika "
                + Laskin.aikaMillisekunteina(javaRakenteenAika) + " ms\n";
    }

}
