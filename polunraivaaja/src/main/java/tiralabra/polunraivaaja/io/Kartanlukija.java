package tiralabra.polunraivaaja.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import tiralabra.polunraivaaja.kartta.Kartta;

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
    public Kartta lueKarttatiedosto(String tiedostonimi) {
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
            // TODO: Heitä poikkeus ja muodosta siitä virheilmoitus UI-kerroksessa.
            System.out.println("Tiedostoa ei löytynyt.");
            System.out.println(karttatiedosto.getAbsolutePath());
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
}
