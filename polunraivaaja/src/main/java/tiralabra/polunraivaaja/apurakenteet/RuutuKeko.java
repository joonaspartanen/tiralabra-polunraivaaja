package tiralabra.polunraivaaja.apurakenteet;

public class RuutuKeko {

    private Ruutu[] keko;
    private int ruutuja;
    private RuutuKomparaattori komparaattori;

    public RuutuKeko(RuutuKomparaattori komparaattori) {
        this.keko = new Ruutu[40];
        this.ruutuja = 0;
        this.komparaattori = komparaattori;
    }

    public void lisaaRuutu(Ruutu ruutu) {
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

    public Ruutu kurkistaKekoon() {
        return keko[1];
    }

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

        int pienemmanLapsenIndeksi = laskePienemmanLapsenIndeksi(indeksi);
        Ruutu pienempiLapsi = keko[pienemmanLapsenIndeksi];

        while (komparaattori.vertaaRuutuja(keko[indeksi], pienempiLapsi) == 1) {
            vaihdaRuudutKeskenaan(indeksi, pienemmanLapsenIndeksi);
            indeksi = pienemmanLapsenIndeksi;
            pienemmanLapsenIndeksi = laskePienemmanLapsenIndeksi(indeksi);
            if (keko[pienemmanLapsenIndeksi] == null) {
                break;
            }
        }
    }

    private int laskePienemmanLapsenIndeksi(int indeksi) {
        int pienemmanLapsenIndeksi;

        int vasemmanLapsenIndeksi = laskeVasemmanLapsenIndeksi(indeksi);
        int oikeanLapsenIndeksi = laskeOikeanLapsenIndeksi(indeksi);

        Ruutu vasenLapsi = keko[vasemmanLapsenIndeksi];
        Ruutu oikeaLapsi = keko[oikeanLapsenIndeksi];

        if (komparaattori.vertaaRuutuja(vasenLapsi, oikeaLapsi) == -1) {
            pienemmanLapsenIndeksi = vasemmanLapsenIndeksi;
        } else {
            pienemmanLapsenIndeksi = oikeanLapsenIndeksi;
        }

        return pienemmanLapsenIndeksi;
    }

    public int haePituus() {
        return ruutuja;
    }

}
