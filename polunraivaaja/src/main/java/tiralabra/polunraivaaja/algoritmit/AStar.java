package tiralabra.polunraivaaja.algoritmit;

import java.util.PriorityQueue;
import java.util.Queue;

import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.apurakenteet.RuutuLista;
import tiralabra.polunraivaaja.apurakenteet.Suunta;
import tiralabra.polunraivaaja.kartta.Kartta;

/**
 * A*-algoritmin toteutus. Hakee kartalta lyhimmän reitin kahden pisteen välillä
 * käyttäen apuna heuristista funktiota, joka arvioi tarkasteltavien solmujen
 * etäisyyttä reitin loppuun.
 * 
 * TODO: Tutki miksi löytää joskus hieman pidemmän reitin kuin JPS.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class AStar extends HakuPohja {

    public AStar(Kartta kartta) {
        super(kartta);
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

        alustaEtaisyysTaulukot(alku);

        Queue<Ruutu> jono = new PriorityQueue<>(
                (a, b) -> etaisyysarvioLoppuun[a.y][a.x] - etaisyysarvioLoppuun[b.y][b.x] < 0 ? -1 : 1);

        jono.add(alku);

        while (!jono.isEmpty()) {
            Ruutu nykyinen = jono.remove();

            if (loppuSaavutettu(nykyinen.y, nykyinen.x)) {
                return muodostaHakutulos();
            }

            RuutuLista naapurit = haeVapaatNaapurit(nykyinen, salliDiagonaalisiirtymat);

            for (int i = 0; i < naapurit.getRuutuja(); i++) {
                Ruutu naapuri = naapurit.haeRuutuIndeksissa(i);

                if (!ruutuKelpaa(naapuri.y, naapuri.x) || vierailtu[naapuri.y][naapuri.x]) {
                    continue;
                }

                double etaisyysTahan = Suunta.laskeSuunta(nykyinen, naapuri).isDiagonaalinen() ? Math.sqrt(2) : 1;

                double uusiEtaisyys = etaisyysAlusta[nykyinen.y][nykyinen.x] + etaisyysTahan;

                if (uusiEtaisyys < etaisyysAlusta[naapuri.y][naapuri.x]) {
                    etaisyysAlusta[naapuri.y][naapuri.x] = uusiEtaisyys;
                    etaisyysarvioLoppuun[naapuri.y][naapuri.x] = uusiEtaisyys
                            + heuristiikka.laskeEtaisyys(naapuri, loppu);

                    vieraile(naapuri.y, naapuri.x);
                    edeltajat[naapuri.y][naapuri.x] = nykyinen;

                    jono.add(naapuri);
                }
            }
        }
        return new Hakutulos(false, "Reitti ei mahdollinen.", ruutujaTarkasteltu, vierailtu);
    }
}
