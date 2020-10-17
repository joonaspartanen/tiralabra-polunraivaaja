package tiralabra.polunraivaaja.suorituskykytestit.algoritmit;

import tiralabra.polunraivaaja.algoritmit.Haku;
import tiralabra.polunraivaaja.mallit.Hakutulos;

/**
 * Suorittaa reitinhakualgoritmin suorituskykymittauksen tietyllä algoritmilla
 * ja reittikuvauksella.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class AlgoritmiSuorituskykytesti {

    /**
     * Mittauksen kohteena oleva reitinhakualgoritmi.
     */
    private final Haku haku;

    /**
     * Konstruktori.
     * 
     * @param haku Mittauksen kohteena oleva reitinhakualgoritmi.
     */
    public AlgoritmiSuorituskykytesti(Haku haku) {
        this.haku = haku;
    }

    /**
     * Suorittaa mittauksen tietyllä reittikuvauksella.
     *
     * @param reittikuvaus Sisältää tiedon etsittävän reitin alku- ja
     *                     loppupisteistä.
     * @return Reitin etsimiseen kulunut aika nanosekunteina.
     */
    public long suorita(Reittikuvaus reittikuvaus) {
        Hakutulos tulos = haku.etsiReitti(reittikuvaus.getAlku(), reittikuvaus.getLoppu());

        return tulos.getHaunKesto();
    }

}
