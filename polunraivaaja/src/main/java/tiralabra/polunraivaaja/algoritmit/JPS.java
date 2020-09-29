package tiralabra.polunraivaaja.algoritmit;

import java.util.PriorityQueue;
import java.util.Queue;

import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.apurakenteet.RuutuLista;
import tiralabra.polunraivaaja.apurakenteet.Suunta;
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
        long alkuAika = System.nanoTime();

        ruutujaTarkasteltu = 0;

        if (!reitinPaatVapaat(alku, loppu)) {
            tulos = new Hakutulos(false, "Alku- tai loppupiste ei kelpaa.", ruutujaTarkasteltu, vierailtu);
            return tulos;
        }

        this.alku = alku;
        this.loppu = loppu;

        double[][] etaisyysAlusta = new double[korkeus][leveys];
        double[][] etaisyysarvioLoppuun = new double[korkeus][leveys];

        alustaTaulukko(etaisyysAlusta);
        alustaTaulukko(etaisyysarvioLoppuun);

        Queue<Ruutu> jono = new PriorityQueue<>((a, b) -> etaisyysarvioLoppuun[a.getRivi()][a.getSarake()]
                - etaisyysarvioLoppuun[b.getRivi()][b.getSarake()] < 0 ? -1 : 1);

        edeltajat = new Ruutu[korkeus][leveys];
        vierailtu = new boolean[korkeus][leveys];

        int alkuY = alku.getRivi();
        int alkuX = alku.getSarake();

        jono.add(alku);
        etaisyysAlusta[alkuY][alkuX] = 0;
        etaisyysarvioLoppuun[alkuY][alkuX] = heuristiikka.laskeEtaisyys(alku, loppu);
        vierailtu[alkuY][alkuX] = true;

        while (!jono.isEmpty()) {
            Ruutu nykyinenRuutu = jono.remove();

            int nykyinenY = nykyinenRuutu.getRivi();
            int nykyinenX = nykyinenRuutu.getSarake();

            if (loppuSaavutettu(nykyinenY, nykyinenX)) {
                muodostaReitti();
                double reitinPituus = etaisyysAlusta[nykyinenY][nykyinenX];

                long loppuAika = System.nanoTime();
                long haunKesto = loppuAika - alkuAika;

                tulos = new Hakutulos(true, "Reitti löytyi.", ruutujaTarkasteltu, reitti, vierailtu, reitinPituus,
                        haunKesto);
                return tulos;
            }

            RuutuLista seuraajat = etsiSeuraajat(nykyinenRuutu);

            for (int i = 0; i < seuraajat.getRuutuja(); i++) {
                Ruutu seuraaja = seuraajat.haeRuutuIndeksissa(i);

                int uusiY = seuraaja.getRivi();
                int uusiX = seuraaja.getSarake();

                double etaisyysTahan = heuristiikka.laskeEtaisyys(nykyinenRuutu, seuraaja);

                double uusiEtaisyys = etaisyysAlusta[nykyinenY][nykyinenX] + etaisyysTahan;

                if (uusiEtaisyys < etaisyysAlusta[uusiY][uusiX] || !vierailtu[uusiY][uusiX]) {
                    etaisyysAlusta[uusiY][uusiX] = uusiEtaisyys;
                    etaisyysarvioLoppuun[uusiY][uusiX] = uusiEtaisyys + heuristiikka.laskeEtaisyys(seuraaja, loppu);
                    edeltajat[uusiY][uusiX] = nykyinenRuutu;
                    vierailtu[uusiY][uusiX] = true;

                    jono.add(new Ruutu(uusiY, uusiX));
                    ruutujaTarkasteltu++;
                }
            }
        }
        tulos = new Hakutulos(false, "Reitti ei mahdollinen.", ruutujaTarkasteltu, vierailtu);
        return tulos;
    }

    private RuutuLista etsiSeuraajat(Ruutu ruutu) {

        RuutuLista seuraajat = new RuutuLista();
        RuutuLista naapurit = haeNaapurit(ruutu);

        for (int i = 0; i < naapurit.getRuutuja(); i++) {
            Ruutu naapuri = naapurit.haeRuutuIndeksissa(i);
            Ruutu hyppypiste = hyppaa(naapuri, ruutu);

            if (hyppypiste != null && !vierailtu[hyppypiste.getRivi()][hyppypiste.getSarake()]) {
                seuraajat.lisaaRuutu(hyppypiste);
            }
        }
        return seuraajat;
    }

    private RuutuLista haeNaapurit(Ruutu ruutu) {
        int y = ruutu.getRivi();
        int x = ruutu.getSarake();
        Ruutu edeltaja = edeltajat[y][x];

        if (edeltaja == null) {
            return haeVapaatNaapurit(ruutu, salliDiagonaalisiirtymat);
        }

        RuutuLista naapurit = new RuutuLista();

        Suunta suunta = Suunta.laskeSuunta(edeltaja, ruutu);
        int dy = suunta.getDY();
        int dx = suunta.getDX();

        // TODO: Poista tuplatarkistukset.
        if (dx != 0 && dy != 0) {
            if (ruutuKelpaa(y + dy, x)) {
                lisaaRuutuJosKelpaa(naapurit, y + dy, x);
            }
            if (ruutuKelpaa(y, x + dx)) {
                lisaaRuutuJosKelpaa(naapurit, y, x + dx);
            }
            if (ruutuKelpaa(y + dy, x + dx)) {
                lisaaRuutuJosKelpaa(naapurit, y + dy, x + dx);
            }
            if (!ruutuKelpaa(y, x - dx)) {
                lisaaRuutuJosKelpaa(naapurit, y + dy, x - dx);
            }
            if (!ruutuKelpaa(y - dy, x)) {
                lisaaRuutuJosKelpaa(naapurit, y - dy, x + dx);
            }
        } else if (dx == 0) {
            if (ruutuKelpaa(y + dy, x)) {
                lisaaRuutuJosKelpaa(naapurit, y + dy, x);
                if (!ruutuKelpaa(y, x + 1)) {
                    lisaaRuutuJosKelpaa(naapurit, y + dy, x + 1);
                }
                if (!ruutuKelpaa(y, x - 1)) {
                    lisaaRuutuJosKelpaa(naapurit, y + dy, x - 1);
                }
            }
        } else {
            if (ruutuKelpaa(y, x + dx)) {
                lisaaRuutuJosKelpaa(naapurit, y, x + dx);
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
        int y = kohde.getRivi();
        int x = kohde.getSarake();

        Suunta suunta = Suunta.laskeSuunta(lahto, kohde);
        int dx = suunta.getDX();
        int dy = suunta.getDY();

        if (!ruutuKelpaa(y, x)) {
            return null;
        }

        if (loppuSaavutettu(y, x)) {
            return kohde;
        }

        if (dx != 0 && dy != 0) {
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
        if (dx != 0 && dy != 0) {
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

        for (int i = 0; i < reitti.getRuutuja() - 1; i++) {
            Ruutu lahto = reitti.haeRuutuIndeksissa(i);
            Ruutu kohde = reitti.haeRuutuIndeksissa(i + 1);

            taysiReitti.lisaaRuutu(lahto);

            Suunta suunta = Suunta.laskeSuunta(lahto, kohde);

            while (true) {
                lahto = Ruutu.haeSeuraavaRuutu(lahto, suunta);
                int lahtoY = lahto.getRivi();
                int lahtoX = lahto.getSarake();
                int kohdeY = kohde.getRivi();
                int kohdeX = kohde.getSarake();
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
