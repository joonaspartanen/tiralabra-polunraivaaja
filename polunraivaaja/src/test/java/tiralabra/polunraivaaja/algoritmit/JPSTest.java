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
import static org.hamcrest.Matchers.closeTo;

import org.junit.Before;
import org.junit.Test;

import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.mallit.Hakutulos;
import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuLista;
import tiralabra.polunraivaaja.io.Kartanlukija;
import tiralabra.polunraivaaja.io.Tiedostonlukupoikkeus;

/**
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 *
 *         JPS taitaa jo löytää oikean reitin, muttei osaa vielä laskea reitin
 *         pituutta oikein.
 */
public class JPSTest {

    private Kartanlukija lukija;

    @Before
    public void setUp() {
        lukija = new Kartanlukija("kartat/testikartat");
    }

    @Test
    public void loytaaReitinTriviaaliKartta() throws Tiedostonlukupoikkeus {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_5.map");
        Haku haku = new JPS(kartta);

        haku.etsiReitti(new Ruutu(0, 0), new Ruutu(4, 4));

        RuutuLista reitti = haku.getReitti();
        assertTrue(reitti.haePituus() > 0);
    }

    @Test
    public void loytaaLyhimmänReitinDiagonaalisiirtymilläTriviaaliKartta() throws Tiedostonlukupoikkeus {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_5.map");
        Haku haku = new JPS(kartta);
        haku.setSalliDiagonaalisiirtymat(true);

        haku.etsiReitti(new Ruutu(0, 0), new Ruutu(4, 4));

        RuutuLista reitti = haku.getReitti();
        assertThat(reitti.haePituus(), is(5));
    }

    @Test
    public void loytaaLyhimmanReitinDiagonaalisiirtymillaHelppoKartta() throws Tiedostonlukupoikkeus {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_10.map");
        Haku haku = new JPS(kartta);
        haku.setSalliDiagonaalisiirtymat(true);

        haku.etsiReitti(new Ruutu(0, 0), new Ruutu(9, 9));

        RuutuLista reitti = haku.getReitti();
        assertThat(reitti.haePituus(), is(17));
    }

    @Test
    public void loytaaLyhimmanReitinDiagonaalisiirtymillaVaikeaKartta() throws Tiedostonlukupoikkeus {
        Kartta kartta = lukija.lueKarttatiedosto("Berlin_0_512.map");
        Haku haku = new JPS(kartta);
        haku.setSalliDiagonaalisiirtymat(true);

        haku.etsiReitti(new Ruutu(0, 0), new Ruutu(511, 511));

        RuutuLista reitti = haku.getReitti();
        assertThat(reitti.haePituus(), is(633));

        Hakutulos tulos = haku.etsiReitti(new Ruutu(15, 443), new Ruutu(219, 466));

        assertThat(tulos.getReitinPituus(), is(closeTo(227.61017303, 0.001)));

        tulos = haku.etsiReitti(new Ruutu(57, 75), new Ruutu(108, 235));

        assertThat(tulos.getReitinPituus(), is(closeTo(181.12489166, 0.001)));
    }

    @Test
    public void ilmoittaaJosReittiEiMahdollinen() throws Tiedostonlukupoikkeus {
        Kartta kartta = lukija.lueKarttatiedosto("test_1_10.map");
        Haku haku = new JPS(kartta);

        Hakutulos tulos = haku.etsiReitti(new Ruutu(0, 0), new Ruutu(9, 9));

        assertFalse(tulos.isOnnistui());
    }

    @Test
    public void ilmoittaaJosReitinPaatEivatKelpaa() throws Tiedostonlukupoikkeus {
        Kartta kartta = lukija.lueKarttatiedosto("test_1_10.map");
        Haku haku = new JPS(kartta);

        Hakutulos tulos = haku.etsiReitti(new Ruutu(0, 0), new Ruutu(3, 2));

        assertFalse(tulos.isOnnistui());
    }
}
