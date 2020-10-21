package tiralabra.polunraivaaja.mallit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import tiralabra.polunraivaaja.tietorakenteet.RuutuLista;

public class HakutulosTest {

    @Test
    public void hakutuloksenTulostusOikein() {

        Hakutulos tulos = new Hakutulos(true, "Reitti löytyi.", 10, 20, new RuutuLista(), new boolean[10][10], 10.0,
                2000000L);

        assertEquals(
                "Reitti löytyi.\nRuutuja tarkasteltu: 10 (~50 % vapaista ruuduista)\nReitin pituus: 10.0\nAikaa kului: 2 ms",
                tulos.toString());
    }

}
