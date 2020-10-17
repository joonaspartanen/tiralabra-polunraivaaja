package tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet;

import java.util.ArrayList;
import java.util.List;

import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuLista;

/**
 * Vertailee omaa RuutuLista-tietorakennetta ja Javan ArrayListia halutulla
 * määrällä listaan lisäämisiä ja listalta lukemisia.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class RuutuListaSuorituskykytesti implements TietorakenneSuorituskykytesti {

    @Override
    public long suoritaJavaRakenteella(int iteraatiot) {
        Ruutu ruutu = new Ruutu(0, 0);

        long alku = System.nanoTime();

        List<Ruutu> lista = new ArrayList<>();

        for (int i = 0; i < iteraatiot; i++) {
            lista.add(ruutu);
        }

        for (int i = 0; i < iteraatiot; i++) {
            lista.get(i);
        }

        long loppu = System.nanoTime();

        return loppu - alku;
    }

    @Override
    public long suoritaOmallaRakenteella(int iteraatiot) {
        Ruutu ruutu = new Ruutu(0, 0);

        long alku = System.nanoTime();

        RuutuLista lista = new RuutuLista();

        for (int i = 0; i < iteraatiot; i++) {
            lista.lisaaRuutu(ruutu);
        }

        for (int i = 0; i < iteraatiot; i++) {
            lista.haeRuutuIndeksissa(i);
        }

        long loppu = System.nanoTime();

        return loppu - alku;
    }
}
