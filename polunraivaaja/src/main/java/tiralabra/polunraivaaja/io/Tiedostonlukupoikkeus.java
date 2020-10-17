package tiralabra.polunraivaaja.io;

/**
 * Luokka, johon tiedostonlukemiseen liittyvät poikkeukset kääritään.
 */
public class Tiedostonlukupoikkeus extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Konstruktori.
   * 
   * @param viesti               Informatiivinen viesti, joka näytetään
   *                             käyttäjälle.
   * @param alkuperainenPoikkeus Alkuperainen poikkeus säilytetään debuggausta
   *                             varten.
   */
  public Tiedostonlukupoikkeus(String viesti, Throwable alkuperainenPoikkeus) {
    super(viesti, alkuperainenPoikkeus);
  }
}
