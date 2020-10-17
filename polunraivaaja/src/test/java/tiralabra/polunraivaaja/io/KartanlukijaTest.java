package tiralabra.polunraivaaja.io;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.suorituskykytestit.algoritmit.Reittikuvaus;
import tiralabra.polunraivaaja.suorituskykytestit.algoritmit.Skenaario;

public class KartanlukijaTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private Kartanlukija lukija;
    private String kansio = "kartat/testikartat";

    @Before
    public void setUp() {
        lukija = new Kartanlukija(kansio);
    }

    @Test
    public void lueKarttatiedostoHeittaaOikeanPoikkeuksenJosTiedostoaEiLoydy() throws Tiedostonlukupoikkeus {

        exceptionRule.expect(Tiedostonlukupoikkeus.class);
        exceptionRule.expectMessage(endsWith("ei löytynyt."));
        exceptionRule.expectMessage(containsString("kartat/testikartat/olematonTiedosto.map"));

        lukija.lueKarttatiedosto("olematonTiedosto.map");
    }

    @Test
    public void lukeeKartanKoonOikein() throws Tiedostonlukupoikkeus {
        Kartta kartta = lukija.lueKarttatiedosto("Berlin_0_512.map");

        assertThat(kartta.getKorkeus(), is(512));
        assertThat(kartta.getLeveys(), is(512));
    }

    @Test
    public void lukeeKartanOikeinTriviaaliKartta() throws Tiedostonlukupoikkeus {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_5.map");

        assertThat(kartta.getKorkeus(), is(5));
        assertThat(kartta.getLeveys(), is(5));

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(0, kartta.getRuutu(i, j));
            }
        }
    }

    @Test
    public void lukeeKartanOikeinHelppoKartta() throws Tiedostonlukupoikkeus {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_10.map");

        assertThat(kartta.getKorkeus(), is(10));
        assertThat(kartta.getLeveys(), is(10));
        assertEquals(0, kartta.getRuutu(0, 0));
        assertEquals(0, kartta.getRuutu(1, 0));
        assertEquals(0, kartta.getRuutu(9, 0));
        assertEquals(1, kartta.getRuutu(0, 1));
        assertEquals(1, kartta.getRuutu(1, 2));
        assertEquals(0, kartta.getRuutu(3, 5));
        assertEquals(0, kartta.getRuutu(3, 7));
        assertEquals(1, kartta.getRuutu(4, 7));
    }

    @Test
    public void lukeeSkenaarionOikein() throws Tiedostonlukupoikkeus {
        lukija = new Kartanlukija("kartat");

        Skenaario skenaario = lukija.lueSkenaario("Berlin_0_256.map.scen");

        assertThat(skenaario.getKartta().getKorkeus(), is(256));
        assertThat(skenaario.getKartta().getLeveys(), is(256));

        assertThat(skenaario.getReittikuvaukset().size(), is(930));

        Reittikuvaus kuvaus1 = skenaario.getReittikuvaukset().get(0);
        Reittikuvaus kuvaus2 = skenaario.getReittikuvaukset().get(1);
        Reittikuvaus kuvaus3 = skenaario.getReittikuvaukset().get(2);
        Reittikuvaus kuvaus4 = skenaario.getReittikuvaukset().get(skenaario.getReittikuvaukset().size() - 1);

        assertTrue(tarkistaReittikuvaus(kuvaus1, 248, 165, 249, 164));
        assertTrue(tarkistaReittikuvaus(kuvaus2, 153, 86, 156, 86));
        assertTrue(tarkistaReittikuvaus(kuvaus3, 38, 240, 40, 241));
        assertTrue(tarkistaReittikuvaus(kuvaus4, 9, 25, 245, 251));
    }

    private boolean tarkistaReittikuvaus(Reittikuvaus kuvaus, int alkuX, int alkuY, int loppuX, int loppuY) {
        return kuvaus.getAlku().getX() == alkuX && kuvaus.getAlku().getY() == alkuY && kuvaus.getLoppu().getX() == loppuX
                && kuvaus.getLoppu().getY() == loppuY;
    }
}
