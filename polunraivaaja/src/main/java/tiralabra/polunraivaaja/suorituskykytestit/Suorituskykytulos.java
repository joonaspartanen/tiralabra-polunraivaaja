package tiralabra.polunraivaaja.suorituskykytestit;

/**
 * Abstrakti yläluokka sekä tietorakenteiden että algoritmien
 * suorituskykytestien tulosten käärimiseksi.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public abstract class Suorituskykytulos {

    /**
     * Suorituskykytestin aikana suoritettujen iteraatioiden määrä.
     */
    protected int iteraatioita;

    /**
     * Konstruktori.
     *
     * @param iteraatioita Suorituskykytestin aikana suoritettujen iteraatioiden
     *                     määrä
     */
    public Suorituskykytulos(int iteraatioita) {
        this.iteraatioita = iteraatioita;
    }

    /**
     * Apumetodi nanosekunteina mitatun ajan muuttamiseksi millisekunneiksi.
     *
     * @param iteraatioita Aika nanosekunteina.
     * @return Aika millisekunteina.
     */
    protected double aikaMillisekunteina(long aika) {
        return aika / 1000000.0;
    }

}
