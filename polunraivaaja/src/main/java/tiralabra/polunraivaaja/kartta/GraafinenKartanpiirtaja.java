package tiralabra.polunraivaaja.kartta;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;

/**
 * Piirtää kartan graafiseen käyttöliittymään.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 *
 */
public class GraafinenKartanpiirtaja extends Kartanpiirtaja {

    private Canvas karttapohja;
    private GraphicsContext gc;
    private final double RUUDUN_LEVEYS = 1;
    private final double RUUDUN_KORKEUS = 1;

    /**
     * Konstruktori.
     *
     * @param kartta Piirrettävä kartta.
     */
    public GraafinenKartanpiirtaja(Kartta kartta) {
        super(kartta);
        karttapohja = new Canvas(kartta.getLeveys() * RUUDUN_LEVEYS, kartta.getKorkeus() * RUUDUN_KORKEUS);
        gc = karttapohja.getGraphicsContext2D();
    }

    /**
     * Piirtää pelkän pohjakartan.
     */
    @Override
    public void piirraKartta() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                piirraRuutu(i, j, false);
            }
        }
    }

    /**
     * Piirtää pohjakartan ja siihen parametrina saadun reitin.
     *
     * @param reitti Pohjakartan päälle piirrettävä reitti.
     */
    @Override
    public void piirraKartta(List<Koordinaatti> reitti) {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                boolean kuuluuReittiin = reitti.contains(new Koordinaatti(i, j));
                piirraRuutu(i, j, kuuluuReittiin);
            }
        }
    }

    private void piirraRuutu(int rivi, int sarake, boolean kuuluuReittiin) {
        if (kuuluuReittiin) {
            gc.setFill(Color.CRIMSON);
        } else {
            gc.setFill(kartta.ruutuVapaa(rivi, sarake) ? Color.WHITESMOKE : Color.BLACK);
        }

        gc.fillRect(sarake * RUUDUN_LEVEYS, rivi * RUUDUN_KORKEUS, RUUDUN_LEVEYS, RUUDUN_KORKEUS);
    }

    /**
     *
     * @return GridPane-olio, joka muodostaa kartan.
     */
    public Canvas getKarttapohja() {
        return karttapohja;
    }

    // TODO: Poista taikanumerot.
    public void piirraPiste(Koordinaatti sijainti) {
        gc.setFill(Color.CRIMSON);
        gc.fillOval(sijainti.getSarake() - 3, sijainti.getRivi() - 3, 7, 7);
    }

}
