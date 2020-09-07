package tiralabra.polunraivaaja.kartta;

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

    public void taytaRivi(int rivinumero, String rivi) {
        for (int i = 0; i < leveys; i++) {
            kartta[rivinumero][i] = (byte) (rivi.charAt(i) == 64 ? 1 : 0);
        }
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

    public boolean ruutuVapaa(int rivi, int sarake) {
        return kartta[rivi][sarake] == 0;
    }

}
