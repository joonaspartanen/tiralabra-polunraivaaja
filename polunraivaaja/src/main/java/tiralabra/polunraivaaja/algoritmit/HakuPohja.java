package tiralabra.polunraivaaja.algoritmit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;

/**
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public abstract class HakuPohja implements Haku {

    protected Kartta kartta;
    protected int korkeus;
    protected int leveys;
    protected Koordinaatti[][] edeltajat;
    protected boolean[][] vierailtu;
    protected Koordinaatti alku;
    protected Koordinaatti loppu;
    protected List<Koordinaatti> reitti;

    public HakuPohja(Kartta kartta) {
        this.kartta = kartta;
        korkeus = kartta.getKorkeus();
        leveys = kartta.getLeveys();
    }

    @Override
    public void setKartta(Kartta kartta) {
        this.kartta = kartta;
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
    public abstract boolean etsiReitti(Koordinaatti alku, Koordinaatti loppu);

    protected boolean reitinPaatVapaat(Koordinaatti... koordinaatit) {
        for (Koordinaatti koordinaatti : koordinaatit) {
            if (!ruutuKelpaa(koordinaatti.getRivi(), koordinaatti.getSarake())) {
                return false;
            }
        }
        return true;
    }

    protected boolean ruutuKelpaa(int rivi, int sarake) {
        return ruutuKartalla(rivi, sarake) && kartta.ruutuVapaa(rivi, sarake);
    }

    protected boolean ruutuKartalla(int rivi, int sarake) {
        return rivi >= 0 && rivi < korkeus && sarake >= 0 && sarake < leveys;
    }

    protected boolean loppuSaavutettu(int rivi, int sarake) {
        return rivi == loppu.getRivi() && sarake == loppu.getSarake();
    }

    protected void muodostaReitti() {
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
