package tiralabra.polunraivaaja.suorituskykytestit.algoritmit;

import java.util.ArrayList;
import java.util.List;

import tiralabra.polunraivaaja.mallit.Kartta;

/**
 * Reitinhakualgoritmien suorituskykytestit käyttävät aineistonaan Skenaarioita,
 * joihin liittyy tietty kartta ja joukko reittikuvauksia, joille pyritään
 * etsimään lyhin reitti.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Skenaario {

    /**
     * Haun kohteena oleva kartta.
     */
    private final Kartta kartta;

    /**
     * Lista reittien alku- ja loppupisteitä, joille haetaan skenaarion aikana
     * lyhimmät reitit.
     */
    private final List<Reittikuvaus> reittikuvaukset;

    /**
     * Konstruktori.
     *
     * @param kartta Haun kohteena oleva kartta.
     */
    public Skenaario(Kartta kartta) {
        this.kartta = kartta;
        this.reittikuvaukset = new ArrayList<>();
    }

    public Kartta getKartta() {
        return kartta;
    }

    public List<Reittikuvaus> getReittikuvaukset() {
        return reittikuvaukset;
    }

    /**
     * Lisää yksittäisen reittikuvauksen reittikuvausten listaan.
     *
     * @param reittikuvaus Lisättävä Reittikuvaus-olio.
     */
    public void lisaaReittikuvaus(Reittikuvaus reittikuvaus) {
        reittikuvaukset.add(reittikuvaus);
    }
}
