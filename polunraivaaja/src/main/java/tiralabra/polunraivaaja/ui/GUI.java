package tiralabra.polunraivaaja.ui;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tiralabra.polunraivaaja.algoritmit.Haku;
import tiralabra.polunraivaaja.algoritmit.Leveyshaku;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;
import tiralabra.polunraivaaja.io.Kartanlukija;
import tiralabra.polunraivaaja.kartta.GraafinenKartanpiirtaja;
import tiralabra.polunraivaaja.kartta.Kartta;

public class GUI extends Application {

    private Canvas karttapohja;
    private Koordinaatti alkupiste;
    private Koordinaatti loppupiste;
    Label alkupisteLabel;
    Label loppupisteLabel;
    HBox hakupalkki;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Polunraivaaja");

        Kartanlukija lukija = new Kartanlukija("kartat");
        Kartta kartta = lukija.lueKarttatiedosto("Berlin_0_512.map");
        GraafinenKartanpiirtaja piirtaja = new GraafinenKartanpiirtaja(kartta);

        Haku haku = new Leveyshaku(kartta);

        piirtaja.piirraKartta();

        VBox mainContainer = new VBox(20);

        karttapohja = piirtaja.getKarttapohja();
        mainContainer.getChildren().add(karttapohja);

        karttapohja.setOnMouseClicked(e -> {
            if (alkupiste == null) {
                alkupiste = new Koordinaatti((int) e.getY(), (int) e.getX());
                piirtaja.piirraPiste(alkupiste);
            } else if (loppupiste == null) {
                loppupiste = new Koordinaatti((int) e.getY(), (int) e.getX());
                piirtaja.piirraPiste(loppupiste);
            } else {
                alkupiste = null;
                loppupiste = null;
                piirtaja.piirraKartta();
            }
            karttapohja = piirtaja.getKarttapohja();
            paivitaHakupalkki();
        });

        Button piirraReittiNappi = new Button("Piirrä reitti");
        piirraReittiNappi.setOnAction(e -> {
            if (alkupiste == null || loppupiste == null) {
                return;
            }
            haku.etsiReitti(alkupiste, loppupiste);
            List<Koordinaatti> reitti = haku.getReitti();
            piirtaja.piirraKartta(reitti);
            karttapohja = piirtaja.getKarttapohja();
        });

        alkupisteLabel = new Label("Alku: ");
        alkupisteLabel.setMinWidth(120);
        loppupisteLabel = new Label("Loppu: ");
        loppupisteLabel.setMinWidth(120);

        Button pyyhiReittiNappi = new Button("Pyyhi reitti");

        pyyhiReittiNappi.setOnAction(e -> {
            piirtaja.piirraKartta();
            karttapohja = piirtaja.getKarttapohja();
        });


        hakupalkki = new HBox(alkupisteLabel, loppupisteLabel, piirraReittiNappi, pyyhiReittiNappi);
        hakupalkki.setSpacing(20);

        mainContainer.getChildren().add(hakupalkki);

        primaryStage.setScene(new Scene(mainContainer, 1600, 1200));
        primaryStage.show();
    }

    private void paivitaHakupalkki() {
        alkupisteLabel.setText(alkupiste == null ? "Alku: " : "Alku: " + alkupiste.toString());
        loppupisteLabel.setText(loppupiste == null ? "Loppu: " : "Loppu: " + loppupiste.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
