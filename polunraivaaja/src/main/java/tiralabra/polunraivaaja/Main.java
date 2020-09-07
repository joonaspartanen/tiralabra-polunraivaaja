package tiralabra.polunraivaaja;

import tiralabra.polunraivaaja.algoritmit.Haku;
import tiralabra.polunraivaaja.algoritmit.Leveyshaku;
import tiralabra.polunraivaaja.kartta.Kartanpiirtaja;
import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.kartta.KonsoliKartanpiirtaja;
import tiralabra.polunraivaaja.ui.GUI;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;
import tiralabra.polunraivaaja.io.Kartanlukija;

/**
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI.main(args);
        
        //Kartanlukija lukija = new Kartanlukija("kartat");
        //Kartta kartta = lukija.lueKarttatiedosto("Berlin_0_256.map");
        // kartta.tulostaKartta();
        //Haku haku = new Leveyshaku(kartta);
        //Kartanpiirtaja piirtaja = new KonsoliKartanpiirtaja(kartta);
        /* try {
            haku.etsiReitti(new Koordinaatti(0, 0), new Koordinaatti(255, 245));
            piirtaja.piirraKartta(haku.getReitti());
        } catch (NullPointerException e) {
            System.out.println("Reitin etsiminen epäonnistui.");
        }
        */
    }

}
