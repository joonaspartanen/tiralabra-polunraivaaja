package tiralabra.polunraivaaja.algoritmit;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.algoritmit.heuristiikka.DiagonaaliEtaisyys;
import tiralabra.polunraivaaja.algoritmit.heuristiikka.Heuristiikka;
import tiralabra.polunraivaaja.algoritmit.heuristiikka.ManhattanEtaisyys;
import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.apurakenteet.RuutuLista;
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
    protected RuutuLista reitti;

    /**
     * Laskuri haun käsittelemistä ruuduista.
     */
    protected int ruutujaTarkasteltu;

    /**
     * Reittihaun alkamisen aikaleima.
     */
    protected long alkuAika;

    /**
     * A*- ja JPS-algoritmien käyttämä heuristinen funktio.
     */
    protected Heuristiikka heuristiikka;

    /**
     * Taulukko, jossa A* ja JPS pitävät kirjaa ruutujen etäisyydestä reitin alkuun.
     */
    protected double[][] etaisyysAlusta;

    /**
     * Taulukko, jossa A* ja JPS pitävät kirjaa heuristisen funktion laskemista
     * etäisyysarvioista kustakin ruudusta reitin loppuun.
     */
    protected double[][] etaisyysarvioLoppuun;

    /**
     *
     * @param kartta Han kohteena oleva kartta.
     */
    public HakuPohja(Kartta kartta) {
        this.kartta = kartta;
        korkeus = kartta.getKorkeus();
        leveys = kartta.getLeveys();
        edeltajat = new Ruutu[korkeus][leveys];
        vierailtu = new boolean[korkeus][leveys];
        this.heuristiikka = new ManhattanEtaisyys();
    }

    @Override
    public void setKartta(Kartta kartta) {
        this.kartta = kartta;
    }

    @Override
    public RuutuLista getReitti() {
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
            if (!ruutuKelpaa(ruutu.y, ruutu.x)) {
                return false;
            }
        }
        return true;
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
        return rivi == loppu.y && sarake == loppu.x;
    }

    protected Hakutulos muodostaHakutulos() {
        long loppuAika = System.nanoTime();
        long haunKesto = loppuAika - alkuAika;

        muodostaReitti();
        double reitinPituus = !salliDiagonaalisiirtymat ? reitti.getRuutuja() - 1 : etaisyysAlusta[loppu.y][loppu.x];

        return new Hakutulos(true, "Reitti löytyi.", ruutujaTarkasteltu, reitti, vierailtu, reitinPituus, haunKesto);
    }

    /**
     * Merkitsee ruudun vierailluksi ja päivittää ruutujaTarkasteltu-laskurin.
     */
    protected void vieraile(int rivi, int sarake) {
        vierailtu[rivi][sarake] = true;
        ruutujaTarkasteltu++;
    }

    protected RuutuLista haeVapaatNaapurit(Ruutu ruutu, boolean salliDiagonaalisiirtymat) {
        RuutuLista naapurit = new RuutuLista();

        int y = ruutu.y;
        int x = ruutu.x;

        for (Suunta suunta : Suunta.values()) {
            if (!salliDiagonaalisiirtymat && suunta.isDiagonaalinen()) {
                continue;
            }

            int uusiY = ruutu.y + suunta.getDY();
            int uusiX = ruutu.x + suunta.getDX();

            // Varmistetaan ettei haku kulje kulmien välistä.
            if (suunta.isDiagonaalinen() && !ruutuKelpaa(uusiY, x) && !ruutuKelpaa(y, uusiX)) {
                continue;
            }

            naapurit.lisaaRuutu(new Ruutu(uusiY, uusiX));
        }

        return naapurit;
    }

    /**
     * Muodostaa haun löytämän reitin seuraamalla edeltajat-taulukkoon tallennettuja
     * edeltäjä-ruutuja reitin alkupisteeseen saakka.
     */
    protected void muodostaReitti() {
        reitti = new RuutuLista();
        reitti.lisaaRuutu(loppu);

        Ruutu seuraava = edeltajat[loppu.y][loppu.x];
        reitti.lisaaRuutu(seuraava);

        while (seuraava != alku) {
            seuraava = edeltajat[seuraava.y][seuraava.x];
            reitti.lisaaRuutu(seuraava);
        }
    }

    protected double laskeDiagonaaliEtaisyys(Ruutu lahto, Ruutu kohde) {
        int dy = Math.abs(kohde.y - lahto.y);
        int dx = Math.abs(kohde.x - lahto.x);

        return (dx + dy) + (Math.sqrt(2) - 2) * Math.min(dx, dy);
    }

    protected void alustaEtaisyysTaulukot(Ruutu alku) {
        etaisyysAlusta = new double[korkeus][leveys];
        etaisyysarvioLoppuun = new double[korkeus][leveys];

        alustaTaulukko(etaisyysAlusta);
        alustaTaulukko(etaisyysarvioLoppuun);

        etaisyysAlusta[alku.y][alku.x] = 0;
        etaisyysarvioLoppuun[alku.y][alku.x] = heuristiikka.laskeEtaisyys(alku, loppu);

    }

    private void alustaTaulukko(double[][] taulukko) {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                taulukko[i][j] = Double.MAX_VALUE;
            }
        }
    }
}
