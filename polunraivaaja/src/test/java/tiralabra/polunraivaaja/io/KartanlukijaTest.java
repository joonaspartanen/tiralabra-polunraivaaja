package tiralabra.polunraivaaja.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;

import tiralabra.polunraivaaja.kartta.Kartta;

public class KartanlukijaTest {

  private Kartanlukija lukija;

  @Before
  public void setUp() {
    lukija = new Kartanlukija("kartat/testikartat");
  }

  @Test
  public void lukeeKartanKoonOikein() {
    Kartta kartta = lukija.lueKarttatiedosto("Berlin_0_512.map");

    assertThat(kartta.getKorkeus(), is(512));
    assertThat(kartta.getLeveys(), is(512));
  }

  @Test
  public void lukeeKartanOikeinTriviaaliKartta() {
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
  public void lukeeKartanOikeinHelppoKartta() {
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
}
