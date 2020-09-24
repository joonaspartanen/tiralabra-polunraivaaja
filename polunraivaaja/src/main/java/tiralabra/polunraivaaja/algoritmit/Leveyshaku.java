package tiralabra.polunraivaaja.algoritmit;

import java.util.ArrayDeque;
import java.util.Queue;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.apurakenteet.Suunta;

/**
 * Leveyshakualgoritmin (BFS) toteutus. Hakee kartalta lyhimmän reitin kahden pisteen välillä.
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
     * @param alku Haettavan reitin alkupiste.
     * @param loppu Haettavan reitin loppupiste.
     * @return Palauttaa haun tulosta kuvaavan Hakutulos-olion.
     */
    @Override
    public Hakutulos etsiReitti(Ruutu alku, Ruutu loppu) {

        ruutujaTarkasteltu = 0;

        if (!reitinPaatVapaat(alku, loppu)) {
            tulos = new Hakutulos(false, "Alku- tai loppupiste ei kelpaa.", ruutujaTarkasteltu, vierailtu);
            return tulos;
        }

        this.alku = alku;
        this.loppu = loppu;

        Queue<Ruutu> jono = new ArrayDeque<>();
        edeltajat = new Ruutu[korkeus][leveys];
        vierailtu = new boolean[korkeus][leveys];

        jono.add(alku);
        vieraile(alku.getRivi(), alku.getSarake());

        while (!jono.isEmpty()) {
            Ruutu nykyinenRuutu = jono.remove();

            for (int i = 0; i < 4; i++) {
                int rivi = nykyinenRuutu.getRivi() + Suunta.riviSiirtymat[i];
                int sarake = nykyinenRuutu.getSarake() + Suunta.sarakeSiirtymat[i];

                if (ruutuKelpaa(rivi, sarake) && !vierailtu[rivi][sarake]) {
                    vieraile(rivi, sarake);
                    edeltajat[rivi][sarake] = nykyinenRuutu;

                    if (loppuSaavutettu(rivi, sarake)) {
                        muodostaReitti();
                        tulos = new Hakutulos(true, "Reitti löytyi.", ruutujaTarkasteltu, reitti, vierailtu);
                        return tulos;
                    }

                    jono.add(new Ruutu(rivi, sarake));
                }
            }
        }

        tulos = new Hakutulos(false, "Reitti ei mahdollinen.", ruutujaTarkasteltu, vierailtu);
        return tulos;
    }

    private void vieraile(int rivi, int sarake) {
        vierailtu[rivi][sarake] = true;
        ruutujaTarkasteltu++;
    }

}
