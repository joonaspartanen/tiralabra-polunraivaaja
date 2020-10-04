package tiralabra.polunraivaaja.apurakenteet;

/**
 * Yksinkertainen, Ruutu-olioita säilövä lista, joka tarjoaa metodit Ruutujen
 * lisäämiseen ja lukemiseen. Kasvattaa pohjalla olevan taulukon kokoa
 * tarvittaessa.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
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
        for (int i = 0; i < ruutuja; i++) {
            kopio[i] = taulukko[i];
        }
    }

    public Ruutu haeRuutuIndeksissa(int indeksi) {
        if (indeksi < 0 || indeksi >= ruutuja) {
            return null;
        }
        return lista[indeksi];
    }

    public Ruutu poistaRuutuIndeksissa(int indeksi) {
        Ruutu ruutu = lista[indeksi];
        siirraVasemmalle(indeksi);
        ruutuja--;
        return ruutu;
    }

    private void siirraVasemmalle(int alkaenIndeksista) {
        for (int i = alkaenIndeksista; i < ruutuja - 1; i++) {
            lista[i] = lista[i + 1];
        }
    }

    public int haeKapasiteetti() {
        return lista.length;
    }

    public int haePituus() {
        return ruutuja;
    }
}
