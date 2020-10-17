package tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet;

/**
 * Tietorakenteiden suorituskykytestiä kuvaava rajapinta. Testien on aina
 * tarjottava implementaatio testin suorittamiseksi omalla tietorakenteella ja
 * (vastaavalla) Javan valmiilla tietorakenteella.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public interface TietorakenneSuorituskykytesti {

    /**
     * Suorittaa testin Javan valmiilla tietorakenteella.
     *
     * @param iteraatiot Määrittää, kuinka monta kertaa sama testattavat operaatiot
     *                   suoritetaan.
     * @return Suoritukseen kulunut aika.
     */
    long suoritaJavaRakenteella(int iteraatiot);

    /**
     * Suorittaa testin omalla tietorakenteella.
     *
     * @param iteraatiot Määrittää, kuinka monta kertaa sama testattavat operaatiot
     *                   suoritetaan.
     * @return Suoritukseen kulunut aika.
     */
    long suoritaOmallaRakenteella(int iteraatiot);
}
