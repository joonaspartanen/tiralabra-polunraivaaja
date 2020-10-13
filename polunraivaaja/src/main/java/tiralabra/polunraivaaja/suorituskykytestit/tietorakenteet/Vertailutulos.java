package tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet;

import tiralabra.polunraivaaja.suorituskykytestit.Suorituskykytulos;

/**
 * Omia ja Javan valmiita tietorakenteita testaavien suorituskykytestien
 * tulokset kääritään Vertailutulos-luokkaan.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Vertailutulos extends Suorituskykytulos {

    private long omanRakenteenAika;
    private long javaRakenteenAika;

    public Vertailutulos(long omanRakenteenAika, long javaRakenteenAika, int iteraatioita) {
        super(iteraatioita);
        this.omanRakenteenAika = omanRakenteenAika;
        this.javaRakenteenAika = javaRakenteenAika;
        this.iteraatioita = iteraatioita;
    }

    @Override
    public String toString() {
        return "\n SUORITUSKYKYVERTAILU: \n" + "Iteraatioita: " + iteraatioita + "\n" + "Oman rakenteen aika: "
                + aikaMillisekunteina(omanRakenteenAika) + " ms \n" + "Javan valmiin tietorakenteen aika "
                + aikaMillisekunteina(javaRakenteenAika) + " ms";
    }

}
