package tiralabra.polunraivaaja.algoritmit;

import java.util.List;

import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.kartta.Kartta;

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
    List<Ruutu> getReitti();

    /**
     *
     * @param kartta Reittihaun kohteena oleva kartta.
     */
    void setKartta(Kartta kartta);

    /**
     *
     * @param salliDiagonaaliSiirtymat Totuusarvo, joka kertoo sallitaanko
     * vinottainen liikkuminen kartalla.
     */
    void setSalliDiagonaalisiirtymat(boolean salliDiagonaaliSiirtymat);
}
