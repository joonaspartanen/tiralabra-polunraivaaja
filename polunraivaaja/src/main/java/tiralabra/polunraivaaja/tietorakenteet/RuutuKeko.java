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

    private Ruutu[] keko;
    private int ruutuja;
    private final RuutuKomparaattori komparaattori;

    public RuutuKeko(RuutuKomparaattori komparaattori) {
        this.keko = new Ruutu[40];
        this.ruutuja = 0;
        this.komparaattori = komparaattori;
    }

    /**
     * Lisää parametrina annetun ruudun oikealle paikalle kekoon.
     *
     * @param ruutu
     */
    public void lisaaRuutu(Ruutu ruutu) {
        if (Taulukonkasittelija.taulukkoTaynna(keko, ruutuja + 1)) {
            keko = Taulukonkasittelija.kasvataTaulukko(keko);
        }

        int indeksi = ++ruutuja;
        keko[indeksi] = ruutu;

        if (ruutuja == 1) {
            return;
        }

        int vanhemmanIndeksi = laskeVanhemmanIndeksi(indeksi);

        while (komparaattori.vertaaRuutuja(keko[vanhemmanIndeksi], keko[indeksi]) == 1) {
            vaihdaRuudutKeskenaan(vanhemmanIndeksi, indeksi);
            indeksi = vanhemmanIndeksi;
            vanhemmanIndeksi = laskeVanhemmanIndeksi(indeksi);
            if (vanhemmanIndeksi == 0) {
                break;
            }
        }
    }

    private int laskeVasemmanLapsenIndeksi(int indeksi) {
        return 2 * indeksi;
    }

    private int laskeOikeanLapsenIndeksi(int indeksi) {
        return 2 * indeksi + 1;
    }

    private int laskeVanhemmanIndeksi(int indeksi) {
        return indeksi / 2;
    }

    private void vaihdaRuudutKeskenaan(int indeksi1, int indeksi2) {
        if (indeksi1 > ruutuja || indeksi2 > ruutuja) {
            return;
        }
        Ruutu temp = keko[indeksi1];
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
        Ruutu ruutu = keko[1];

        poistaPaallimmainenRuutu();

        return ruutu;
    }

    private void poistaPaallimmainenRuutu() {
        int indeksi = 1;

        Ruutu pohjimmainen = keko[ruutuja];
        keko[indeksi] = pohjimmainen;
        keko[ruutuja--] = null;

        int sopivammanLapsenIndeksi = laskeSopivammanLapsenIndeksi(indeksi);
        Ruutu sopivampiLapsi = keko[sopivammanLapsenIndeksi];

        while (komparaattori.vertaaRuutuja(keko[indeksi], sopivampiLapsi) == 1) {
            vaihdaRuudutKeskenaan(indeksi, sopivammanLapsenIndeksi);
            indeksi = sopivammanLapsenIndeksi;
            sopivammanLapsenIndeksi = laskeSopivammanLapsenIndeksi(indeksi);
            if (indeksi > ruutuja) {
                break;
            }
        }
    }

    private int laskeSopivammanLapsenIndeksi(int indeksi) {
        int pienemmanLapsenIndeksi;
        Ruutu vasenLapsi;
        Ruutu oikeaLapsi;

        int vasemmanLapsenIndeksi = laskeVasemmanLapsenIndeksi(indeksi);
        int oikeanLapsenIndeksi = laskeOikeanLapsenIndeksi(indeksi);

        if (vasemmanLapsenIndeksi > ruutuja) {
            vasenLapsi = null;
        } else {
            vasenLapsi = keko[vasemmanLapsenIndeksi];
        }

        if (oikeanLapsenIndeksi > ruutuja) {
            oikeaLapsi = null;
        } else {
            oikeaLapsi = keko[oikeanLapsenIndeksi];
        }

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
