package tiralabra.polunraivaaja.apurakenteet;

public class RuutuLista {

  private Ruutu[] lista;
  private int ruutuja;

  public RuutuLista() {
    this.lista = new Ruutu[40];
    ruutuja = 0;
  }

  public void lisaaRuutu(Ruutu ruutu) {
    if (listaTaynna()) {
      kasvata();
    }
    lista[ruutuja++] = ruutu;
  }

  private boolean listaTaynna() {
    return ruutuja == lista.length;
  }

  private void kasvata() {
    int uusiKoko = lista.length + lista.length / 2;
    Ruutu[] uusiLista = new Ruutu[uusiKoko];

    kopioiTaulukko(lista, uusiLista);

    lista = uusiLista;
  }

  private void kopioiTaulukko(Ruutu[] taulukko, Ruutu[] kopio) {
    for (int i = 0; i < taulukko.length; i++) {
      kopio[i] = taulukko[i];
    }
  }

  public Ruutu haeRuutuIndeksissa(int indeksi) {
    return lista[indeksi];
  }

  public int haeKapasiteetti() {
    return lista.length;
  }

  public int getRuutuja() {
    return ruutuja;
  }
}
