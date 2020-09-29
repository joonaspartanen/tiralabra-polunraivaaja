package tiralabra.polunraivaaja.algoritmit.heuristiikka;

import tiralabra.polunraivaaja.apurakenteet.Ruutu;

public class DiagonaaliEtaisyys implements Heuristiikka {

  @Override
  public double laskeEtaisyys(Ruutu lahto, Ruutu kohde) {

    int dy = Math.abs(kohde.getRivi() - lahto.getRivi());
    int dx = Math.abs(kohde.getSarake() - lahto.getSarake());

    return (dx + dy) + (Math.sqrt(2) - 2) * Math.min(dx, dy);
  }

}
