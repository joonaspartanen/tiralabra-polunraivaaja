package tiralabra.polunraivaaja.kartta;

/**
 * Luokka, joka esittää karttaa binääritaulukkona (0 = kulkuväylä, 1 = este).
 * TODO: Muuta kartan esitysmuoto niin, että 0 = este. Näin on helpompi esittää
 * erilaisia maastoja (1=helppokulkuinen, esim tie, 2=vaikeakulkuisempi, jne.)
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Kartta {

    private int korkeus;
    private int leveys;
    private byte[][] kartta;

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

}
