package tiralabra.polunraivaaja.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import tiralabra.polunraivaaja.mallit.Kartta;
import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.suorituskykytestit.algoritmit.Reittikuvaus;
import tiralabra.polunraivaaja.suorituskykytestit.algoritmit.Skenaario;

/**
 * Lukee tekstitiedostona tallennetun kartan ja muodostaa siitä
 * reitinhakualgoritmien ymmärtämän Kartta-olion.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class Kartanlukija {

    private String kansio;
    private Kartta kartta;
    int korkeus;
    int leveys;

    /**
     * @param kansio Suhteellinen polku kansioon, josta kartat luetaan.
     */
    public Kartanlukija(String kansio) {
        this.kansio = kansio;
    }

    /**
     * Lukee tekstitiedostona tallennetun kartan.
     *
     * @param tiedostonimi Luettavan kartan tiedostonimi.
     * @return Luetusta tekstitiedostosta muodostettu Kartta-olio,
     */
    public Kartta lueKarttatiedosto(String tiedostonimi) throws Tiedostonlukupoikkeus {
        File karttatiedosto = new File(kansio + "/" + tiedostonimi);

        try (Scanner lukija = new Scanner(karttatiedosto)) {

            // Kartan tyyppi – ei tarvita
            lukija.nextLine();

            korkeus = Integer.parseInt(lukija.nextLine().split(" ")[1]);
            leveys = Integer.parseInt(lukija.nextLine().split(" ")[1]);

            // Kartta alkaa tämän rivin jälkeen
            lukija.nextLine();

            muodostaKartta(lukija);
        } catch (FileNotFoundException e) {
            throw new Tiedostonlukupoikkeus(String.format("Tiedostoa %s ei löytynyt.", karttatiedosto.getAbsolutePath()) , e);

        } catch (NumberFormatException e) {
            throw new Tiedostonlukupoikkeus("Karttadata ei kelpaa.", e);
        }

        return kartta;
    }

    private void muodostaKartta(final Scanner lukija) {
        kartta = new Kartta(korkeus, leveys);

        int rivinumero = 0;
        while (lukija.hasNextLine()) {
            String rivi = lukija.nextLine();
            taytaRivi(rivinumero, rivi);
            rivinumero++;
        }
    }

    private void taytaRivi(int rivinumero, String rivi) {
        for (int i = 0; i < leveys; i++) {
            byte merkki = (byte) (rivi.charAt(i) == 64 ? 1 : 0);
            kartta.setRuutu(rivinumero, i, merkki);
        }
    }

    /**
     * Lukee tekstitiedostona tallennetun kartan.
     *
     * @param tiedostonimi Luettavan kartan tiedostonimi.
     * @return Luetusta tekstitiedostosta muodostettu Kartta-olio,
     */
    public Skenaario lueSkenaario(String tiedostonimi) throws Tiedostonlukupoikkeus {

        File tiedosto = new File(kansio + "/testikartat/skenaariot/" + tiedostonimi);
        String kartanNimi = tiedostonimi.substring(0, tiedostonimi.length() - 5);
        Kartta skenaarioKartta = lueKarttatiedosto(kartanNimi);

        Skenaario skenaario = new Skenaario(skenaarioKartta);

        try (Scanner lukija = new Scanner(tiedosto)) {
            // Versio – ei tarvita
            lukija.nextLine();

            while (lukija.hasNextLine()) {
                Reittikuvaus reittikuvaus = lueSkenaarioRivi(lukija);
                skenaario.lisaaReittikuvaus(reittikuvaus);
            }
        } catch (FileNotFoundException e) {
            throw new Tiedostonlukupoikkeus(String.format("Tiedostoa %s ei löytynyt.", tiedosto.getAbsolutePath()) , e);
        } catch (NumberFormatException e) {
            throw new Tiedostonlukupoikkeus("Skenaariodata ei kelpaa.", e);
        }
        return skenaario;
    }

    private Reittikuvaus lueSkenaarioRivi(Scanner lukija) {
        String rivi = lukija.nextLine();

        String[] palat = rivi.split("\t");

        int alkuX = Integer.parseInt(palat[4]);
        int alkuY = Integer.parseInt(palat[5]);
        int loppuX = Integer.parseInt(palat[6]);
        int loppuY = Integer.parseInt(palat[7]);

        Ruutu alku = new Ruutu(alkuY, alkuX);
        Ruutu loppu = new Ruutu(loppuY, loppuX);

        return new Reittikuvaus(alku, loppu);
    }
}
