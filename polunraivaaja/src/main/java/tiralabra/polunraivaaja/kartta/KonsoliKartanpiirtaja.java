package tiralabra.polunraivaaja.kartta;

import java.util.List;

import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;

public class KonsoliKartanpiirtaja extends Kartanpiirtaja {

    public KonsoliKartanpiirtaja(Kartta kartta) {
        super(kartta);
    }

    public void piirraKartta() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                System.out.print(kartta.getRuutu(i, j));
            }
            System.out.println("");
        }
    }

    public void piirraKartta(List<Koordinaatti> reitti) {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                if (!reitti.contains(new Koordinaatti(i, j))) {
                    piirraRuutu(i, j);
                } else {
                    System.out.print("*");
                }
            }
            System.out.println("");
        }
    }

    private void piirraRuutu(int rivi, int sarake) {
        if (kartta.getRuutu(rivi, sarake) == 0) {
            System.out.print(".");
        } else {
            System.out.print("@");
        }
    }

    @Override
    public void piirraKartta(List<Koordinaatti> reitti, boolean[][] vierailtu) {
        // TODO Auto-generated method stub
    }

}
