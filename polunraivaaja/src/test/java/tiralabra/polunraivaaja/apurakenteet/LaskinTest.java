package tiralabra.polunraivaaja.apurakenteet;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

public class LaskinTest {

  @Test
  public void laskeItseisarvoToimiiPositiivisellaLuvulla() {
    assertThat(Laskin.laskeItseisarvo(3), is(3));
  }

  @Test
  public void laskeItseisarvoToimiiNegatiivisellaLuvulla() {
    assertThat(Laskin.laskeItseisarvo(-5), is(5));
  }

  @Test
  public void pyoristaKokonaislukuunToimiiOikein() {
    assertThat(Laskin.pyoristaKokonaislukuun(3.25), is(3));
    assertThat(Laskin.pyoristaKokonaislukuun(3.5), is(4));
    assertThat(Laskin.pyoristaKokonaislukuun(4.75), is(5));
    assertThat(Laskin.pyoristaKokonaislukuun(-4.8), is(-5));
    assertThat(Laskin.pyoristaKokonaislukuun(-4.5), is(-5));
    assertThat(Laskin.pyoristaKokonaislukuun(-4.2), is(-4));
  }

  @Test
  public void valitsePienempiToimiiOikein() {
    assertThat(Laskin.valitsePienempi(1, 2), is(1));
    assertThat(Laskin.valitsePienempi(3, 2), is(2));
    assertThat(Laskin.valitsePienempi(-1, 0), is(-1));
    assertThat(Laskin.valitsePienempi(0, 0), is(0));
  }
}
