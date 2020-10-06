package tiralabra.polunraivaaja.tietorakenteet;

import tiralabra.polunraivaaja.mallit.Ruutu;

/**
 * Ruutu-olioita säilövä jono. Mahdollistaa ruutujen lisäämisen jonoon ja jonon
 * etummaisen ruudun poistamisen jonosta.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class RuutuJono {

    private RuutuLista lista;

    public RuutuJono() {
        this.lista = new RuutuLista();
    }

    /**
     * Lisää parametrina annetun ruudun jonoon.
     *
     * @param ruutu
     */
    public void lisaaJonoon(Ruutu ruutu) {
        lista.lisaaRuutu(ruutu);
    }

    /**
     * Palauttaa jonon kärjessä olevan ruudun ja poistaa sen jonosta.
     * 
     * @return Jonon etummainen (=pisimpään jonossa ollut) ruutu.
     */
    public Ruutu otaJonosta() {
        return lista.haePituus() > 0 ? lista.poistaRuutuIndeksissa(0) : null;
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
        return haePituus() == 0;
    }
}
