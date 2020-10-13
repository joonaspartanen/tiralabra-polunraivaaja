package tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet;

import java.util.PriorityQueue;
import java.util.Queue;

import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuKeko;
import tiralabra.polunraivaaja.tyokalut.RuutuKomparaattori;

public class RuutuKekoSuorituskykytesti implements TietorakenneSuorituskykytesti {

    private final double[][] vertailuperuste;

    public RuutuKekoSuorituskykytesti() {
        vertailuperuste = new double[10][10];

        for (int i = 0; i < vertailuperuste.length; i++) {
            for (int j = 0; j < vertailuperuste[0].length; j++) {
                vertailuperuste[i][j] = i + j;
            }
        }
    }

    @Override
    public long suoritaJavaRakenteella(int iteraatiot) {
        Ruutu ruutu = new Ruutu(0, 0);

        long alku = System.nanoTime();

        Queue<Ruutu> jono = new PriorityQueue<>((a, b) -> vertailuperuste[a.y][a.x] - vertailuperuste[b.y][b.x] < 0 ? -1 : 1);

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

        RuutuKomparaattori komparaattori = new RuutuKomparaattori(vertailuperuste);

        long alku = System.nanoTime();

        RuutuKeko keko = new RuutuKeko(komparaattori);

        for (int i = 0; i < iteraatiot; i++) {
            keko.lisaaRuutu(ruutu);
        }

        for (int i = 0; i < iteraatiot; i++) {
            keko.otaKeosta();
        }

        long loppu = System.nanoTime();

        return loppu - alku;
    }

}
