package tiralabra.polunraivaaja.mallit;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class RuutuTest {

    @Test
    public void equalsToimiiOikein() {
        Ruutu ruutu = new Ruutu(2, 4);
        Ruutu samaRuutu = new Ruutu(2, 4);
        Ruutu toinenRuutu = new Ruutu(3, 3);
        Ruutu kolmasRuutu = new Ruutu(2, 3);

        assertThat(ruutu, is(equalTo(ruutu)));
        assertThat(ruutu, is(equalTo(samaRuutu)));
        assertThat(ruutu, is(equalTo(new Ruutu(2, 4))));
        assertThat(ruutu, is(not(equalTo(toinenRuutu))));
        assertThat(ruutu, is(not(equalTo(kolmasRuutu))));
        assertThat(ruutu, is(not(equalTo(new Kartta(10, 10)))));
        assertThat(ruutu, is(not(equalTo(null))));
    }
}
