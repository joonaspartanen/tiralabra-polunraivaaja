package tiralabra.polunraivaaja.algoritmit;

import java.util.ArrayDeque;
import java.util.Queue;

import tiralabra.polunraivaaja.kartta.Kartta;
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
    public boolean etsiReitti(Koordinaatti alku, Koordinaatti loppu) {

        if (!reitinPaatVapaat(alku, loppu)) {
            System.out.println("Alku- tai loppupiste ei kelpaa.");
            return false;
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

            if (loppuSaavutettu(nykyinenRuutu.getRivi(), nykyinenRuutu.getSarake())) {
                muodostaReitti();
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int rivi = nykyinenRuutu.getRivi() + Suunnat.riviSiirtymat[i];
                int sarake = nykyinenRuutu.getSarake() + Suunnat.sarakeSiirtymat[i];

                if (ruutuKelpaa(rivi, sarake) && !vierailtu[rivi][sarake]) {
                    vieraile(rivi, sarake);
                    jono.add(new Koordinaatti(rivi, sarake));
                    edeltajat[rivi][sarake] = nykyinenRuutu;
                }
            }
        }
        System.out.println("Reitti ei mahdollinen.");
        return false;
    }

    private void vieraile(int rivi, int sarake) {
        vierailtu[rivi][sarake] = true;
    }

}
