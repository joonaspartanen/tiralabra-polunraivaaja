package tiralabra.polunraivaaja.mallit;

/**
 * Luokka, joka esittää karttaa binääritaulukkona (0 = kulkuväylä, 1 = este).
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Kartta {

    private int korkeus;
    private int leveys;
    private byte[][] kartta;

    /**
     * Reitinhaun tulosten laskentaa varten karttaa luodessa lasketaan, kuinka monta
     * kuljettavissa olevaa ruutua kartalla on.
     */
    private int vapaitaRuutuja;

    public Kartta(int korkeus, int leveys) {
        this.korkeus = korkeus;
        this.leveys = leveys;
        this.kartta = new byte[korkeus][leveys];
    }

    public byte[][] getKartta() {
        return kartta;
    }

    public int getKorkeus() {
        return korkeus;
    }

    public int getLeveys() {
        return leveys;
    }

    public byte getRuutu(int rivi, int sarake) {
        return kartta[rivi][sarake];
    }

    public void setRuutu(int rivi, int sarake, byte merkki) {
        kartta[rivi][sarake] = merkki;
    }

    public int getVapaitaRuutuja() {
        return vapaitaRuutuja;
    }

    /**
     * Tarkistaa, onko jokin karttaruutu kuljettavissa.
     *
     * @param rivi
     * @param sarake
     * @return
     */
    public boolean ruutuVapaa(int rivi, int sarake) {
        return kartta[rivi][sarake] == 0;
    }

    public void kasvataVapaitaRuutuja() {
        vapaitaRuutuja++;
    }

}
