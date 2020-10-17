package tiralabra.polunraivaaja.tietorakenteet;

import tiralabra.polunraivaaja.mallit.Ruutu;

/**
 * Ruutu-olioita säilövä jono. Mahdollistaa ruutujen lisäämisen jonoon ja jonon
 * etummaisen ruudun poistamisen jonosta. Tämän hetkinen toteutus ei
 * varsinaisesti poista ruutuja pohjalla olevalta listalta, koska siihen kuluisi
 * nyt liikaa aikaa, joten ratkaisu ei ole muistinkäytön kannalta tehokas.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class RuutuJono {

    /**
     * RuutuLista-olio, johon jonon sisältämät ruudut tallennetaan.
     */
    private final RuutuLista lista;

    /**
     * Indeksi, jossa vanhin kullakin hetkellä jonossa oleva ruutu sijaitsee.
     */
    private int vanhimmanIndeksi;

    /**
     * Jonon sisältämien ruutujen määrä.
     */
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
