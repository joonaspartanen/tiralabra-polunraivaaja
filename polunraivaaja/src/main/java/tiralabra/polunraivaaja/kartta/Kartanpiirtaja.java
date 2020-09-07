package tiralabra.polunraivaaja.kartta;

import java.util.List;

import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;

public abstract class Kartanpiirtaja {

    protected Kartta kartta;
    protected int korkeus;
    protected int leveys;

    public Kartanpiirtaja(Kartta kartta) {
        this.kartta = kartta;
        this.korkeus = kartta.getKorkeus();
        this.leveys = kartta.getLeveys();
    }

    public abstract void piirraKartta();

    public abstract void piirraKartta(List<Koordinaatti> reitti);
}
