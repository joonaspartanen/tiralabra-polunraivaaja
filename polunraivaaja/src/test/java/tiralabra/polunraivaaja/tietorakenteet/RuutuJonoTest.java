package tiralabra.polunraivaaja.tietorakenteet;

import tiralabra.polunraivaaja.mallit.Ruutu;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class RuutuJonoTest {

    private RuutuJono jono;

    @Before
    public void luoTestiJono() {
        this.jono = new RuutuJono();
        this.jono.lisaaJonoon(new Ruutu(0, 0));
        this.jono.lisaaJonoon(new Ruutu(1, 1));
        this.jono.lisaaJonoon(new Ruutu(2, 2));
    }

    @Test
    public void ruutujaVoiLisataJonoon() {

        assertThat(jono.haePituus(), is(3));
    }

    @Test
    public void otaJonostaPalauttaaOikeanRuudun() {
        Ruutu ruutu = jono.otaJonosta();
        assertThat(ruutu.x, is(0));
        assertThat(ruutu.y, is(0));

        ruutu = jono.otaJonosta();
        assertThat(ruutu.x, is(1));
        assertThat(ruutu.y, is(1));

        ruutu = jono.otaJonosta();
        assertThat(ruutu.x, is(2));
        assertThat(ruutu.y, is(2));
    }

    @Test
    public void otaJonostaPalauttaaNullJosJonoTyhja() {
        for (int i = 0; i < 3; i++) {
            jono.otaJonosta();
        }
        Ruutu ruutu = jono.otaJonosta();
        assertThat(ruutu, is(nullValue()));
    }
}
