package tiralabra.polunraivaaja.kartta;

import java.util.List;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;

/**
 * Piirtää kartan graafiseen käyttöliittymään.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 *
 */
public class GraafinenKartanpiirtaja extends Kartanpiirtaja {

    private GridPane karttaruudukko;

    /**
     * Konstruktori.
     *
     * @param kartta Piirrettävä kartta.
     */
    public GraafinenKartanpiirtaja(Kartta kartta) {
        super(kartta);
        karttaruudukko = new GridPane();
    }

    /**
     * Piirtää pelkän pohjakartan.
     */
    @Override
    public void piirraKartta() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                piirraRuutu(i, j);
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
                if (!reitti.contains(new Koordinaatti(i, j))) {
                    piirraRuutu(i, j);
                } else {
                    Rectangle ruutu = new Rectangle(1, 1);
                    ruutu.setFill(Color.CRIMSON);
                    karttaruudukko.add(ruutu, j, i);
                }
            }
        }
    }

    private void piirraRuutu(int rivi, int sarake) {
        Rectangle ruutu = new Rectangle(1, 1);
        ruutu.setFill(kartta.getRuutu(rivi, sarake) == 0 ? Color.GHOSTWHITE : Color.BLACK);
        karttaruudukko.add(ruutu, sarake, rivi);
    }

    public GridPane getKarttaruudukko() {
        return karttaruudukko;
    }

}
