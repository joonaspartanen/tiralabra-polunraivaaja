package tiralabra.polunraivaaja.algoritmit;

import java.util.PriorityQueue;
import java.util.Queue;

import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;
import tiralabra.polunraivaaja.apurakenteet.Suunnat;
import tiralabra.polunraivaaja.kartta.Kartta;

public class AStar extends HakuPohja {

  public AStar(Kartta kartta) {
    super(kartta);
  }

  @Override
  public void setKartta(Kartta kartta) {
    this.kartta = kartta;
  }

  @Override
  public boolean etsiReitti(Koordinaatti alku, Koordinaatti loppu) {

    if (!reitinPaatVapaat(alku, loppu)) {
      System.out.println("Alku- tai loppupiste ei kelpaa.");
      return false;
    }

    this.alku = alku;
    this.loppu = loppu;

    int[][] etaisyys = new int[korkeus][leveys];
    int[][] etaisyysarvio = new int[korkeus][leveys];

    for (int i = 0; i < korkeus; i++) {
      for (int j = 0; j < leveys; j++) {
        etaisyys[i][j] = Integer.MAX_VALUE;
        etaisyysarvio[i][j] = Integer.MAX_VALUE;

      }
    }

    Queue<Koordinaatti> jono = new PriorityQueue<>(
        (a, b) -> etaisyysarvio[a.getRivi()][a.getSarake()] - etaisyysarvio[b.getRivi()][b.getSarake()]);
    edeltajat = new Koordinaatti[korkeus][leveys];
    vierailtu = new boolean[korkeus][leveys];

    jono.add(alku);
    etaisyys[alku.getRivi()][alku.getSarake()] = 0;
    etaisyysarvio[alku.getRivi()][alku.getSarake()] = laskeManhattanEtaisyys(alku.getRivi(), alku.getSarake());
    vierailtu[alku.getRivi()][alku.getSarake()] = true;

    while (!jono.isEmpty()) {
      Koordinaatti nykyinenRuutu = jono.remove();

      if (loppuSaavutettu(nykyinenRuutu.getRivi(), nykyinenRuutu.getSarake())) {
        muodostaReitti();
        return true;
      }

      for (int i = 0; i < 4; i++) {
        int rivi = nykyinenRuutu.getRivi() + Suunnat.riviSiirtymat[i];
        int sarake = nykyinenRuutu.getSarake() + Suunnat.sarakeSiirtymat[i];
        Koordinaatti seuraavaRuutu = new Koordinaatti(rivi, sarake);
        // seuraavaRuutu.setEtaisyys(kartta.getRuutu(rivi, sarake));

        if (!ruutuKelpaa(rivi, sarake)) {
          // System.out.println(seuraavaRuutu + " ei kelvannut");
          continue;
        }

        //System.out.println("Seuraava: " + seuraavaRuutu);

        int uusiEtaisyys = etaisyys[nykyinenRuutu.getRivi()][nykyinenRuutu.getSarake()] + 1;
        //System.out.println("Uusi etäisyys " + uusiEtaisyys + " = "
        //    + etaisyys[nykyinenRuutu.getRivi()][nykyinenRuutu.getSarake()] + " + " + "1");

        if (!vierailtu[rivi][sarake] || uusiEtaisyys < etaisyys[seuraavaRuutu.getRivi()][seuraavaRuutu.getSarake()]) {
          etaisyys[seuraavaRuutu.getRivi()][seuraavaRuutu.getSarake()] = uusiEtaisyys;
          etaisyysarvio[seuraavaRuutu.getRivi()][seuraavaRuutu.getSarake()] = uusiEtaisyys
              + laskeManhattanEtaisyys(rivi, sarake);
          jono.add(seuraavaRuutu);
          edeltajat[rivi][sarake] = nykyinenRuutu;
          vierailtu[rivi][sarake] = true;
        }
      }

    }
    System.out.println("Reitti ei mahdollinen.");
    return false;
  }

  private int laskeManhattanEtaisyys(int rivi, int sarake) {
    return Math.abs(rivi - loppu.getRivi()) + Math.abs(sarake - loppu.getSarake());
  }

}
