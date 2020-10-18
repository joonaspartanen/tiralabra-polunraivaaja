package tiralabra.polunraivaaja.algoritmit.heuristiikka;

import tiralabra.polunraivaaja.tyokalut.Laskin;
import tiralabra.polunraivaaja.mallit.Ruutu;

public class DiagonaaliEtaisyys implements Heuristiikka {

    @Override
    public int laskeEtaisyys(Ruutu lahto, Ruutu kohde) {

        int dy = Laskin.laskeItseisarvo(kohde.y - lahto.y);
        int dx = Laskin.laskeItseisarvo(kohde.x - lahto.x);

        return 10 * (dx + dy) + (14 - 20) * Laskin.valitsePienempi(dx, dy);
    }

}
