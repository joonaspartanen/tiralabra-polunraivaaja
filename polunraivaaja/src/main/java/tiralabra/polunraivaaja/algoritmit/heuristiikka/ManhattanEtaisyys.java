package tiralabra.polunraivaaja.algoritmit.heuristiikka;

import tiralabra.polunraivaaja.tyokalut.Laskin;
import tiralabra.polunraivaaja.mallit.Ruutu;

public class ManhattanEtaisyys implements Heuristiikka {

    @Override
    public double laskeEtaisyys(Ruutu lahto, Ruutu kohde) {
        return (double) Laskin.laskeItseisarvo(lahto.y - kohde.y) + Laskin.laskeItseisarvo(lahto.x - kohde.x);
    }

}
