package tiralabra.polunraivaaja.apurakenteet;

public class Koordinaatti {

    private int rivi;
    private int sarake;

    public Koordinaatti(int rivi, int sarake) {
        this.rivi = rivi;
        this.sarake = sarake;
    }

    public int getRivi() {
        return rivi;
    }

    public int getSarake() {
        return sarake;
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
     * Kaksi koordinaattia ovat samat, mikäli niiden rivi ja sarake on sama.
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
        Koordinaatti toinen = (Koordinaatti) obj;
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
        return rivi + ", " + sarake;
    }

}
