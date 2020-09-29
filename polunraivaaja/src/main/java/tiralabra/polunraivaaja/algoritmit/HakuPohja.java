package tiralabra.polunraivaaja.algoritmit;

import java.util.ArrayList;
import java.util.List;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.algoritmit.heuristiikka.DiagonaaliEtaisyys;
import tiralabra.polunraivaaja.algoritmit.heuristiikka.Heuristiikka;
import tiralabra.polunraivaaja.algoritmit.heuristiikka.ManhattanEtaisyys;
import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.apurakenteet.Suunta;

/**
 * Abstrakti luokka, joka sisältää useille reitinhakualgoritmeille yhteiset
 * metodit ja oliomuuttujat.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public abstract class HakuPohja implements Haku {

    /**
     * Reittihaun kohteena oleva suorakulmion muotoinen kartta.
     */
    protected Kartta kartta;

    /**
     * Kohteena olevan kartan korkeus.
     */
    protected int korkeus;

    /**
     * Kohteena olevan kartan leveys.
     */
    protected int leveys;

    /**
     * Pitää kirjaa siitä, minkä ruudun kautta kulloinkin tarkasteltavaan ruutuun on
     * saavuttu.
     */
    protected Ruutu[][] edeltajat;

    /**
     * Pitää kirjaa jo vierailluista ruuduista.
     */
    protected boolean[][] vierailtu;

    /**
     * Ruutu, josta haettava reitti alkaa.
     */
    protected Ruutu alku;

    /**
     * Ruutu, johon haettava reitti päättyy.
     */
    protected Ruutu loppu;

    /**
     * Määrittää, saako kartalla kulkea vinottain ruudusta toiseen.
     */
    protected boolean salliDiagonaalisiirtymat;

    /**
     * Lista ruutuja, jotka muodostavat haun löytämän lyhimmän reitin.
     */
    protected List<Ruutu> reitti;

    /**
     * Laskuri haun käsittelemistä ruuduista.
     */
    protected int ruutujaTarkasteltu;

    /**
     * Reittihaun tuloksen käärivä Hakutulos-olio.
     */
    protected Hakutulos tulos;

    /**
     * A*- ja JPS-algoritmien käyttämä heuristinen funktio.
     */
    protected Heuristiikka heuristiikka;

    /**
     *
     * @param kartta Han kohteena oleva kartta.
     */
    public HakuPohja(Kartta kartta) {
        this.kartta = kartta;
        korkeus = kartta.getKorkeus();
        leveys = kartta.getLeveys();
        this.heuristiikka = new ManhattanEtaisyys();
    }

    @Override
    public void setKartta(Kartta kartta) {
        this.kartta = kartta;
    }

    /**
     * Tarkistaa, ovatko haun alku- ja loppuruudut kuljettavissa (eli ovat kartalla
     * eikä niissä ole esteitä).
     *
     * @param ruudut Tarkistettavat ruudut.
     * @return True, jos reitin kumpikin pää on vapaa. False, jos jompikumpi ei ole
     *         kuljettavissa.
     */
    protected boolean reitinPaatVapaat(Ruutu... ruudut) {
        for (Ruutu ruutu : ruudut) {
            if (!ruutuKelpaa(ruutu.getRivi(), ruutu.getSarake())) {
                return false;
            }
        }
        return true;
    }

    protected List<Ruutu> haeVapaatNaapurit(Ruutu ruutu, boolean salliDiagonaalisiirtymat) {
        List<Ruutu> naapurit = new ArrayList<>();

        int y = ruutu.getRivi();
        int x = ruutu.getSarake();

        for (Suunta suunta : Suunta.values()) {
            if (!salliDiagonaalisiirtymat && suunta.isDiagonaalinen()) {
                continue;
            }

            int uusiY = ruutu.getRivi() + suunta.getDY();
            int uusiX = ruutu.getSarake() + suunta.getDX();

            // Varmistetaan ettei haku kulje kulmien välistä.
            if (suunta.isDiagonaalinen() && !ruutuKelpaa(uusiY, x) && !ruutuKelpaa(y, uusiX)) {
                continue;
            }

            naapurit.add(new Ruutu(uusiY, uusiX));
        }

        return naapurit;
    }

    /**
     * Tarkistaa, onko ruutu kartalla ja kuljettavissa.
     *
     * @param rivi
     * @param sarake
     * @return
     */
    protected boolean ruutuKelpaa(int rivi, int sarake) {
        return ruutuKartalla(rivi, sarake) && kartta.ruutuVapaa(rivi, sarake);
    }

    /**
     * Tarkistaa, onko ruutu kartalla.
     *
     * @param rivi
     * @param sarake
     * @return
     */
    protected boolean ruutuKartalla(int rivi, int sarake) {
        return rivi >= 0 && rivi < korkeus && sarake >= 0 && sarake < leveys;
    }

    /**
     * Tarkistaa, vastaako jokin ruutu haun loppua.
     *
     * @param rivi   Tarkasteltavan ruudun rivi.
     * @param sarake Tarkasteltavan ruudun sarake.
     * @return
     */
    protected boolean loppuSaavutettu(int rivi, int sarake) {
        return rivi == loppu.getRivi() && sarake == loppu.getSarake();
    }

    /**
     * Muodostaa haun löytämän reitin seuraamalla edeltajat-taulukkoon tallennettuja
     * edeltäjä-ruutuja reitin alkupisteeseen saakka.
     */
    protected void muodostaReitti() {
        reitti = new ArrayList<>();
        reitti.add(loppu);

        Ruutu ruutu = edeltajat[loppu.getRivi()][loppu.getSarake()];
        reitti.add(ruutu);

        while (!(ruutu.getRivi() == alku.getRivi() && ruutu.getSarake() == alku.getSarake())) {
            ruutu = edeltajat[ruutu.getRivi()][ruutu.getSarake()];
            reitti.add(ruutu);
        }
    }

    /**
     * Palauttaa haun löytämää reittiä kuvaavan listan Ruutuja.
     *
     * @return Lista Ruutuja.
     */
    @Override
    public List<Ruutu> getReitti() {
        return reitti;
    }

    /**
     * Asettaa salliDiagonaalisiirtymät lipun. Jos sallitaan, niin heuristiikka
     * vaihdetaan asiaankuuluvaksi.
     *
     */
    public void setSalliDiagonaalisiirtymat(boolean salliDiagonaalisiirtymat) {
        this.salliDiagonaalisiirtymat = salliDiagonaalisiirtymat;
        this.heuristiikka = new DiagonaaliEtaisyys();
    }

    protected double laskeDiagonaaliEtaisyys(Ruutu lahto, Ruutu kohde) {

        int dy = Math.abs(kohde.getRivi() - lahto.getRivi());
        int dx = Math.abs(kohde.getSarake() - lahto.getSarake());

        return (dx + dy) + (Math.sqrt(2) - 2) * Math.min(dx, dy);
    }

    protected void alustaTaulukko(double[][] taulukko) {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                taulukko[i][j] = Double.MAX_VALUE;
            }
        }
    }
}
