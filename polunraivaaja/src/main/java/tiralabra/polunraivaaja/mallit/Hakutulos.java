package tiralabra.polunraivaaja.mallit;

import tiralabra.polunraivaaja.tietorakenteet.RuutuLista;
import tiralabra.polunraivaaja.tyokalut.Laskin;

/**
 * Luokka, joka käärii reittihaun lopputuloksena saadun reitin ja muita haun
 * metatietoja.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Hakutulos {

    private boolean onnistui;
    private String viesti;
    private RuutuLista reitti;
    private int ruutujaTarkasteltu;
    private int kartallaVapaitaRuutuja;
    private boolean[][] vierailtu;
    private double reitinPituus;
    private long haunKesto;

    /**
     *
     * @param onnistui               Totuusarvo, joka kertoo onnistuiko haku.
     * @param viesti                 Viesti, joka kertoo esimerkiksi, miksi haku ei
     *                               onnistunut.
     * @param ruutujaTarkasteltu     Hakualgoritmin tarkastelemien solmujen määrä.
     * @param kartallaVapaitaRuutuja Kartalla olevien vapaiden ruutujen määrä.
     * @param reitti                 Haun lopputuloksena muodostettu reitti.
     * @param vierailtu              Taulukko, joka sisältää tiedon solmuista,
     *                               joissa hakualgoritmi vieraili.
     * @param reitinPituus           Reitin pituus liukulukuna.
     * @param haunKesto              Haun kesto nanosekunteina.
     *
     */
    public Hakutulos(boolean onnistui, String viesti, int tarkasteltujaSolmuja, int kartallaVapaitaRuutuja,
            boolean[][] vierailtu) {
        this.onnistui = onnistui;
        this.viesti = viesti;
        this.reitti = new RuutuLista();
        this.ruutujaTarkasteltu = tarkasteltujaSolmuja;
        this.kartallaVapaitaRuutuja = kartallaVapaitaRuutuja;
        this.vierailtu = vierailtu;
        this.reitinPituus = reitti.haePituus() - 1.0;
        this.haunKesto = 0;
    }

    public Hakutulos(boolean onnistui, String viesti, int tarkasteltujaSolmuja, int kartallaVapaitaRuutuja,
            RuutuLista reitti, boolean[][] vierailtu, double reitinPituus, long haunKesto) {
        this.onnistui = onnistui;
        this.viesti = viesti;
        this.reitti = reitti;
        this.ruutujaTarkasteltu = tarkasteltujaSolmuja;
        this.kartallaVapaitaRuutuja = kartallaVapaitaRuutuja;
        this.vierailtu = vierailtu;
        this.reitinPituus = reitinPituus;
        this.haunKesto = haunKesto;
    }

    public boolean isOnnistui() {
        return onnistui;
    }

    public String getViesti() {
        return viesti;
    }

    public RuutuLista getReitti() {
        return reitti;
    }

    public int getRuutujaTarkasteltu() {
        return ruutujaTarkasteltu;
    }

    public boolean[][] getVierailtu() {
        return vierailtu;
    }

    public double getReitinPituus() {
        return reitinPituus;
    }

    public long getHaunKesto() {
        return haunKesto;
    }

    private int laskeTarkasteltujenRuutujenOsuus() {
        return (int) ((double) ruutujaTarkasteltu / (double) kartallaVapaitaRuutuja * 100);
    }

    @Override
    public String toString() {
        return viesti + "\n" + "Ruutuja tarkasteltu: " + ruutujaTarkasteltu + " (~" + laskeTarkasteltujenRuutujenOsuus()
                + " % vapaista ruuduista)\n" + "Reitin pituus: " + reitinPituus + "\n" + "Aikaa kului: "
                + haunKesto / 1000000 + " ms";
    }

}
