package tiralabra.polunraivaaja.algoritmit;

import java.util.ArrayDeque;
import java.util.Queue;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;
import tiralabra.polunraivaaja.apurakenteet.Suunnat;

/**
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
     * @return True, jos reitti löytyi; false, jos reittiä ei löytynyt tai ei voitu
     *         hakea.
     */
    @Override
    public Hakutulos etsiReitti(Koordinaatti alku, Koordinaatti loppu) {

        solmujaTarkasteltu = 0;

        if (!reitinPaatVapaat(alku, loppu)) {
            tulos = new Hakutulos(false, "Alku- tai loppupiste ei kelpaa.", solmujaTarkasteltu, vierailtu);
            return tulos;
        }

        this.alku = alku;
        this.loppu = loppu;

        Queue<Koordinaatti> jono = new ArrayDeque<>();
        edeltajat = new Koordinaatti[korkeus][leveys];
        vierailtu = new boolean[korkeus][leveys];

        jono.add(alku);
        vieraile(alku.getRivi(), alku.getSarake());

        while (!jono.isEmpty()) {
            Koordinaatti nykyinenRuutu = jono.remove();

            for (int i = 0; i < 4; i++) {
                int rivi = nykyinenRuutu.getRivi() + Suunnat.riviSiirtymat[i];
                int sarake = nykyinenRuutu.getSarake() + Suunnat.sarakeSiirtymat[i];

                if (ruutuKelpaa(rivi, sarake) && !vierailtu[rivi][sarake]) {
                    vieraile(rivi, sarake);
                    edeltajat[rivi][sarake] = nykyinenRuutu;

                    if (loppuSaavutettu(rivi, sarake)) {
                        muodostaReitti();
                        tulos = new Hakutulos(true, "Reitti löytyi.", solmujaTarkasteltu, reitti, vierailtu);
                        return tulos;
                    }

                    jono.add(new Koordinaatti(rivi, sarake));
                }
            }
        }

        tulos = new Hakutulos(false, "Reitti ei mahdollinen.", solmujaTarkasteltu, vierailtu);
        return tulos;
    }

    private void vieraile(int rivi, int sarake) {
        vierailtu[rivi][sarake] = true;
        solmujaTarkasteltu++;
    }

    @Override
    public void setSalliDiagonaalisiirtymat(boolean salliDiagonaalisiirtymat) {
        // Leveyshaku ei toimi, jos diagonaalisiirtymät sallitaan.
    }

}
