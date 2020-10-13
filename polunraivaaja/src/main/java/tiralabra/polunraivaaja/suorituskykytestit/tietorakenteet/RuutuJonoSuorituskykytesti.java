package tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet;

import java.util.ArrayDeque;
import java.util.Queue;

import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuJono;

public class RuutuJonoSuorituskykytesti implements TietorakenneSuorituskykytesti {

    @Override
    public long suoritaJavaRakenteella(int iteraatiot) {
        Ruutu ruutu = new Ruutu(0, 0);

        long alku = System.nanoTime();

        Queue<Ruutu> jono = new ArrayDeque<>();

        for (int i = 0; i < iteraatiot; i++) {
            jono.add(ruutu);
        }

        for (int i = 0; i < iteraatiot; i++) {
            jono.remove();
        }

        long loppu = System.nanoTime();

        return loppu - alku;
    }

    @Override
    public long suoritaOmallaRakenteella(int iteraatiot) {
        Ruutu ruutu = new Ruutu(0, 0);

        long alku = System.nanoTime();

        RuutuJono jono = new RuutuJono();

        for (int i = 0; i < iteraatiot; i++) {
            jono.lisaaJonoon(ruutu);
        }

        for (int i = 0; i < iteraatiot; i++) {
            jono.otaJonosta();
        }

        long loppu = System.nanoTime();

        return loppu - alku;
    }

}