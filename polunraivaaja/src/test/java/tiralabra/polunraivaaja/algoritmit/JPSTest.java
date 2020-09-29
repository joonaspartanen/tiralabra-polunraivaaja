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
import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Ruutu;
import tiralabra.polunraivaaja.io.Kartanlukija;

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
    public void loytaaReitinTriviaaliKartta() {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_5.map");
        Haku haku = new JPS(kartta);

        haku.etsiReitti(new Ruutu(0, 0), new Ruutu(4, 4));

        List<Ruutu> reitti = haku.getReitti();
        assertFalse(reitti.isEmpty());
    }

    @Test
    public void loytaaLyhimmänReitinDiagonaalisiirtymilläTriviaaliKartta() {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_5.map");
        Haku haku = new JPS(kartta);
        haku.setSalliDiagonaalisiirtymat(true);

        haku.etsiReitti(new Ruutu(0, 0), new Ruutu(4, 4));

        List<Ruutu> reitti = haku.getReitti();
        assertThat(reitti.size(), is(5));
    }

    @Test
    public void loytaaLyhimmanReitinDiagonaalisiirtymillaHelppoKartta() {
        Kartta kartta = lukija.lueKarttatiedosto("test_0_10.map");
        Haku haku = new JPS(kartta);
        haku.setSalliDiagonaalisiirtymat(true);

        haku.etsiReitti(new Ruutu(0, 0), new Ruutu(9, 9));

        List<Ruutu> reitti = haku.getReitti();
        assertThat(reitti.size(), is(17));
    }

    @Test
    public void loytaaLyhimmanReitinDiagonaalisiirtymillaVaikeaKartta() {
        Kartta kartta = lukija.lueKarttatiedosto("Berlin_0_512.map");
        Haku haku = new JPS(kartta);
        haku.setSalliDiagonaalisiirtymat(true);

        haku.etsiReitti(new Ruutu(0, 0), new Ruutu(511, 511));

        List<Ruutu> reitti = haku.getReitti();
        assertThat(reitti.size(), is(633));
    }

    @Test
    public void ilmoittaaJosReittiEiMahdollinen() {
        Kartta kartta = lukija.lueKarttatiedosto("test_1_10.map");
        Haku haku = new JPS(kartta);

        Hakutulos tulos = haku.etsiReitti(new Ruutu(0, 0), new Ruutu(9, 9));

        assertFalse(tulos.isOnnistui());
    }

    @Test
    public void ilmoittaaJosReitinPaatEivatKelpaa() {
        Kartta kartta = lukija.lueKarttatiedosto("test_1_10.map");
        Haku haku = new JPS(kartta);

        Hakutulos tulos = haku.etsiReitti(new Ruutu(0, 0), new Ruutu(3, 2));

        assertFalse(tulos.isOnnistui());
    }
}
