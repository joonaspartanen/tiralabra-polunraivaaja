package tiralabra.polunraivaaja.algoritmit;

import java.util.List;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;
import tiralabra.polunraivaaja.kartta.Kartta;

/**
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public interface Haku {


    boolean etsiReitti(Koordinaatti alku, Koordinaatti loppu);
  
    /**
     * Palauttaa haun löytämää reittiä kuvaavan listan Koordinaatteja.
     *
     * @return Lista Koordinaatteja.
     */
    List<Koordinaatti> getReitti();

    void setKartta(Kartta kartta);
    
}
