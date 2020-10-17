package tiralabra.polunraivaaja.algoritmit;

import tiralabra.polunraivaaja.mallit.Hakutulos;
import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuKeko;
import tiralabra.polunraivaaja.tietorakenteet.RuutuLista;
import tiralabra.polunraivaaja.tyokalut.RuutuKomparaattori;
import tiralabra.polunraivaaja.mallit.Suunta;
import tiralabra.polunraivaaja.kartta.Kartta;

/**
 * JPS-algoritmin toteutus, joka kaipaa vielä refaktorointia.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class JPS extends HakuPohja {

    public JPS(Kartta kartta) {
        super(kartta);
        setSalliDiagonaalisiirtymat(true);
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

        alustaEdeltajatTaulukko();
        alustaVierailtuTaulukko();
        alustaEtaisyysTaulukot(alku);

        RuutuKomparaattori komparaattori = new RuutuKomparaattori(etaisyysarvioLoppuun);
        RuutuKeko keko = new RuutuKeko(komparaattori);

        keko.lisaaRuutu(alku);

        vieraile(alku.y, alku.x);

        while (!keko.onTyhja()) {
            Ruutu nykyinen = keko.otaKeosta();

            if (loppuSaavutettu(nykyinen.y, nykyinen.x)) {
                return muodostaHakutulos();
            }

            RuutuLista seuraajat = etsiSeuraajat(nykyinen);

            for (int i = 0; i < seuraajat.haePituus(); i++) {
                Ruutu seuraaja = seuraajat.haeRuutuIndeksissa(i);

                double etaisyysTahan = heuristiikka.laskeEtaisyys(nykyinen, seuraaja);

                double uusiEtaisyys = etaisyysAlusta[nykyinen.y][nykyinen.x] + etaisyysTahan;

                if (uusiEtaisyys < etaisyysAlusta[seuraaja.y][seuraaja.x] || !vierailtu[seuraaja.y][seuraaja.x]) {
                    etaisyysAlusta[seuraaja.y][seuraaja.x] = uusiEtaisyys;
                    etaisyysarvioLoppuun[seuraaja.y][seuraaja.x] = uusiEtaisyys
                            + heuristiikka.laskeEtaisyys(seuraaja, loppu);

                    edeltajat[seuraaja.y][seuraaja.x] = nykyinen;
                    vieraile(seuraaja.y, seuraaja.x);

                    keko.lisaaRuutu(new Ruutu(seuraaja.y, seuraaja.x));
                    ruutujaTarkasteltu++;
                }
            }
        }
        return new Hakutulos(false, "Reitti ei mahdollinen.", ruutujaTarkasteltu, vierailtu);
    }

    private RuutuLista etsiSeuraajat(Ruutu ruutu) {

        RuutuLista seuraajat = new RuutuLista();
        RuutuLista naapurit = haeNaapurit(ruutu);

        for (int i = 0; i < naapurit.haePituus(); i++) {
            Ruutu naapuri = naapurit.haeRuutuIndeksissa(i);
            Ruutu hyppypiste = hyppaa(naapuri, ruutu);

            if (hyppypiste != null && !vierailtu[hyppypiste.y][hyppypiste.x]) {
                seuraajat.lisaaRuutu(hyppypiste);
            }
        }
        return seuraajat;
    }

    private RuutuLista haeNaapurit(Ruutu ruutu) {
        int y = ruutu.y;
        int x = ruutu.x;
        Ruutu edeltaja = edeltajat[y][x];

        if (edeltaja == null) {
            return haeVapaatNaapurit(ruutu, salliDiagonaalisiirtymat);
        }

        RuutuLista naapurit = new RuutuLista();

        Suunta suunta = Suunta.laskeSuunta(edeltaja, ruutu);
        int dy = suunta.getDY();
        int dx = suunta.getDX();

        if (suunta.isDiagonaalinen()) {
            lisaaRuutuJosKelpaa(naapurit, y + dy, x);
            lisaaRuutuJosKelpaa(naapurit, y, x + dx);
            lisaaRuutuJosKelpaa(naapurit, y + dy, x + dx);

            if (!ruutuKelpaa(y, x - dx)) {
                lisaaRuutuJosKelpaa(naapurit, y + dy, x - dx);
            }
            if (!ruutuKelpaa(y - dy, x)) {
                lisaaRuutuJosKelpaa(naapurit, y - dy, x + dx);
            }
        } else if (dx == 0) {
            if (ruutuKelpaa(y + dy, x)) {
                naapurit.lisaaRuutu(new Ruutu(y + dy, x));

                if (!ruutuKelpaa(y, x + 1)) {
                    lisaaRuutuJosKelpaa(naapurit, y + dy, x + 1);
                }
                if (!ruutuKelpaa(y, x - 1)) {
                    lisaaRuutuJosKelpaa(naapurit, y + dy, x - 1);
                }
            }
        } else {
            if (ruutuKelpaa(y, x + dx)) {
                naapurit.lisaaRuutu(new Ruutu(y, x + dx));

                if (!ruutuKelpaa(y + 1, x)) {
                    lisaaRuutuJosKelpaa(naapurit, y + 1, x + dx);
                }
                if (!ruutuKelpaa(y - 1, x)) {
                    lisaaRuutuJosKelpaa(naapurit, y - 1, x + dx);
                }
            }
        }
        return naapurit;
    }

    private void lisaaRuutuJosKelpaa(RuutuLista lista, int y, int x) {
        if (ruutuKelpaa(y, x)) {
            lista.lisaaRuutu(new Ruutu(y, x));
        }
    }

    private Ruutu hyppaa(Ruutu kohde, Ruutu lahto) {
        int y = kohde.y;
        int x = kohde.x;

        Suunta suunta = Suunta.laskeSuunta(lahto, kohde);
        int dx = suunta.getDX();
        int dy = suunta.getDY();

        if (!ruutuKelpaa(y, x)) {
            return null;
        }

        if (loppuSaavutettu(y, x)) {
            return kohde;
        }

        if (suunta.isDiagonaalinen()) {
            if ((ruutuKelpaa(y + dy, x - dx) && !ruutuKelpaa(y, x - dx))
                    || (ruutuKelpaa(y - dy, x + dx) && !ruutuKelpaa(y - dy, x))) {
                return kohde;
            }
        } else {

            if (dx != 0) {
                if ((ruutuKelpaa(y + 1, x + dx) && !ruutuKelpaa(y + 1, x))
                        || (ruutuKelpaa(y - 1, x + dx) && !ruutuKelpaa(y - 1, x))) {
                    return kohde;
                }
            } else {
                if ((ruutuKelpaa(y + dy, x + 1) && !ruutuKelpaa(y, x + 1))
                        || (ruutuKelpaa(y + dy, x - 1) && !ruutuKelpaa(y, x - 1))) {
                    return kohde;
                }
            }
        }
        if (suunta.isDiagonaalinen()) {
            if (hyppaa(new Ruutu(y, x + dx), kohde) != null) {
                return kohde;
            }
            if (hyppaa(new Ruutu(y + dy, x), kohde) != null) {
                return kohde;
            }
        }

        if (ruutuKelpaa(y, x + dx) || ruutuKelpaa(y + dy, x)) {
            return hyppaa(new Ruutu(y + dy, x + dx), kohde);
        }
        return null;

    }

    @Override
    public void muodostaReitti() {
        super.muodostaReitti();

        RuutuLista taysiReitti = new RuutuLista();

        for (int i = 0; i < reitti.haePituus() - 1; i++) {
            Ruutu lahto = reitti.haeRuutuIndeksissa(i);
            Ruutu kohde = reitti.haeRuutuIndeksissa(i + 1);

            taysiReitti.lisaaRuutu(lahto);

            Suunta suunta = Suunta.laskeSuunta(lahto, kohde);

            while (true) {
                lahto = Ruutu.haeSeuraavaRuutu(lahto, suunta);
                int lahtoY = lahto.y;
                int lahtoX = lahto.x;
                int kohdeY = kohde.y;
                int kohdeX = kohde.x;
                if (lahtoY == kohdeY && lahtoX == kohdeX) {
                    break;
                }
                taysiReitti.lisaaRuutu(lahto);
            }
        }
        taysiReitti.lisaaRuutu(alku);
        reitti = taysiReitti;
    }
}
