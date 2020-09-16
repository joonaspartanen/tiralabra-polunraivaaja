package tiralabra.polunraivaaja.ui;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tiralabra.polunraivaaja.algoritmit.AStar;
import tiralabra.polunraivaaja.algoritmit.Leveyshaku;
import tiralabra.polunraivaaja.apurakenteet.Hakutulos;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;
import tiralabra.polunraivaaja.io.Kartanlukija;
import tiralabra.polunraivaaja.kartta.GraafinenKartanpiirtaja;
import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.algoritmit.Haku;

public class GUI extends Application {

    private Canvas karttapohja;
    private VBox valikko;
    private Koordinaatti alkupiste;
    private Koordinaatti loppupiste;
    private Label alkupisteLabel;
    private Label loppupisteLabel;
    private HBox hakupalkki;
    private Haku haku;
    CheckBox salliDiagonaaliSiirtymat;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Polunraivaaja");

        Kartanlukija lukija = new Kartanlukija("kartat");
        Kartta kartta = lukija.lueKarttatiedosto("Berlin_0_512.map");
        GraafinenKartanpiirtaja piirtaja = new GraafinenKartanpiirtaja(kartta);

        ToggleGroup algoritmiToggleGroup = new ToggleGroup();
        RadioButton leveyshakuNappi = new RadioButton("Leveyshaku");
        leveyshakuNappi.setToggleGroup(algoritmiToggleGroup);
        leveyshakuNappi.setSelected(true);
        leveyshakuNappi.setOnAction(e -> salliDiagonaaliSiirtymat.setDisable(true));
        RadioButton aStarNappi = new RadioButton("A*");
        aStarNappi.setToggleGroup(algoritmiToggleGroup);
        aStarNappi.setOnAction(e -> salliDiagonaaliSiirtymat.setDisable(false));

        piirtaja.piirraKartta();

        VBox mainContainer = new VBox(20);

        karttapohja = piirtaja.getKarttapohja();
        mainContainer.getChildren().add(karttapohja);

        karttapohja.setOnMouseClicked(e -> {
            if (alkupiste == null) {
                alkupiste = new Koordinaatti((int) e.getY() / (int) piirtaja.getRuudunKorkeus(),
                        (int) e.getX() / (int) piirtaja.getRuudunLeveys());
                piirtaja.piirraPiste(alkupiste);
            } else if (loppupiste == null) {
                loppupiste = new Koordinaatti((int) e.getY() / (int) piirtaja.getRuudunKorkeus(),
                        (int) e.getX() / (int) piirtaja.getRuudunLeveys());
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

            if (algoritmiToggleGroup.getSelectedToggle() == leveyshakuNappi) {
                haku = new Leveyshaku(kartta);
            } else if (algoritmiToggleGroup.getSelectedToggle() == aStarNappi) {
                haku = new AStar(kartta);
                haku.setSalliDiagonaalisiirtymat(salliDiagonaaliSiirtymat.isSelected());
            }

            Hakutulos tulos = haku.etsiReitti(alkupiste, loppupiste);
            System.out.println(tulos);

            List<Koordinaatti> reitti = tulos.getReitti();
            boolean[][] vierailtu = tulos.getVierailtu();
            piirtaja.piirraKartta(reitti, vierailtu);
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
            alkupiste = null;
            loppupiste = null;
            paivitaHakupalkki();
        });

        salliDiagonaaliSiirtymat = new CheckBox("Salli diagonaalisiirtymät");
        salliDiagonaaliSiirtymat.setDisable(true);

        HBox algoritmivalikko = new HBox(leveyshakuNappi, aStarNappi, salliDiagonaaliSiirtymat);
        algoritmivalikko.setSpacing(20);

        hakupalkki = new HBox(alkupisteLabel, loppupisteLabel, piirraReittiNappi, pyyhiReittiNappi);
        hakupalkki.setSpacing(20);

        valikko = new VBox(algoritmivalikko, hakupalkki);
        valikko.setSpacing(20);

        mainContainer.getChildren().add(valikko);

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
