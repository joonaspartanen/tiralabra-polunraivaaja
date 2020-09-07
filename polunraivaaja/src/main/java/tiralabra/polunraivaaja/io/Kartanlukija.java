package tiralabra.polunraivaaja.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import tiralabra.polunraivaaja.kartta.Kartta;

public class Kartanlukija {

    private String kansio;
    private Kartta kartta;

    public Kartanlukija(String kansio) {
        this.kansio = kansio;
    }

    public Kartta lueKarttatiedosto(String tiedostonimi) {
        File karttatiedosto = new File(kansio + "/" + tiedostonimi);

        try (Scanner lukija = new Scanner(karttatiedosto)) {

            // Kartan tyyppi – ei tarvita
            lukija.nextLine();

            int korkeus = Integer.parseInt(lukija.nextLine().split(" ")[1]);
            int leveys = Integer.parseInt(lukija.nextLine().split(" ")[1]);

            // Kartta alkaa tämän rivin jälkeen
            lukija.nextLine();

            kartta = new Kartta(korkeus, leveys);

            int rivinumero = 0;
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                kartta.taytaRivi(rivinumero, rivi);
                rivinumero++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tiedostoa ei löytynyt.");
            System.out.println(karttatiedosto.getAbsolutePath());
        }
        return kartta;
    }
}
