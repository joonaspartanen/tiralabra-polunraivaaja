package tiralabra.polunraivaaja.apurakenteet;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class RuutuKekoTest {

    private RuutuKeko keko;
    private double[][] etaisyys;

    @Before
    public void alustaKeko() {
        etaisyys = new double[5][5];

        int k = 1;

        for (int i = 0; i < etaisyys.length; i++) {
            for (int j = 0; j < etaisyys.length; j++) {
                etaisyys[i][j] = k++;
            }
        }

        RuutuKomparaattori komparaattori = new RuutuKomparaattori(etaisyys);

        this.keko = new RuutuKeko(komparaattori);
    }

    @Test
    public void kekoonVoiLisataRuutuja() {
        keko.lisaaRuutu(new Ruutu(1, 1));
        keko.lisaaRuutu(new Ruutu(2, 2));
        keko.lisaaRuutu(new Ruutu(3, 3));

        assertThat(keko.haePituus(), is(3));
    }

    @Test
    public void oikeaAlkioLoytyyKeonPaalta() {
        keko.lisaaRuutu(new Ruutu(3, 3));
        keko.lisaaRuutu(new Ruutu(1, 1));
        keko.lisaaRuutu(new Ruutu(2, 2));

        Ruutu ruutu = keko.kurkistaKekoon();
        assertThat(ruutu.x, is(1));
        assertThat(ruutu.y, is(1));
    }

    @Test
    public void paallimmaisenRuudunVoiPoistaaKeosta() {
        keko.lisaaRuutu(new Ruutu(3, 3));
        keko.lisaaRuutu(new Ruutu(1, 1));
        keko.lisaaRuutu(new Ruutu(4, 2));
        keko.lisaaRuutu(new Ruutu(2, 2));

        Ruutu ruutu = keko.otaKeosta();
        assertThat(ruutu.x, is(1));
        assertThat(ruutu.y, is(1));

        assertThat(keko.haePituus(), is(3));
    }

    // Apumetodi debuggaukseen.
    private void tulostaEtaisyysTaulu() {
        for (int i = 0; i < etaisyys.length; i++) {
            for (int j = 0; j < etaisyys.length; j++) {
                System.out.print(etaisyys[i][j] + "\t");
            }
            System.out.println("");
        }
    }
}
