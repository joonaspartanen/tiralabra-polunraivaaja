package tiralabra.polunraivaaja.tietorakenteet;

import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tyokalut.Taulukonkasittelija;

/**
 * Yksinkertainen, Ruutu-olioita säilövä lista, joka tarjoaa metodit Ruutujen
 * lisäämiseen ja lukemiseen. Kasvattaa pohjalla olevan taulukon kokoa
 * tarvittaessa.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class RuutuLista {

    /**
     * Taulukko, johon listan sisältämät ruudut säilötään.
     */
    private Ruutu[] lista;

    /**
     * Listan sisältämien ruutujen määrä.
     */
    private int ruutuja;

    /**
     * Konstruktori. Listaan mahtuu aluksi 100 ruutua ja listan kokoa kasvatetaan tarvittaessa.
     */
    public RuutuLista() {
        this.lista = new Ruutu[100];
        ruutuja = 0;
    }

    /**
     * Lisää parametrina annetun ruudun listalle.
     *
     * @param ruutu
     */
    public void lisaaRuutu(Ruutu ruutu) {
        if (Taulukonkasittelija.taulukkoTaynna(lista, ruutuja)) {
            lista = Taulukonkasittelija.kasvataTaulukko(lista);
        }
        lista[ruutuja++] = ruutu;
    }

    /**
     * Palauttaa ruudun, joka sijaitsee listalla parametrina annetussa indeksissä.
     *
     * @param indeksi
     */
    public Ruutu haeRuutuIndeksissa(int indeksi) {
        if (indeksi < 0 || indeksi >= ruutuja) {
            return null;
        }
        return lista[indeksi];
    }

    /**
     * Poistaa listalta ruudun, joka sijaitsee parametrina annetussa indeksissä.
     *
     * @param indeksi
     */
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

    /**
     * Palauttaa listan tämänhetkisen kapasiteetin.
     */
    public int haeKapasiteetti() {
        return lista.length;
    }

    /**
     * Ilmoittaa, kuinka monta ruutua lista sisältää.
     */
    public int haePituus() {
        return ruutuja;
    }
}
