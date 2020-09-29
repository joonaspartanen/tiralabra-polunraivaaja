package tiralabra.polunraivaaja.algoritmit;

import java.util.PriorityQueue;
import java.util.Queue;

import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.apurakenteet.Suunta;
import tiralabra.polunraivaaja.kartta.Kartta;

/**
 * A*-algoritmin toteutus. Hakee kartalta lyhimmän reitin kahden pisteen välillä
 * käyttäen apuna heuristista funktiota, joka arvioi tarkasteltavien solmujen
 * etäisyyttä reitin loppuun.
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
        etaisyysarvioLoppuun[alkuY][alkuX] = laskeDiagonaaliEtaisyys(alku, loppu);
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

            int suuntienMaara = salliDiagonaalisiirtymat ? 8 : 4;

            for (int i = 0; i < suuntienMaara; i++) {
                int uusiY = nykyinenY + Suunta.riviSiirtymat[i];
                int uusiX = nykyinenX + Suunta.sarakeSiirtymat[i];

                if (!ruutuKelpaa(uusiY, uusiX) || vierailtu[uusiY][uusiX]) {
                    continue;
                }

                // TODO: Poista taikanumerot.
                double etaisyysSeuraavaan = i < 4 ? 1 : Math.sqrt(2);

                double uusiEtaisyys = etaisyysAlusta[nykyinenY][nykyinenX] + etaisyysSeuraavaan;

                if (uusiEtaisyys < etaisyysAlusta[uusiY][uusiX]) {
                    etaisyysAlusta[uusiY][uusiX] = uusiEtaisyys;
                    etaisyysarvioLoppuun[uusiY][uusiX] = uusiEtaisyys
                            + laskeDiagonaaliEtaisyys(new Ruutu(uusiY, uusiX), loppu);
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

    private int laskeManhattanEtaisyys(int rivi, int sarake) {
        return Math.abs(rivi - loppu.getRivi()) + Math.abs(sarake - loppu.getSarake());
    }
}
