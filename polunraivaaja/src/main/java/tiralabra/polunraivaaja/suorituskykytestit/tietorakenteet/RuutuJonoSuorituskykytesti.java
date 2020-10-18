package tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet;

import java.util.ArrayDeque;
import java.util.Queue;

import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuJono;

/**
 * Testi, joka vertaa oman RuutuJono-luokan suorituskykyä Javan
 * ArrayDeque-rakenteeseen. Kummallakin tietorakenteella lisätään haluttu määrä
 * kertoja Ruutu-olio jonoon ja poistetaan jonon ensimmäinen alkio.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class RuutuJonoSuorituskykytesti implements TietorakenneSuorituskykytesti {

    @Override
    public long suoritaJavaRakenteella(int operaatioita) {
        Ruutu ruutu = new Ruutu(0, 0);

        long alku = System.nanoTime();

        Queue<Ruutu> jono = new ArrayDeque<>();

        for (int i = 0; i < operaatioita; i++) {
            jono.add(ruutu);
        }

        for (int i = 0; i < operaatioita; i++) {
            jono.remove();
        }

        long loppu = System.nanoTime();

        return loppu - alku;
    }

    @Override
    public long suoritaOmallaRakenteella(int operaatioita) {
        Ruutu ruutu = new Ruutu(0, 0);

        long alku = System.nanoTime();

        RuutuJono jono = new RuutuJono();

        for (int i = 0; i < operaatioita; i++) {
            jono.lisaaJonoon(ruutu);
        }

        for (int i = 0; i < operaatioita; i++) {
            jono.otaJonosta();
        }

        long loppu = System.nanoTime();

        return loppu - alku;
    }

}
