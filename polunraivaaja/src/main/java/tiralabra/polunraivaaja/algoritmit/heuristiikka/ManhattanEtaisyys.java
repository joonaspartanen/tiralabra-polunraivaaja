package tiralabra.polunraivaaja.algoritmit.heuristiikka;

import tiralabra.polunraivaaja.apurakenteet.Laskin;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;

public class ManhattanEtaisyys implements Heuristiikka {

    @Override
    public double laskeEtaisyys(Ruutu lahto, Ruutu kohde) {
        return (double) Laskin.laskeItseisarvo(lahto.y - kohde.y) + Laskin.laskeItseisarvo(lahto.x - kohde.x);
    }

}
