package tiralabra.polunraivaaja.algoritmit;

import tiralabra.polunraivaaja.mallit.Hakutulos;
import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuKeko;
import tiralabra.polunraivaaja.tietorakenteet.RuutuLista;
import tiralabra.polunraivaaja.tyokalut.Laskin;
import tiralabra.polunraivaaja.tyokalut.RuutuKomparaattori;
import tiralabra.polunraivaaja.kartta.Kartta;

/**
 * A*-algoritmin toteutus. Hakee kartalta lyhimmän reitin kahden pisteen välillä
 * käyttäen apuna heuristista funktiota, joka arvioi tarkasteltavien solmujen
 * etäisyyttä reitin loppuun.
 * 
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Dijkstra extends HakuPohja {

    public Dijkstra(Kartta kartta) {
        super(kartta);
        setSalliDiagonaalisiirtymat(false);
    }

    public Dijkstra(Kartta kartta, boolean salliDiagonaalisiirtymat) {
        super(kartta);
        setSalliDiagonaalisiirtymat(salliDiagonaalisiirtymat);
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

        RuutuKomparaattori komparaattori = new RuutuKomparaattori(etaisyysAlusta);
        RuutuKeko keko = new RuutuKeko(komparaattori);

        keko.lisaaRuutu(alku);
        vieraile(alku.y, alku.x);

        while (!keko.onTyhja()) {
            Ruutu nykyinen = keko.otaKeosta();

            if (loppuSaavutettu(nykyinen.y, nykyinen.x)) {
                return muodostaHakutulos();
            }

            RuutuLista naapurit = haeVapaatNaapurit(nykyinen, salliDiagonaalisiirtymat);

            for (int i = 0; i < naapurit.haePituus(); i++) {
                Ruutu naapuri = naapurit.haeRuutuIndeksissa(i);

                double etaisyysTahan = nykyinen.y == naapuri.y || nykyinen.x == naapuri.x ? 1 : Laskin.SQRT_2;

                double uusiEtaisyys = etaisyysAlusta[nykyinen.y][nykyinen.x] + etaisyysTahan;

                if (uusiEtaisyys < etaisyysAlusta[naapuri.y][naapuri.x]) {
                    etaisyysAlusta[naapuri.y][naapuri.x] = uusiEtaisyys;

                    vieraile(naapuri.y, naapuri.x);
                    edeltajat[naapuri.y][naapuri.x] = nykyinen;

                    keko.lisaaRuutu(naapuri);
                }
            }
        }
        return new Hakutulos(false, "Reitti ei mahdollinen.", ruutujaTarkasteltu, vierailtu);
    }
}
