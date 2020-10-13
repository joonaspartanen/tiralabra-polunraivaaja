package tiralabra.polunraivaaja.suorituskykytestit.algoritmit;

import tiralabra.polunraivaaja.suorituskykytestit.Suorituskykytulos;

public class Mittaustulos extends Suorituskykytulos {

    private long aika;

    public Mittaustulos(long aika, int iteraatioita) {
        super(iteraatioita);
        this.aika = aika;
    }

    @Override
    public String toString() {
        return "SUORITUSKYKYVERTAILU: \n" + "Iteraatioita: " + iteraatioita + "\n" + "Algoritmin suoritusaika: "
                + aikaMillisekunteina(aika) + " ms \n";
    }

}
