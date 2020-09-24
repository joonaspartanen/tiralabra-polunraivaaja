package tiralabra.polunraivaaja.apurakenteet;

import java.util.ArrayList;
import java.util.List;

/**
 * Luokka, joka käärii reittihaun lopputuloksena saadun reitin ja muita haun
 * metatietoja.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Hakutulos {

    private boolean onnistui;
    private String viesti;
    private List<Ruutu> reitti;
    private int ruutujaTarkasteltu;
    private boolean[][] vierailtu;

    public Hakutulos(boolean onnistui, String viesti, int tarkasteltujaSolmuja, boolean[][] vierailtu) {
        this.onnistui = onnistui;
        this.viesti = viesti;
        this.reitti = new ArrayList<>();
        this.ruutujaTarkasteltu = tarkasteltujaSolmuja;
        this.vierailtu = vierailtu;
    }

    /**
     *
     * @param onnistui Totuusarvo, joka kertoo onnistuiko haku.
     * @param viesti Viesti, joka kertoo esimerkiksi, miksi haku ei onnistunut.
     * @param ruutujaTarkasteltu Hakualgoritmin tarkastelemien solmujen määrä.
     * @param reitti Haun lopputuloksena muodostettu reitti.
     * @param vierailtu Taulukko, joka sisältää tiedon solmuista, joissa
     * hakualgoritmi vieraili.
     */
    public Hakutulos(boolean onnistui, String viesti, int tarkasteltujaSolmuja, List<Ruutu> reitti,
            boolean[][] vierailtu) {
        this.onnistui = onnistui;
        this.viesti = viesti;
        this.reitti = reitti;
        this.ruutujaTarkasteltu = tarkasteltujaSolmuja;
        this.vierailtu = vierailtu;
    }

    public boolean isOnnistui() {
        return onnistui;
    }

    public String getViesti() {
        return viesti;
    }

    public List<Ruutu> getReitti() {
        return reitti;
    }

    public int getRuutujaTarkasteltu() {
        return ruutujaTarkasteltu;
    }

    public boolean[][] getVierailtu() {
        return vierailtu;
    }

    @Override
    public String toString() {
        return viesti + "\n" + "Ruutuja tarkasteltu: " + ruutujaTarkasteltu + "\n" + "Reitin pituus: " + reitti.size();
    }

}
