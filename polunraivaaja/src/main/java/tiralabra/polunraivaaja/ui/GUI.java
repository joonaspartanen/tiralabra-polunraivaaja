package tiralabra.polunraivaaja.ui;

import java.io.File;
import java.io.FilenameFilter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tiralabra.polunraivaaja.algoritmit.AStar;
import tiralabra.polunraivaaja.algoritmit.Leveyshaku;
import tiralabra.polunraivaaja.mallit.Hakutulos;
import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.tietorakenteet.RuutuLista;
import tiralabra.polunraivaaja.io.Kartanlukija;
import tiralabra.polunraivaaja.kartta.Kartanpiirtaja;
import tiralabra.polunraivaaja.kartta.Kartta;
import tiralabra.polunraivaaja.algoritmit.Haku;
import tiralabra.polunraivaaja.algoritmit.JPS;

/**
 * Sovelluksen graafinen käyttöliittymä. Luokan koodin laatu on heikkoa ja se
 * tulisi refaktoroida ja pilkkoa pienempiin osiin.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class GUI extends Application {

    private Canvas karttapohja;
    Kartta kartta;
    Kartanlukija lukija;
    Kartanpiirtaja piirtaja;
    private VBox valikko;
    private Ruutu alkupiste;
    private Ruutu loppupiste;
    private Label alkupisteLabel;
    private Label loppupisteLabel;
    private HBox hakupalkki;
    private Haku haku;
    private CheckBox salliDiagonaaliSiirtymat;
    private Label hakutulosnakyma;
    private ComboBox<String> karttalista;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Polunraivaaja");

        Alert ilmoitus = new Alert(AlertType.INFORMATION);

        lukija = new Kartanlukija("kartat");

        alustaKarttalista();

        ToggleGroup algoritmiToggleGroup = new ToggleGroup();
        RadioButton leveyshakuNappi = new RadioButton("Leveyshaku");
        leveyshakuNappi.setToggleGroup(algoritmiToggleGroup);
        leveyshakuNappi.setSelected(true);
        leveyshakuNappi.setOnAction(e -> {
            salliDiagonaaliSiirtymat.setSelected(false);
            salliDiagonaaliSiirtymat.setDisable(true);
        });

        RadioButton aStarNappi = new RadioButton("A*");
        aStarNappi.setToggleGroup(algoritmiToggleGroup);
        aStarNappi.setOnAction(e -> salliDiagonaaliSiirtymat.setDisable(false));

        RadioButton jpsNappi = new RadioButton("JPS");
        jpsNappi.setToggleGroup(algoritmiToggleGroup);
        jpsNappi.setOnAction(e -> {
            salliDiagonaaliSiirtymat.setSelected(true);
            salliDiagonaaliSiirtymat.setDisable(true);
        });

        hakutulosnakyma = new Label();
        hakutulosnakyma.setFont(new Font("Segoe UI", 20));

        piirtaja.piirraKartta();

        HBox mainContainer = new HBox(20);
        mainContainer.setPadding(new Insets(50, 50, 50, 50));

        karttapohja = piirtaja.getKarttapohja();
        mainContainer.getChildren().add(karttapohja);

        karttapohja.setOnMouseClicked(e -> {
            if (alkupiste == null) {
                alkupiste = new Ruutu((int) e.getY() / (int) piirtaja.getRuudunKorkeus(),
                        (int) e.getX() / (int) piirtaja.getRuudunLeveys());
                piirtaja.piirraPiste(alkupiste);
            } else if (loppupiste == null) {
                loppupiste = new Ruutu((int) e.getY() / (int) piirtaja.getRuudunKorkeus(),
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
                ilmoitus.setContentText("Valitse reitin päät hiirellä kartalta.");
                ilmoitus.setTitle("Virhe");
                ilmoitus.setHeaderText("Reitin alku- tai loppupiste puuttuu");
                ilmoitus.show();
                return;
            }

            if (algoritmiToggleGroup.getSelectedToggle() == leveyshakuNappi) {
                haku = new Leveyshaku(kartta);
            } else if (algoritmiToggleGroup.getSelectedToggle() == aStarNappi) {
                haku = new AStar(kartta);
                haku.setSalliDiagonaalisiirtymat(salliDiagonaaliSiirtymat.isSelected());
            } else if (algoritmiToggleGroup.getSelectedToggle() == jpsNappi) {
                haku = new JPS(kartta);
            }

            Hakutulos tulos = haku.etsiReitti(alkupiste, loppupiste);
            hakutulosnakyma.setText(tulos.toString());

            if (tulos.isOnnistui()) {
                RuutuLista reitti = tulos.getReitti();
                boolean[][] vierailtu = tulos.getVierailtu();
                piirtaja.piirraKartta(reitti, vierailtu);
                karttapohja = piirtaja.getKarttapohja();
            }
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
            hakutulosnakyma.setText("");
        });

        salliDiagonaaliSiirtymat = new CheckBox("Salli diagonaalisiirtymät");
        salliDiagonaaliSiirtymat.setDisable(true);

        HBox algoritmivalikko = new HBox(leveyshakuNappi, aStarNappi, jpsNappi, salliDiagonaaliSiirtymat);
        algoritmivalikko.setSpacing(20);

        hakupalkki = new HBox(alkupisteLabel, loppupisteLabel, piirraReittiNappi, pyyhiReittiNappi);
        hakupalkki.setSpacing(20);

        valikko = new VBox(karttalista, algoritmivalikko, hakupalkki, hakutulosnakyma);
        valikko.setSpacing(20);
        valikko.setPadding(new Insets(0, 0, 0, 50));

        mainContainer.getChildren().add(valikko);

        primaryStage.setScene(new Scene(mainContainer, 1600, 1200));
        primaryStage.show();
    }

    private void alustaKarttalista() {
        File karttakansio = new File("kartat");

        FilenameFilter suodatin = (tiedosto, nimi) -> nimi.endsWith(".map");

        String[] karttatiedostot = karttakansio.list(suodatin);
        karttalista = new ComboBox<>();

        karttalista.getItems().addAll(karttatiedostot);
        karttalista.getSelectionModel().selectFirst();
        kartta = lukija.lueKarttatiedosto(karttalista.getSelectionModel().getSelectedItem());
        piirtaja = new Kartanpiirtaja(kartta);

        karttalista.setOnAction(e -> {
            kartta = lukija.lueKarttatiedosto(karttalista.getSelectionModel().getSelectedItem());
            piirtaja.setKartta(kartta);
            piirtaja.piirraKartta();
            karttapohja = piirtaja.getKarttapohja();
        });
    }

    private void paivitaHakupalkki() {
        alkupisteLabel.setText(alkupiste == null ? "Alku: " : "Alku: " + alkupiste.toString());
        loppupisteLabel.setText(loppupiste == null ? "Loppu: " : "Loppu: " + loppupiste.toString());
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
