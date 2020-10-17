package tiralabra.polunraivaaja.tietorakenteet;

import tiralabra.polunraivaaja.tyokalut.RuutuKomparaattori;
import tiralabra.polunraivaaja.mallit.Ruutu;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class RuutuKekoTest {

    private RuutuKeko keko;
    private double[][] etaisyys;

    @Before
    public void alustaKeko() {
        etaisyys = new double[5][5];

        for (int i = 0; i < etaisyys.length; i++) {
            for (int j = 0; j < etaisyys.length; j++) {
                etaisyys[i][j] = i + j;
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

        assertThat(keko.haeKoko(), is(3));
    }

    @Test
    public void kekoKasvaaTarvittaessa() {
        for (int i = 0; i < 50; i++) {
            keko.lisaaRuutu(new Ruutu(0, 0));
        }
        assertThat(keko.haeKoko(), is(50));
    }

    @Test
    public void oikeaAlkioLoytyyKeonPaalta() {
        keko.lisaaRuutu(new Ruutu(3, 3));
        keko.lisaaRuutu(new Ruutu(1, 1));
        keko.lisaaRuutu(new Ruutu(2, 2));
        keko.lisaaRuutu(new Ruutu(2, 2));
        keko.lisaaRuutu(new Ruutu(4, 2));
        keko.lisaaRuutu(new Ruutu(3, 2));

        Ruutu ruutu = keko.kurkistaKekoon();
        assertThat(ruutu.y, is(1));
        assertThat(ruutu.x, is(1));
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

        assertThat(keko.haeKoko(), is(3));

        ruutu = keko.kurkistaKekoon();
        assertThat(ruutu.y, is(2));
        assertThat(ruutu.x, is(2));
    }

    @Test
    public void oikeaRuutuNouseeKeonPäälleKunPäällimmäinenPoistetaan() {
        keko.lisaaRuutu(new Ruutu(3, 3));
        keko.lisaaRuutu(new Ruutu(1, 1)); // Pienin
        keko.lisaaRuutu(new Ruutu(4, 2));
        keko.lisaaRuutu(new Ruutu(2, 2)); // 3. pienin
        keko.lisaaRuutu(new Ruutu(2, 1)); // 2. pienin
        keko.lisaaRuutu(new Ruutu(1, 4));

        keko.otaKeosta();
        assertThat(keko.haeKoko(), is(5));

        Ruutu ruutu = keko.kurkistaKekoon();
        assertThat(ruutu.y, is(2));
        assertThat(ruutu.x, is(1));

        keko.otaKeosta();

        ruutu = keko.kurkistaKekoon();
        assertThat(ruutu.y, is(2));
        assertThat(ruutu.x, is(2));
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
