package tiralabra.polunraivaaja.apurakenteet;

/**
 * Kuvaa karttaruutua ja sen sijaintia. Paino-muuttujaa ei vielä hyödynnetä,
 * mutta se mahdollistaisi erilaisten "maastojen" kuvaamisen.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Ruutu {

    private int rivi;
    private int sarake;

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

    @Override
    public int hashCode() {
        return super.hashCode();
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
}
