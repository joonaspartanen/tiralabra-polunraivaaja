package tiralabra.polunraivaaja.mallit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SuuntaTest {

    @Test
    public void laskeeOikeinSuunnanOIKEA() {
        Ruutu lahto = new Ruutu(0, 0);
        Ruutu kohde = new Ruutu(0, 1);

        Suunta suunta = Suunta.laskeSuunta(lahto, kohde);

        assertEquals(Suunta.OIKEA, suunta);
    }

    @Test
    public void laskeeOikeinSuunnanVASEN() {
        Ruutu lahto = new Ruutu(1, 1);
        Ruutu kohde = new Ruutu(1, 0);

        Suunta suunta = Suunta.laskeSuunta(lahto, kohde);

        assertEquals(Suunta.VASEN, suunta);
    }

    @Test
    public void laskeeOikeinSuunnanYLOS() {
        Ruutu lahto = new Ruutu(1, 1);
        Ruutu kohde = new Ruutu(0, 1);

        Suunta suunta = Suunta.laskeSuunta(lahto, kohde);

        assertEquals(Suunta.YLOS, suunta);
    }

    @Test
    public void laskeeOikeinSuunnanALAS() {
        Ruutu lahto = new Ruutu(0, 0);
        Ruutu kohde = new Ruutu(1, 0);

        Suunta suunta = Suunta.laskeSuunta(lahto, kohde);

        assertEquals(Suunta.ALAS, suunta);
    }

    @Test
    public void laskeeOikeinSuunnanYLAOIKEA() {
        Ruutu lahto = new Ruutu(2, 2);
        Ruutu kohde = new Ruutu(1, 3);

        Suunta suunta = Suunta.laskeSuunta(lahto, kohde);

        assertEquals(Suunta.YLAOIKEA, suunta);
    }

    @Test
    public void laskeeOikeinSuunnanYLAVASEN() {
        Ruutu lahto = new Ruutu(2, 2);
        Ruutu kohde = new Ruutu(1, 1);

        Suunta suunta = Suunta.laskeSuunta(lahto, kohde);

        assertEquals(Suunta.YLAVASEN, suunta);
    }

    @Test
    public void laskeeOikeinSuunnanALAOIKEA() {
        Ruutu lahto = new Ruutu(0, 0);
        Ruutu kohde = new Ruutu(1, 1);

        Suunta suunta = Suunta.laskeSuunta(lahto, kohde);

        assertEquals(Suunta.ALAOIKEA, suunta);
    }

    @Test
    public void laskeeOikeinSuunnanALAVASEN() {
        Ruutu lahto = new Ruutu(2, 2);
        Ruutu kohde = new Ruutu(3, 1);

        Suunta suunta = Suunta.laskeSuunta(lahto, kohde);

        assertEquals(Suunta.ALAVASEN, suunta);
    }
}
