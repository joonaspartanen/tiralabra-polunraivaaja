package tiralabra.polunraivaaja.algoritmit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;

/**
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Leveyshaku implements Haku {

    private Kartta kartta;
    private int korkeus;
    private int leveys;
    private Koordinaatti[][] edeltajat;
    private boolean[][] vierailtu;
    private Koordinaatti alku;
    private Koordinaatti loppu;

    List<Koordinaatti> reitti;

    private int[] rivisuunnat = {-1, 0, 0, 1, -1, -1, 1, 1};
    private int[] sarakesuunnat = {0, -1, 1, 0, -1, 1, -1, 1};

    /**
     *
     * @param kartta Reittihaun kohteena oleva kartta.
     */
    public Leveyshaku(Kartta kartta) {
        this.kartta = kartta;
    }

    @Override
    public void setKartta(Kartta kartta) {
        this.kartta = kartta;
    }

    /**
     * Etsii jonkin lyhimmistä reiteistä parametreina saatujen alku- ja
     * loppupisteiden välillä.
     *
     * @param alku Haettavan reitin alkupiste.
     * @param loppu Haettavan reitin loppupiste.
     * @return True, jos reitti löytyi; false, jos reittiä ei löytynyt tai ei
     * voitu hakea.
     */
    @Override
    public boolean etsiReitti(Koordinaatti alku, Koordinaatti loppu) {

        korkeus = kartta.getKorkeus();
        leveys = kartta.getLeveys();

        if (!reitinPaatVapaat(alku, loppu)) {
            System.out.println("Alku- tai loppupiste ei kelpaa.");
            return false;
        }

        this.alku = alku;
        this.loppu = loppu;

        Queue<Koordinaatti> jono = new ArrayDeque<>();
        vierailtu = new boolean[korkeus][leveys];
        edeltajat = new Koordinaatti[korkeus][leveys];

        jono.add(alku);
        vieraile(alku.getRivi(), alku.getSarake());

        while (!jono.isEmpty()) {

            Koordinaatti ruutu = jono.remove();

            if (loppuSaavutettu(ruutu.getRivi(), ruutu.getSarake())) {
                muodostaReitti();
                return true;
            }

            for (int i = 0; i < 8; i++) {
                int rivi = ruutu.getRivi() + rivisuunnat[i];
                int sarake = ruutu.getSarake() + sarakesuunnat[i];

                if (ruutuKelpaa(rivi, sarake) && !vierailtu[rivi][sarake]) {
                    vieraile(rivi, sarake);
                    jono.add(new Koordinaatti(rivi, sarake));
                    edeltajat[rivi][sarake] = ruutu;
                }
            }
        }
        System.out.println("Reitti ei mahdollinen.");
        return false;
    }

    private boolean reitinPaatVapaat(Koordinaatti... koordinaatit) {
        for (Koordinaatti koordinaatti : koordinaatit) {
            if (!ruutuKelpaa(koordinaatti.getRivi(), koordinaatti.getSarake())) {
                return false;
            }
        }
        return true;
    }

    private boolean ruutuKelpaa(int rivi, int sarake) {
        return ruutuKartalla(rivi, sarake) && kartta.ruutuVapaa(rivi, sarake);
    }

    private boolean ruutuKartalla(int rivi, int sarake) {
        return rivi >= 0 && rivi < korkeus && sarake >= 0 && sarake < leveys;
    }

    private void vieraile(int rivi, int sarake) {
        vierailtu[rivi][sarake] = true;
    }

    private boolean loppuSaavutettu(int rivi, int sarake) {
        return rivi == loppu.getRivi() && sarake == loppu.getSarake();
    }

    private void muodostaReitti() {
        reitti = new ArrayList<>();
        reitti.add(loppu);

        Koordinaatti ruutu = edeltajat[loppu.getRivi()][loppu.getSarake()];
        reitti.add(ruutu);

        while (!(ruutu.getRivi() == alku.getRivi() && ruutu.getSarake() == alku.getSarake())) {
            ruutu = edeltajat[ruutu.getRivi()][ruutu.getSarake()];
            reitti.add(ruutu);
        }
    }

    /**
     * Palauttaa haun löytämää reittiä kuvaavan listan Koordinaatteja.
     *
     * @return Lista Koordinaatteja.
     */
    @Override
    public List<Koordinaatti> getReitti() {
        return reitti;
    }
}
