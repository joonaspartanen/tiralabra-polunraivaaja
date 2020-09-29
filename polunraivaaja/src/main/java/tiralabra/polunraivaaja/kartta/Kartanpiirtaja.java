package tiralabra.polunraivaaja.kartta;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.apurakenteet.RuutuLista;

/**
 * Piirtää kartan graafiseen käyttöliittymään.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 *
 */
public class Kartanpiirtaja {

    private Kartta kartta;
    private int korkeus;
    private int leveys;
    private Canvas karttapohja;
    private GraphicsContext gc;
    private final double KARTTAPOHJAN_LEVEYS = 512;
    private final double KARTTAPOHJAN_KORKEUS = 512;

    private double ruudunLeveys;
    private double ruudunKorkeus;

    /**
     * @param kartta Piirrettävä kartta.
     */
    public Kartanpiirtaja(Kartta kartta) {
        this.kartta = kartta;
        this.korkeus = kartta.getKorkeus();
        this.leveys = kartta.getLeveys();
        karttapohja = new Canvas(KARTTAPOHJAN_LEVEYS, KARTTAPOHJAN_KORKEUS);
        gc = karttapohja.getGraphicsContext2D();
        alustaKarttapohja();
    }

    /**
     * Piirtää pelkän pohjakartan.
     */
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
    public void piirraKartta(RuutuLista reitti) {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                piirraRuutu(i, j, false, false);
            }
        }
        piirraReitti(reitti);
    }

    /**
     *
     * Piirtää pohjakartan ja siihen parametrina saadun reitin sekä vieraillut
     * ruudut.
     *
     *
     */
    public void piirraKartta(RuutuLista reitti, boolean[][] vierailtu) {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                boolean ruudussaVierailtu = vierailtu[i][j];
                piirraRuutu(i, j, false, ruudussaVierailtu);
            }
        }
        piirraReitti(reitti);
    }

    private void piirraRuutu(int rivi, int sarake, boolean ruutuKuuluuReittiin, boolean ruudussaVierailtu) {
        if (ruutuKuuluuReittiin) {
            gc.setFill(Color.CRIMSON);
        } else if (ruudussaVierailtu) {
            gc.setFill(Color.GREENYELLOW);
        } else {
            gc.setFill(kartta.ruutuVapaa(rivi, sarake) ? Color.WHITESMOKE : Color.BLACK);
        }
        gc.fillRect(sarake * ruudunLeveys, rivi * ruudunKorkeus, ruudunLeveys, ruudunKorkeus);
    }

    private void piirraReitti(RuutuLista reitti) {
        for (int i = 0; i < reitti.getRuutuja(); i++) {
            Ruutu ruutu = reitti.haeRuutuIndeksissa(i);
            piirraRuutu(ruutu.getRivi(), ruutu.getSarake(), true, false);
        }
    }

    /**
     *
     * @return Canvas-olio, joka muodostaa kartan.
     */
    public Canvas getKarttapohja() {
        return karttapohja;
    }

    /**
     * Käytetään piirtämään karttaan reitin alku- ja loppupisteet.
     *
     * TODO: Poista taikanumerot.
     */
    public void piirraPiste(Ruutu sijainti) {
        gc.setFill(Color.CRIMSON);
        gc.fillOval(sijainti.getSarake() * ruudunLeveys - 3, sijainti.getRivi() * ruudunKorkeus - 3, 7, 7);
    }

    public double getRuudunLeveys() {
        return ruudunLeveys;
    }

    public double getRuudunKorkeus() {
        return ruudunKorkeus;
    }

    public void setKartta(Kartta kartta) {
        this.kartta = kartta;
        this.korkeus = kartta.getKorkeus();
        this.leveys = kartta.getLeveys();
        alustaKarttapohja();
    }

    private void alustaKarttapohja() {
        this.ruudunLeveys = KARTTAPOHJAN_LEVEYS / leveys;
        this.ruudunKorkeus = KARTTAPOHJAN_KORKEUS / korkeus;
        gc.clearRect(0, 0, leveys, korkeus);
    }
}
