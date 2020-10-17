package tiralabra.polunraivaaja.tietorakenteet;

import tiralabra.polunraivaaja.mallit.Ruutu;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class RuutuListaTest {

  @Test
  public void listaKasvaaTarvittaessa() {
    RuutuLista lista = new RuutuLista();

    assertThat(lista.haeKapasiteetti(), is(100));

    for (int i = 0; i < 150; i++) {
      lista.lisaaRuutu(new Ruutu(0, 0));
    }

    assertTrue(lista.haeKapasiteetti() > 100); 
    assertThat(lista.haePituus(), is(150));
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

  @Test
  public void haeRuutuNegatiivisestaIndeksistaPalauttaaNull() {
    RuutuLista lista = new RuutuLista();

    assertThat(lista.haeRuutuIndeksissa(-1), is(nullValue()));
  }

  @Test
  public void haeRuutuLiianSuurestaIndeksistaPalauttaaNull() {
    RuutuLista lista = new RuutuLista();
    lista.lisaaRuutu(new Ruutu(0, 0));

    assertThat(lista.haeRuutuIndeksissa(10), is(nullValue()));
    assertThat(lista.haeRuutuIndeksissa(100), is(nullValue()));
  }

  @Test
  public void listaltaVoiPoistaaRuudun() {
    RuutuLista lista = new RuutuLista();

    for (int i = 0; i < 5; i++) {
      lista.lisaaRuutu(new Ruutu(i, i));
    }

    assertThat(lista.haePituus(), is(5));

    Ruutu ruutu = lista.poistaRuutuIndeksissa(0);

    assertThat(ruutu.getX(), is(0));
    assertThat(ruutu.getY(), is(0));
    assertThat(lista.haePituus(), is(4));

    ruutu = lista.poistaRuutuIndeksissa(2);

    assertThat(ruutu.getX(), is(3));
    assertThat(ruutu.getY(), is(3));
    assertThat(lista.haePituus(), is(3));
  }
}
