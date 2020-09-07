package tiralabra.polunraivaaja.apurakenteet;

import tiralabra.polunraivaaja.kartta.Kartta;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class KoordinaattiTest {

    @Test
    public void equalsToimiiOikein() {
        Koordinaatti ruutu = new Koordinaatti(2, 4);
        Koordinaatti samaRuutu = new Koordinaatti(2, 4);
        Koordinaatti toinenRuutu = new Koordinaatti(3, 3);
        Koordinaatti kolmasRuutu = new Koordinaatti(2, 3);

        assertThat(ruutu, is(equalTo(ruutu)));
        assertThat(ruutu, is(equalTo(samaRuutu)));
        assertThat(ruutu, is(not(equalTo(toinenRuutu))));
        assertThat(ruutu, is(not(equalTo(kolmasRuutu))));
        assertThat(ruutu, is(not(equalTo(new Kartta(10, 10)))));
        assertThat(ruutu, is(not(equalTo(null))));
    }
}
