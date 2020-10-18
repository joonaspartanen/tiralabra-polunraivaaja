package tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuLista;

/**
 * Vertailee omaa RuutuLista-tietorakennetta ja Javan ArrayListia halutulla
 * määrällä listaan lisäämisiä ja listalta lukemisia.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class RuutuListaSuorituskykytesti implements TietorakenneSuorituskykytesti {

    /**
     * Random-olio, jota käytetään haettavan ruudun "arpomiseen".
     */
    private Random arpoja;

    public RuutuListaSuorituskykytesti() {
        this.arpoja = new Random(999);
    }

    @Override
    public long suoritaJavaRakenteella(int operaatioita) {
        Ruutu ruutu = new Ruutu(0, 0);

        long alku = System.nanoTime();

        List<Ruutu> lista = new ArrayList<>();

        for (int i = 0; i < operaatioita; i++) {
            lista.add(ruutu);
        }

        for (int i = 0; i < operaatioita; i++) {
            lista.get(arpoja.nextInt(operaatioita));
        }

        long loppu = System.nanoTime();

        return loppu - alku;
    }

    @Override
    public long suoritaOmallaRakenteella(int operaatioita) {
        Ruutu ruutu = new Ruutu(0, 0);

        long alku = System.nanoTime();

        RuutuLista lista = new RuutuLista();

        for (int i = 0; i < operaatioita; i++) {
            lista.lisaaRuutu(ruutu);
        }

        for (int i = 0; i < operaatioita; i++) {
            lista.haeRuutuIndeksissa(arpoja.nextInt(operaatioita));
        }

        long loppu = System.nanoTime();

        return loppu - alku;
    }
}
