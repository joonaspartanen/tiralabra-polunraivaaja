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
    private final double KARTTAPOHJAN_LEVEYS = 512;
    private final double KARTTAPOHJAN_KORKEUS = 512;

    private double ruudunLeveys;
    private double ruudunKorkeus;

    /**
     * Konstruktori.
     *
     * @param kartta Piirrettävä kartta.
     */
    public GraafinenKartanpiirtaja(Kartta kartta) {
        super(kartta);
        karttapohja = new Canvas(KARTTAPOHJAN_LEVEYS, KARTTAPOHJAN_KORKEUS);
        this.ruudunLeveys = KARTTAPOHJAN_LEVEYS / leveys;
        this.ruudunKorkeus = KARTTAPOHJAN_KORKEUS / korkeus;
        gc = karttapohja.getGraphicsContext2D();
    }

    /**
     * Piirtää pelkän pohjakartan.
     */
    @Override
    public void piirraKartta() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                piirraRuutu(i, j, false, false);
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
                piirraRuutu(i, j, kuuluuReittiin, false);
            }
        }
    }

    @Override
    public void piirraKartta(List<Koordinaatti> reitti, boolean[][] vierailtu) {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                boolean kuuluuReittiin = reitti.contains(new Koordinaatti(i, j));
                boolean ruudussaVierailtu = vierailtu[i][j];
                piirraRuutu(i, j, kuuluuReittiin, ruudussaVierailtu);
            }
        }
    }

    private void piirraRuutu(int rivi, int sarake, boolean kuuluuReittiin, boolean ruudussaVierailtu) {
        if (kuuluuReittiin) {
            gc.setFill(Color.CRIMSON);
        } else if (ruudussaVierailtu) {
            gc.setFill(Color.GREENYELLOW);
        } else {
            gc.setFill(kartta.ruutuVapaa(rivi, sarake) ? Color.WHITESMOKE : Color.BLACK);
        }
        gc.fillRect(sarake * ruudunLeveys, rivi * ruudunKorkeus, ruudunLeveys, ruudunKorkeus);
    }

    /**
     *
     * @return Canvas-olio, joka muodostaa kartan.
     */
    public Canvas getKarttapohja() {
        return karttapohja;
    }

    // TODO: Poista taikanumerot.
    public void piirraPiste(Koordinaatti sijainti) {
        gc.setFill(Color.CRIMSON);
        gc.fillOval(sijainti.getSarake() * ruudunLeveys - 3, sijainti.getRivi() * ruudunKorkeus - 3, 7, 7);
    }

    public double getRuudunLeveys() {
        return ruudunLeveys;
    }

    public double getRuudunKorkeus() {
        return ruudunKorkeus;
    }

}
