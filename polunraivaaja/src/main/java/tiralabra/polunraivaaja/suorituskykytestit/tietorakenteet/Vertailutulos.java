package tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet;

/**
 * Omia ja Javan valmiita tietorakenteita testaavien suorituskykytestien
 * tulokset kääritään Vertailutulos-luokkaan.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Vertailutulos {

    private long omanRakenteenAika;
    private long javaRakenteenAika;
    private int iteraatioita;

    public Vertailutulos(long omanRakenteenAika, long javaRakenteenAika, int iteraatioita) {
        this.omanRakenteenAika = omanRakenteenAika;
        this.javaRakenteenAika = javaRakenteenAika;
        this.iteraatioita = iteraatioita;
    }

    private long aikaMillisekunteina(long aika) {
        return aika / 1000000;
    }

    @Override
    public String toString() {
        return "SUORITUSKYKYVERTAILU: \n" + "Iteraatioita: " + iteraatioita + "\n" + "Oman rakenteen aika: "
                + aikaMillisekunteina(omanRakenteenAika) + " ms \n" + "Javan valmiin tietorakenteen aika "
                + aikaMillisekunteina(javaRakenteenAika) + " ms";
    }

}
