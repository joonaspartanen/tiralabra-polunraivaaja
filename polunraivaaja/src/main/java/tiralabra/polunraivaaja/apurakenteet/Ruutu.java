package tiralabra.polunraivaaja.apurakenteet;

/**
 * Kuvaa karttaruutua ja sen sijaintia. Paino-muuttujaa ei vielä hyödynnetä,
 * mutta se mahdollistaisi erilaisten "maastojen" kuvaamisen.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Ruutu implements Comparable<Ruutu> {

    private int rivi;
    private int sarake;
    private int paino;

    public Ruutu(int rivi, int sarake) {
        this.rivi = rivi;
        this.sarake = sarake;
    }

    public static Ruutu haeSeuraavaRuutu(Ruutu lahtoruutu, Suunta suunta) {
        int seuraavaY = lahtoruutu.getRivi() + suunta.getDY();
        int seuraavaX = lahtoruutu.getSarake() + suunta.getDX();
        return new Ruutu(seuraavaY, seuraavaX);
    }

    public int getRivi() {
        return rivi;
    }

    public int getSarake() {
        return sarake;
    }

    public int getPaino() {
        return paino;
    }

    @Override
    public int hashCode() {
        final int alkuluku = 31;
        int tulos = 1;
        tulos = alkuluku * tulos + rivi;
        tulos = alkuluku * tulos + sarake;
        return tulos;
    }

    /**
     * Kaksi ruutua ovat samat, mikäli niiden rivi ja sarake on sama.
     *
     * @param obj Verrattava objekti.
     * @return True, jos koordinaatit ovat samat; false jos eivät.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Ruutu toinen = (Ruutu) obj;
        if (rivi != toinen.rivi) {
            return false;
        }
        if (sarake != toinen.sarake) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return rivi + " : " + sarake;
    }

    @Override
    public int compareTo(Ruutu toinen) {
        return toinen.paino - this.paino;
    }

}
