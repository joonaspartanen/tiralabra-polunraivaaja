package tiralabra.polunraivaaja.algoritmit;

import tiralabra.polunraivaaja.mallit.Hakutulos;
import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuLista;

/**
 * Erilaisia reitinhakualgoritmeja kuvaava rajapinta.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public interface Haku {

    /**
     * Etsii jonkin lyhimmistä reiteistä parametreina saatujen alku- ja
     * loppupisteiden välillä.
     *
     * @param alku Haettavan reitin alkupiste.
     * @param loppu Haettavan reitin loppupiste.
     * @return Palauttaa haun tulosta kuvaavan Hakutulos-olion.
     */
    Hakutulos etsiReitti(Ruutu alku, Ruutu loppu);

    /**
     * Palauttaa haun löytämää reittiä kuvaavan listan Ruutuja.
     *
     * @return Lista Ruutuja.
     */
    RuutuLista getReitti();

    /**
     *
     * @param salliDiagonaaliSiirtymat Totuusarvo, joka kertoo sallitaanko
     * vinottainen liikkuminen kartalla.
     */
    void setSalliDiagonaalisiirtymat(boolean salliDiagonaaliSiirtymat);
}
