package tiralabra.polunraivaaja.algoritmit.heuristiikka;

import tiralabra.polunraivaaja.apurakenteet.Laskin;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;

public class DiagonaaliEtaisyys implements Heuristiikka {

    @Override
    public double laskeEtaisyys(Ruutu lahto, Ruutu kohde) {

        int dy = Laskin.laskeItseisarvo(kohde.y - lahto.y);
        int dx = Laskin.laskeItseisarvo(kohde.x - lahto.x);

        return (dx + dy) + (Laskin.SQRT_2 - 2) * Laskin.valitsePienempi(dx, dy);
    }

}
