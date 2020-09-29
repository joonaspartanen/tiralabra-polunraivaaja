package tiralabra.polunraivaaja.algoritmit;

import java.util.ArrayDeque;
import java.util.Queue;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.apurakenteet.Suunta;

/**
 * Leveyshakualgoritmin (BFS) toteutus. Hakee kartalta lyhimmän reitin kahden
 * pisteen välillä.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Leveyshaku extends HakuPohja {

    /**
     *
     * @param kartta Reittihaun kohteena oleva kartta.
     */
    public Leveyshaku(Kartta kartta) {
        super(kartta);
    }

    /**
     * Etsii jonkin lyhimmistä reiteistä parametreina saatujen alku- ja
     * loppupisteiden välillä.
     *
     * @param alku  Haettavan reitin alkupiste.
     * @param loppu Haettavan reitin loppupiste.
     * @return Palauttaa haun tulosta kuvaavan Hakutulos-olion.
     */
    @Override
    public Hakutulos etsiReitti(Ruutu alku, Ruutu loppu) {
        alkuAika = System.nanoTime();

        ruutujaTarkasteltu = 0;

        if (!reitinPaatVapaat(alku, loppu)) {
            return new Hakutulos(false, "Alku- tai loppupiste ei kelpaa.", ruutujaTarkasteltu, vierailtu);
        }

        this.alku = alku;
        this.loppu = loppu;

        Queue<Ruutu> jono = new ArrayDeque<>();

        jono.add(alku);
        vieraile(alku.y, alku.x);

        while (!jono.isEmpty()) {
            Ruutu nykyinenRuutu = jono.remove();

            for (int i = 0; i < 4; i++) {
                int y = nykyinenRuutu.y + Suunta.riviSiirtymat[i];
                int x = nykyinenRuutu.x + Suunta.sarakeSiirtymat[i];

                if (!ruutuKelpaa(y, x) || vierailtu[y][x]) {
                    continue;
                }

                vieraile(y, x);
                edeltajat[y][x] = nykyinenRuutu;

                if (loppuSaavutettu(y, x)) {
                    return muodostaHakutulos();
                }

                jono.add(new Ruutu(y, x));
            }
        }

        return new Hakutulos(false, "Reitti ei mahdollinen.", ruutujaTarkasteltu, vierailtu);
    }
}
