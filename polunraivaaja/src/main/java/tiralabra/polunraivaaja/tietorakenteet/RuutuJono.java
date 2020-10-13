package tiralabra.polunraivaaja.tietorakenteet;

import tiralabra.polunraivaaja.mallit.Ruutu;

/**
 * Ruutu-olioita säilövä jono. Mahdollistaa ruutujen lisäämisen jonoon ja jonon
 * etummaisen ruudun poistamisen jonosta.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class RuutuJono {

    private final RuutuLista lista;
    private int vanhimmanIndeksi;
    private int ruutuja;

    public RuutuJono() {
        this.lista = new RuutuLista();
        this.vanhimmanIndeksi = 0;
        this.ruutuja = 0;
    }

    /**
     * Lisää parametrina annetun ruudun jonoon.
     *
     * @param ruutu
     */
    public void lisaaJonoon(Ruutu ruutu) {
        lista.lisaaRuutu(ruutu);
        ruutuja++;
    }

    /**
     * Palauttaa jonon kärjessä olevan ruudun ja poistaa sen jonosta.
     * 
     * @return Jonon etummainen (=pisimpään jonossa ollut) ruutu.
     */
    public Ruutu otaJonosta() {
        ruutuja--;
        return lista.haeRuutuIndeksissa(vanhimmanIndeksi++);
    }

    /**
     * Ilmoittaa, kuinka monta ruutua jono sisältää.
     * 
     * @return Jonon pituus.
     */
    public int haePituus() {
        return lista.haePituus();
    }

    /**
     * Ilmoittaa, onko jono tyhjä.
     * 
     * @return true jos jono on tyhjä; false jos jonossa on ruutuja.
     */
    public boolean onTyhja() {
        return ruutuja == 0;
    }
}
