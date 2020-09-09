package tiralabra.polunraivaaja.algoritmit;

import java.util.List;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;

/**
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public interface Haku {

    void setKartta(Kartta kartta);

    /**
     * Etsii jonkin lyhimmistä reiteistä parametreina saatujen alku- ja
     * loppupisteiden välillä.
     *
     * @param alku Haettavan reitin alkupiste.
     * @param loppu Haettavan reitin loppupiste.
     * @return True, jos reitti löytyi; false, jos reittiä ei löytynyt tai ei
     * voitu hakea.
     */
    boolean etsiReitti(Koordinaatti alku, Koordinaatti loppu);

    /**
     * Palauttaa haun löytämää reittiä kuvaavan listan Koordinaatteja.
     *
     * @return Lista Koordinaatteja.
     */
    List<Koordinaatti> getReitti();
}
