package tiralabra.polunraivaaja.suorituskykytestit;

public abstract class Suorituskykytulos {

    protected int iteraatioita;

    public Suorituskykytulos(int iteraatioita) {
        this.iteraatioita = iteraatioita;
    }

    protected double aikaMillisekunteina(long aika) {
        return aika / 1000000.0;
    }

}
