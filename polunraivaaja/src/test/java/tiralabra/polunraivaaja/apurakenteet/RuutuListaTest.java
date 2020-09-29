package tiralabra.polunraivaaja.apurakenteet;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class RuutuListaTest {

  @Test
  public void listaKasvaaTarvittaessa() {
    RuutuLista lista = new RuutuLista();

    assertThat(lista.haeKapasiteetti(), is(40));

    for (int i = 0; i < 50; i++) {
      lista.lisaaRuutu(new Ruutu(0, 0));
    }

    assertTrue(lista.haeKapasiteetti() > 40); 
    assertThat(lista.getRuutuja(), is(50));
  }

  @Test
  public void listaPalauttaaOikeatRuudut() {
    RuutuLista lista = new RuutuLista();

    for (int i = 0; i < 5; i++) {
      lista.lisaaRuutu(new Ruutu(i, i));
    }

    for (int i = 0; i < 5; i++) {
      Ruutu ruutu = lista.haeRuutuIndeksissa(i);
      assertThat(ruutu.y, is(i));
      assertThat(ruutu.x, is(i));
    }
  }
}
