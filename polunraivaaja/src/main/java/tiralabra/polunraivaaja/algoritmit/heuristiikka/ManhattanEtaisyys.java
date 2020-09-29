package tiralabra.polunraivaaja.algoritmit.heuristiikka;

import tiralabra.polunraivaaja.apurakenteet.Ruutu;

public class ManhattanEtaisyys implements Heuristiikka {

  @Override
  public double laskeEtaisyys(Ruutu lahto, Ruutu kohde) {
    return Math.abs(lahto.y - kohde.y) + Math.abs(lahto.x - kohde.x);
  }

}
