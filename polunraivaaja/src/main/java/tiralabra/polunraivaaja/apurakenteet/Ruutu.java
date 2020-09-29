package tiralabra.polunraivaaja.apurakenteet;

/**
 * Kuvaa karttaruutua ja sen sijaintia.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Ruutu {

    public final int y;
    public final int x;

    public Ruutu(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public static Ruutu haeSeuraavaRuutu(Ruutu lahtoruutu, Suunta suunta) {
        int seuraavaY = lahtoruutu.y + suunta.getDY();
        int seuraavaX = lahtoruutu.x + suunta.getDX();
        return new Ruutu(seuraavaY, seuraavaX);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    /**
     * Kaksi ruutua ovat samat, mikäli niiden rivi ja sarake ovat samat.
     *
     * @param obj Verrattava objekti.
     * @return True, jos ruutujen koordinaatit ovat samat; false jos eivät.
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
        final Ruutu other = (Ruutu) obj;
        if (this.y != other.y) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return y + " : " + x;
    }
}
