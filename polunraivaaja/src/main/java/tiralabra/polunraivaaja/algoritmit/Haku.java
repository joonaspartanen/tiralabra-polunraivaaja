package tiralabra.polunraivaaja.algoritmit;

import java.util.List;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;

/**
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public interface Haku {

    void setKartta(Kartta kartta);

    boolean etsiReitti(Koordinaatti alku, Koordinaatti loppu);

    List<Koordinaatti> getReitti();
}
