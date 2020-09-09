package tiralabra.polunraivaaja.kartta;

import java.util.List;

import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;

/**
 * Abstrakti luokka, jonka toteuttavat luokat piirtävät karttoja esim. konsoliin
 * tai graafiseen käyttöliittymään.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public abstract class Kartanpiirtaja {

    protected Kartta kartta;
    protected int korkeus;
    protected int leveys;

    public Kartanpiirtaja(Kartta kartta) {
        this.kartta = kartta;
        this.korkeus = kartta.getKorkeus();
        this.leveys = kartta.getLeveys();
    }

    /**
     * Piirtää pelkän pohjakartan.
     */
    public abstract void piirraKartta();

    /**
     * Piirtää pohjakartan ja siihen parametrina saadun reitin.
     *
     * @param reitti Pohjakartan päälle piirrettävä reitti.
     */
    public abstract void piirraKartta(List<Koordinaatti> reitti);
}
