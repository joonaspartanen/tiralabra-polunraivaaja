/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.polunraivaaja.algoritmit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.apurakenteet.RuutuLista;
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

        leveyshaku.etsiReitti(new Ruutu(0, 0), new Ruutu(4, 4));

        RuutuLista reitti = leveyshaku.getReitti();
        assertTrue(reitti.getRuutuja() > 0);
    }

    @Test
    public void loytaaLyhimmänReitinTriviaaliKartta() {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_5.map");
        Haku leveyshaku = new Leveyshaku(kartta);

        leveyshaku.etsiReitti(new Ruutu(0, 0), new Ruutu(4, 4));

        RuutuLista reitti = leveyshaku.getReitti();
        assertThat(reitti.getRuutuja(), is(9));
    }

    @Test
    public void loytaaLyhimmanReitinHelppoKartta() {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_10.map");
        Haku leveyshaku = new Leveyshaku(kartta);

        leveyshaku.etsiReitti(new Ruutu(0, 0), new Ruutu(9, 9));

        RuutuLista reitti = leveyshaku.getReitti();
        assertThat(reitti.getRuutuja(), is(19));
    }

    @Test
    public void loytaaLyhimmanReitinVaikeaKartta() {
        Kartta kartta = lukija.lueKarttatiedosto("Berlin_0_512.map");
        Haku leveyshaku = new Leveyshaku(kartta);

        leveyshaku.etsiReitti(new Ruutu(0, 0), new Ruutu(511, 511));

        RuutuLista reitti = leveyshaku.getReitti();
        assertThat(reitti.getRuutuja(), is(1023));
    }

    @Test
    public void ilmoittaaJosReittiEiMahdollinen() {
        Kartta kartta = lukija.lueKarttatiedosto("test_1_10.map");
        Haku leveyshaku = new Leveyshaku(kartta);

        Hakutulos tulos = leveyshaku.etsiReitti(new Ruutu(0, 0), new Ruutu(9, 9));

        assertFalse(tulos.isOnnistui());
    }

    @Test
    public void ilmoittaaJosReitinPaatEivatKelpaa() {
        Kartta kartta = lukija.lueKarttatiedosto("test_1_10.map");
        Haku leveyshaku = new Leveyshaku(kartta);

        Hakutulos tulos = leveyshaku.etsiReitti(new Ruutu(0, 0), new Ruutu(3, 2));

        assertFalse(tulos.isOnnistui());
    }

}
