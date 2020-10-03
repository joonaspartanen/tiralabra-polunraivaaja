package tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet;

import java.util.ArrayList;
import java.util.List;

import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.apurakenteet.RuutuLista;

/**
 * Alustava toteutus suorituskykytestistä, joka vertailee omaa
 * RuutuLista-tietorakennetta ja Javan ArrayListia suurella määrällä listaan
 * lisäämisiä ja listalta lukemisia.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class RuutuListaSuorituskykytesti {

    public static Vertailutulos suorita() {
        int iteraatioita = 1000000;

        long javaRakenteenAika = suoritaArrayListilla(iteraatioita);
        long omanRakenteenAika = suoritaRuutuListalla(iteraatioita);

        return new Vertailutulos(omanRakenteenAika, javaRakenteenAika, iteraatioita);
    }

    private static long suoritaRuutuListalla(int iteraatioita) {
        long alku = System.nanoTime();

        RuutuLista lista = new RuutuLista();
        for (int i = 0; i < iteraatioita; i++) {
            lista.lisaaRuutu(new Ruutu(i, i));
        }

        for (int i = 0; i < iteraatioita; i++) {
            Ruutu ruutu = lista.haeRuutuIndeksissa(i);
        }

        long loppu = System.nanoTime();

        return loppu - alku;
    }

    private static long suoritaArrayListilla(int iteraatioita) {
        long alku = System.nanoTime();

        List<Ruutu> lista = new ArrayList<>();
        for (int i = 0; i < iteraatioita; i++) {
            lista.add(new Ruutu(i, i));
        }

        for (int i = 0; i < iteraatioita; i++) {
            Ruutu ruutu = lista.get(i);
        }

        long loppu = System.nanoTime();

        return loppu - alku;
    }

}
