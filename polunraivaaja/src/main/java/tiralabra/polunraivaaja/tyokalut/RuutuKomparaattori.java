package tiralabra.polunraivaaja.tyokalut;

import tiralabra.polunraivaaja.mallit.Ruutu;

/**
 * RuutuKeon käyttämä apuluokka, joka mahdollistaa ruutujen järjestämisen
 * vertailuperusteet sisältävän taulukon perusteella.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class RuutuKomparaattori {

    private double[][] vertailuperuste;

    /**
     * Konstruktori. Ottaa parametrina liukulukutaulukon, jota käytetään
     * vertailuperusteena ruutujen järjestämisessä.
     *
     * @param vertailuperuste Esim. taulukko, joka ruutujen etäisyyden reitin
     *                        alusta.
     */
    public RuutuKomparaattori(double[][] vertailuperuste) {
        this.vertailuperuste = vertailuperuste;
    }

    /**
     * Vertaa parametrina saamiaan ruutuja toisiinsa.
     *
     * @param a
     * @param b
     * @return -1 jos ruutua a vastaava arvo vertailuperustetaulukossa on pienempi
     *         kuin ruutua b vastaava arvo; 1 jos päinvastoin.
     */
    public int vertaaRuutuja(Ruutu a, Ruutu b) {
        if (a == null) {
            return 1;
        }
        if (b == null) {
            return -1;
        }
        return vertailuperuste[a.y][a.x] < vertailuperuste[b.y][b.x] ? -1 : 1;
    }

}
