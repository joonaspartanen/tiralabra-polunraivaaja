package tiralabra.polunraivaaja.mallit;

/**
 * Apuluokka, joka tarjoaa hakualgoritmeille tiedon mahdollisista kulkusuunnista
 * karttaruudukolla (ylös, alas, vasen, oikea, kulmittain).
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public enum Suunta {
    YLOS(0, -1, false), OIKEA(1, 0, false), ALAS(0, 1, false), VASEN(-1, 0, false), YLAOIKEA(1, -1, true),
    ALAOIKEA(1, 1, true), ALAVASEN(-1, 1, true), YLAVASEN(-1, -1, true);

    private final int dx;
    private final int dy;
    private final boolean diagonaalinen;

    Suunta(int dx, int dy, boolean diagonaalinen) {
        this.dx = dx;
        this.dy = dy;
        this.diagonaalinen = diagonaalinen;
    }

    public int getDX() {
        return dx;
    }

    public int getDY() {
        return dy;
    }

    public boolean isDiagonaalinen() {
        return diagonaalinen;
    }

    public static int laskeDX(Ruutu alku, Ruutu kohde) {
        return kohde.x - alku.x;
    }

    public static int laskeDY(Ruutu alku, Ruutu kohde) {
        return kohde.y - alku.y;
    }

    public static Suunta laskeSuunta(Ruutu lahto, Ruutu kohde) {
        if (laskeDX(lahto, kohde) > 0 && laskeDY(lahto, kohde) == 0) {
            return OIKEA;
        }
        if (laskeDX(lahto, kohde) < 0 && laskeDY(lahto, kohde) == 0) {
            return VASEN;
        }
        if (laskeDX(lahto, kohde) == 0 && laskeDY(lahto, kohde) > 0) {
            return ALAS;
        }
        if (laskeDX(lahto, kohde) == 0 && laskeDY(lahto, kohde) < 0) {
            return YLOS;
        }
        if (laskeDX(lahto, kohde) > 0 && laskeDY(lahto, kohde) < 0) {
            return YLAOIKEA;
        }
        if (laskeDX(lahto, kohde) < 0 && laskeDY(lahto, kohde) < 0) {
            return YLAVASEN;
        }
        if (laskeDX(lahto, kohde) > 0 && laskeDY(lahto, kohde) > 0) {
            return ALAOIKEA;
        }
        if (laskeDX(lahto, kohde) < 0 && laskeDY(lahto, kohde) > 0) {
            return ALAVASEN;
        }
        return null;
    }

    // Vanha implementaatio, joka on vielä käytössä Leveyshaussa.
    public static final int[] RIVISIIRTYMAT = { -1, 0, 0, 1, -1, -1, 1, 1 };
    public static final int[] SARAKESIIRTYMAT = { 0, -1, 1, 0, -1, 1, -1, 1 };
}
