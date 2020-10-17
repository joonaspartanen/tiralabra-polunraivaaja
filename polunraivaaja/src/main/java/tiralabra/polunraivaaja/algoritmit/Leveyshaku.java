package tiralabra.polunraivaaja.algoritmit;

import tiralabra.polunraivaaja.algoritmit.heuristiikka.ManhattanEtaisyys;
import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.mallit.Hakutulos;
import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuJono;
import tiralabra.polunraivaaja.mallit.Suunta;

/**
 * Leveyshakualgoritmin (BFS) toteutus. Hakee kartalta lyhimmän reitin kahden
 * pisteen välillä.
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
        this.heuristiikka = new ManhattanEtaisyys();
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

        RuutuJono jono = new RuutuJono();

        jono.lisaaJonoon(alku);
        vieraile(alku.y, alku.x);

        while (!jono.onTyhja()) {
            Ruutu nykyinenRuutu = jono.otaJonosta();

            for (int i = 0; i < 4; i++) {
                int y = nykyinenRuutu.y + Suunta.RIVISIIRTYMAT[i];
                int x = nykyinenRuutu.x + Suunta.SARAKESIIRTYMAT[i];

                if (!ruutuKelpaa(y, x) || vierailtu[y][x]) {
                    continue;
                }

                vieraile(y, x);
                edeltajat[y][x] = nykyinenRuutu;

                if (loppuSaavutettu(y, x)) {
                    return muodostaHakutulos();
                }

                jono.lisaaJonoon(new Ruutu(y, x));
            }
        }

        return new Hakutulos(false, "Reitti ei mahdollinen.", ruutujaTarkasteltu, vierailtu);
    }
}
