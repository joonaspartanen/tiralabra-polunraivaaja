package tiralabra.polunraivaaja.algoritmit;

import java.util.PriorityQueue;
import java.util.Queue;

import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;
import tiralabra.polunraivaaja.apurakenteet.Suunnat;
import tiralabra.polunraivaaja.kartta.Kartta;

public class AStar extends HakuPohja {

  // TODO: Huomioi tilanteet, joissa algoritmi luikahtaa diagonaalisiirtymällä
  // kahden kulmittaisen esteen välistä?
  private boolean salliDiagonaalisiirtymat;

  public AStar(Kartta kartta) {
    super(kartta);
  }

  @Override
  public void setKartta(Kartta kartta) {
    this.kartta = kartta;
  }

  public void setSalliDiagonaalisiirtymat(boolean salliDiagonaalisiirtymat) {
    this.salliDiagonaalisiirtymat = salliDiagonaalisiirtymat;
  }

  // TODO: Refaktoroi metodi.
  @Override
  public Hakutulos etsiReitti(Koordinaatti alku, Koordinaatti loppu) {
    solmujaTarkasteltu = 0;

    if (!reitinPaatVapaat(alku, loppu)) {
      tulos = new Hakutulos(false, "Alku- tai loppupiste ei kelpaa.", solmujaTarkasteltu, vierailtu);
      return tulos;
    }

    this.alku = alku;
    this.loppu = loppu;

    double[][] etaisyysAlusta = new double[korkeus][leveys];
    double[][] etaisyysarvioLoppuun = new double[korkeus][leveys];

    alustaTaulukko(etaisyysAlusta);
    alustaTaulukko(etaisyysarvioLoppuun);

    Queue<Koordinaatti> jono = new PriorityQueue<>((a,
        b) -> etaisyysarvioLoppuun[a.getRivi()][a.getSarake()] - etaisyysarvioLoppuun[b.getRivi()][b.getSarake()] < 0
            ? -1
            : 1);

    edeltajat = new Koordinaatti[korkeus][leveys];
    vierailtu = new boolean[korkeus][leveys];

    int alkuY = alku.getRivi();
    int alkuX = alku.getSarake();

    jono.add(alku);
    etaisyysAlusta[alkuY][alkuX] = 0;
    etaisyysarvioLoppuun[alkuY][alkuX] = laskeManhattanEtaisyys(alkuY, alkuX);
    vierailtu[alkuY][alkuX] = true;

    while (!jono.isEmpty()) {
      Koordinaatti nykyinenRuutu = jono.remove();

      int nykyinenY = nykyinenRuutu.getRivi();
      int nykyinenX = nykyinenRuutu.getSarake();

      if (loppuSaavutettu(nykyinenY, nykyinenX)) {
        muodostaReitti();
        tulos = new Hakutulos(true, "Reitti löytyi.", solmujaTarkasteltu, reitti, vierailtu);
        return tulos;
      }

      int suuntienMaara = salliDiagonaalisiirtymat ? 8 : 4;

      for (int i = 0; i < suuntienMaara; i++) {
        int uusiY = nykyinenY + Suunnat.riviSiirtymat[i];
        int uusiX = nykyinenX + Suunnat.sarakeSiirtymat[i];

        if (!ruutuKelpaa(uusiY, uusiX) || vierailtu[uusiY][uusiX]) {
          continue;
        }

        // TODO: Poista taikanumerot.
        double etaisyysSeuraavaan = i < 4 ? 1 : Math.sqrt(2);

        double uusiEtaisyys = etaisyysAlusta[nykyinenY][nykyinenX] + etaisyysSeuraavaan;

        if (uusiEtaisyys < etaisyysAlusta[uusiY][uusiX]) {
          etaisyysAlusta[uusiY][uusiX] = uusiEtaisyys;
          etaisyysarvioLoppuun[uusiY][uusiX] = uusiEtaisyys + laskeManhattanEtaisyys(uusiY, uusiX);
          edeltajat[uusiY][uusiX] = nykyinenRuutu;
          vierailtu[uusiY][uusiX] = true;
          jono.add(new Koordinaatti(uusiY, uusiX));
          solmujaTarkasteltu++;
        }
      }
    }
    tulos = new Hakutulos(false, "Reitti ei mahdollinen.", solmujaTarkasteltu, vierailtu);
    return tulos;
  }

  private int laskeManhattanEtaisyys(int rivi, int sarake) {
    return Math.abs(rivi - loppu.getRivi()) + Math.abs(sarake - loppu.getSarake());
  }

  private void alustaTaulukko(double[][] taulukko) {
    for (int i = 0; i < korkeus; i++) {
      for (int j = 0; j < leveys; j++) {
        taulukko[i][j] = Double.MAX_VALUE;
      }
    }
  }
}
