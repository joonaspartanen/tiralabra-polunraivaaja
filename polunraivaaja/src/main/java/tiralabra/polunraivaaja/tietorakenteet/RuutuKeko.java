package tiralabra.polunraivaaja.tietorakenteet;

import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tyokalut.RuutuKomparaattori;
import tiralabra.polunraivaaja.tyokalut.Taulukonkasittelija;

/**
 * Ruutu-olioita säilövä binäärikeko. Vaatii komparaattorin, jonka perusteella
 * ruudut järjestetään kekoon. Indeksi 0 pidetään tyhjänä laskutoimitusten
 * yksinkertaistamiseksi.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class RuutuKeko {

    /**
     * Taulukko, jossa keon sisältämät ruudut säilötään.
     */
    private Ruutu[] keko;

    /**
     * Keossa olevien ruutujen määrä.
     */
    private int ruutuja;

    /**
     * Komparaattori, jota käytetään ruutujen keskinäiseen järjestämiseen.
     */
    private final RuutuKomparaattori komparaattori;

    /**
     * Konstruktori. Kekoon mahtuu aluksi 100 ruutua, ja keon kokoa kasvatetaan
     * tarvittaessa.
     *
     * @param komparaattori Komparaattori, jota käytetään ruutujen keskinäiseen
     *                      järjestämiseen.
     */
    public RuutuKeko(final RuutuKomparaattori komparaattori) {
        this.keko = new Ruutu[100];
        this.ruutuja = 0;
        this.komparaattori = komparaattori;
    }

    /**
     * Lisää parametrina annetun ruudun oikealle paikalle kekoon.
     *
     * @param ruutu Lisättävä ruutu.
     */
    public void lisaaRuutu(final Ruutu ruutu) {
        if (Taulukonkasittelija.taulukkoTaynna(keko, ruutuja + 1)) {
            keko = Taulukonkasittelija.kasvataTaulukko(keko);
        }

        int indeksi = ++ruutuja;
        keko[indeksi] = ruutu;

        int vanhemmanIndeksi = laskeVanhemmanIndeksi(indeksi);

        while (indeksi > 1 && komparaattori.vertaaRuutuja(keko[vanhemmanIndeksi], keko[indeksi]) == 1) {
            vaihdaRuudutKeskenaan(vanhemmanIndeksi, indeksi);
            indeksi = vanhemmanIndeksi;
            vanhemmanIndeksi = laskeVanhemmanIndeksi(indeksi);
        }
    }

    private int laskeVasemmanLapsenIndeksi(final int indeksi) {
        return 2 * indeksi;
    }

    private int laskeOikeanLapsenIndeksi(final int indeksi) {
        return 2 * indeksi + 1;
    }

    private int laskeVanhemmanIndeksi(final int indeksi) {
        return indeksi / 2;
    }

    private void vaihdaRuudutKeskenaan(final int indeksi1, final int indeksi2) {
        if (indeksi1 > ruutuja || indeksi2 > ruutuja) {
            return;
        }
        final Ruutu temp = keko[indeksi1];
        keko[indeksi1] = keko[indeksi2];
        keko[indeksi2] = temp;
    }

    /**
     * Palauttaa keon päällimmäisen ruudun poistamatta sitä keosta.
     *
     * @return Keon päällimmäinen ruutu.
     */
    public Ruutu kurkistaKekoon() {
        return keko[1];
    }

    /**
     * Palauttaa keon päällimmäisen ruudun ja poistaa sen keosta.
     *
     * @return Keon päällimmäinen ruutu.
     */
    public Ruutu otaKeosta() {
        final Ruutu ruutu = keko[1];

        poistaPaallimmainenRuutu();

        return ruutu;
    }

    private void poistaPaallimmainenRuutu() {
        int indeksi = 1;

        final Ruutu pohjimmainen = keko[ruutuja];
        keko[indeksi] = pohjimmainen;
        keko[ruutuja--] = null;

        while (laskeOikeanLapsenIndeksi(indeksi) <= ruutuja) {
            final int sopivammanLapsenIndeksi = laskeSopivammanLapsenIndeksi(indeksi);

            final Ruutu sopivampiLapsi = keko[sopivammanLapsenIndeksi];

            if (komparaattori.vertaaRuutuja(keko[indeksi], sopivampiLapsi) == -1) {
                break;
            }

            vaihdaRuudutKeskenaan(indeksi, sopivammanLapsenIndeksi);
            indeksi = sopivammanLapsenIndeksi;
        }
    }

    private int laskeSopivammanLapsenIndeksi(final int indeksi) {
        int pienemmanLapsenIndeksi;

        final int vasemmanLapsenIndeksi = laskeVasemmanLapsenIndeksi(indeksi);
        final int oikeanLapsenIndeksi = laskeOikeanLapsenIndeksi(indeksi);

        final Ruutu vasenLapsi = keko[vasemmanLapsenIndeksi];
        final Ruutu oikeaLapsi = keko[oikeanLapsenIndeksi];

        if (komparaattori.vertaaRuutuja(vasenLapsi, oikeaLapsi) == -1) {
            pienemmanLapsenIndeksi = vasemmanLapsenIndeksi;
        } else {
            pienemmanLapsenIndeksi = oikeanLapsenIndeksi;
        }

        return pienemmanLapsenIndeksi;
    }

    /**
     * Ilmoittaa, kuinka monta ruutua keossa on.
     *
     * @return Keon koko.
     */
    public int haeKoko() {
        return ruutuja;
    }

    /**
     * Ilmoittaa, onko keko tyhjä.
     *
     * @return true jos keko on tyhjä; false jos keossa on ruutuja.
     */
    public boolean onTyhja() {
        return ruutuja == 0;
    }

}
