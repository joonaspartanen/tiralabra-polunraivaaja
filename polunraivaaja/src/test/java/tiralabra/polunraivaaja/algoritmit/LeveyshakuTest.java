/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.polunraivaaja.algoritmit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;
import tiralabra.polunraivaaja.io.Kartanlukija;

/**
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class LeveyshakuTest {

    private Kartanlukija lukija;

    @Before
    public void setUp() {
        lukija = new Kartanlukija("kartat/testikartat");
    }

    @Test
    public void loytaaReitinTriviaaliKartta() {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_5.map");
        Haku leveyshaku = new Leveyshaku(kartta);

        leveyshaku.etsiReitti(new Koordinaatti(0, 0), new Koordinaatti(4, 4));

        List<Koordinaatti> reitti = leveyshaku.getReitti();
        assertFalse(reitti.isEmpty());
    }

    @Test
    public void loytaaLyhimmänReitinTriviaaliKartta() {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_5.map");
        Haku leveyshaku = new Leveyshaku(kartta);

        leveyshaku.etsiReitti(new Koordinaatti(0, 0), new Koordinaatti(4, 4));

        List<Koordinaatti> reitti = leveyshaku.getReitti();
        assertThat(reitti.size(), is(5));
    }

    @Test
    public void loytaaLyhimmanReitinHelppoKartta() {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_10.map");
        Haku leveyshaku = new Leveyshaku(kartta);

        leveyshaku.etsiReitti(new Koordinaatti(0, 0), new Koordinaatti(9, 9));

        List<Koordinaatti> reitti = leveyshaku.getReitti();
        assertThat(reitti.size(), is(17));
    }

}
