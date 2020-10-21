package tiralabra.polunraivaaja.algoritmit;

import tiralabra.polunraivaaja.mallit.Kartta;
import tiralabra.polunraivaaja.algoritmit.heuristiikka.DiagonaaliEtaisyys;
import tiralabra.polunraivaaja.algoritmit.heuristiikka.Heuristiikka;
import tiralabra.polunraivaaja.algoritmit.heuristiikka.ManhattanEtaisyys;
import tiralabra.polunraivaaja.mallit.Hakutulos;
import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuLista;
import tiralabra.polunraivaaja.tyokalut.Taulukonkasittelija;
import tiralabra.polunraivaaja.mallit.Suunta;

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
    protected final Kartta kartta;

    /**
     * Kohteena olevan kartan korkeus.
     */
    protected final int korkeus;

    /**
     * Kohteena olevan kartan leveys.
     */
    protected final int leveys;

    /**
     * Pitää kirjaa siitä, minkä ruudun kautta kulloinkin tarkasteltavaan ruutuun on
     * saavuttu.
     */
    protected final Ruutu[][] edeltajat;

    /**
     * Pitää kirjaa jo vierailluista ruuduista.
     */
    protected final boolean[][] vierailtu;

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
        this.heuristiikka = salliDiagonaalisiirtymat ? new DiagonaaliEtaisyys() : new ManhattanEtaisyys();
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

    /**
     * Muodostaa haun lopputulosta kuvaavan Hakutulos-olion.
     *
     * @return Haun lopputulosta kuvaava Hakutulos-olio.
     */
    protected Hakutulos muodostaHakutulos() {
        final long loppuAika = System.nanoTime();
        final long haunKesto = loppuAika - alkuAika;

        muodostaReitti();
        double reitinPituus = !salliDiagonaalisiirtymat ? reitti.haePituus() - 1 : etaisyysAlusta[loppu.y][loppu.x];

        return new Hakutulos(true, "Reitti löytyi.", ruutujaTarkasteltu, reitti, vierailtu, reitinPituus, haunKesto);
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

    /**
     * Merkitsee ruudun vierailluksi ja päivittää ruutujaTarkasteltu-laskurin.
     */
    protected void vieraile(int rivi, int sarake) {
        vierailtu[rivi][sarake] = true;
        ruutujaTarkasteltu++;
    }

    /**
     * Tarkistaa, sijaitsevatko kaksi ruutua samalla rivillä tai sarakkeessa.
     * @param a Ensimmäinen verrattava ruutu.
     * @param b Toinen verrattava ruutu.
     * @return true, jos sijaitsevat samalla rivillä tai sarakkeessa; muuten false.
     */
    protected boolean samaRiviTaiSarake(Ruutu a, Ruutu b) {
        return a.y == b.y || a.x == b.x;
    }

    protected RuutuLista haeVapaatNaapurit(Ruutu ruutu, boolean salliDiagonaalisiirtymat) {
        final RuutuLista naapurit = new RuutuLista(8);

        final int y = ruutu.y;
        final int x = ruutu.x;

        for (Suunta suunta : Suunta.values()) {
            if (!salliDiagonaalisiirtymat && suunta.isDiagonaalinen()) {
                continue;
            }

            final int uusiY = y + suunta.getDY();
            final int uusiX = x + suunta.getDX();

            if (!ruutuKelpaa(uusiY, uusiX)) {
                continue;
            }

            // Varmistetaan ettei haku kulje kulmien välistä.
            if (suunta.isDiagonaalinen() && !ruutuKelpaa(uusiY, x) && !ruutuKelpaa(y, uusiX)) {
                continue;
            }

            naapurit.lisaaRuutu(new Ruutu(uusiY, uusiX));
        }

        return naapurit;
    }

    protected void alustaEtaisyysTaulukot(Ruutu alku) {
        etaisyysAlusta = new double[korkeus][leveys];
        etaisyysarvioLoppuun = new double[korkeus][leveys];

        Taulukonkasittelija.alustaLiukulukuTaulukko(etaisyysAlusta);
        Taulukonkasittelija.alustaLiukulukuTaulukko(etaisyysarvioLoppuun);

        etaisyysAlusta[alku.y][alku.x] = 0;
        etaisyysarvioLoppuun[alku.y][alku.x] = heuristiikka.laskeEtaisyys(alku, loppu);
    }

    protected void alustaVierailtuTaulukko() {
        Taulukonkasittelija.alustaBooleanTaulukko(vierailtu);
    }

    protected void alustaEdeltajatTaulukko() {
        Taulukonkasittelija.alustaRuutuTaulukko(edeltajat);
    }

}
