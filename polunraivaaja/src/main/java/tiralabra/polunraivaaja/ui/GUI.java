package tiralabra.polunraivaaja.ui;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Map;

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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import tiralabra.polunraivaaja.algoritmit.AStar;
import tiralabra.polunraivaaja.algoritmit.Leveyshaku;
import tiralabra.polunraivaaja.mallit.Hakutulos;
import tiralabra.polunraivaaja.mallit.Ruutu;
import tiralabra.polunraivaaja.suorituskykytestit.SuorituskykyTestaaja;
import tiralabra.polunraivaaja.suorituskykytestit.algoritmit.Mittaustulos;
import tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet.Vertailutulos;
import tiralabra.polunraivaaja.tietorakenteet.RuutuLista;
import tiralabra.polunraivaaja.io.Kartanlukija;
import tiralabra.polunraivaaja.io.Tiedostonlukupoikkeus;
import tiralabra.polunraivaaja.mallit.Kartta;
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
    private Kartta kartta;
    private Kartanlukija lukija;
    private Kartanpiirtaja piirtaja;
    private VBox valikko;
    private ToggleGroup algoritmiToggleGroup;
    private RadioButton leveyshakuNappi;
    private RadioButton aStarNappi;
    private RadioButton jpsNappi;
    private Ruutu alkupiste;
    private Ruutu loppupiste;
    private Label alkupisteLabel;
    private Label loppupisteLabel;
    private HBox hakupalkki;
    private Haku haku;
    private CheckBox salliDiagonaaliSiirtymatCheckBox;
    private Label hakutulosnakyma;
    private ComboBox<String> karttalista;
    private Alert ilmoitus;
    private final String BORDER_BLACK = "-fx-border-color: black";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Polunraivaaja");

        ilmoitus = new Alert(AlertType.INFORMATION);
        ilmoitus.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        TabPane valilehdet = new TabPane();
        valilehdet.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        Tab hakuValilehti = alustaHakuValilehti();
        Tab testiValilehti = alustaTestiValilehti();

        valilehdet.getTabs().addAll(hakuValilehti, testiValilehti);

        primaryStage.setScene(new Scene(valilehdet, 1600, 1200));
        primaryStage.show();
    }

    private Tab alustaHakuValilehti() {
        Tab hakuValilehti = new Tab("Reittihaku");

        lukija = new Kartanlukija("kartat");

        alustaKarttalista();

        alustaAlgoritmiValikko();

        hakutulosnakyma = new Label();
        hakutulosnakyma.setFont(new Font("Segoe UI", 20));

        piirtaja.piirraKartta();
        karttapohja = piirtaja.getKarttapohja();

        HBox hakuWrapper = new HBox(20);
        hakuWrapper.setPadding(new Insets(50, 50, 50, 50));
        hakuWrapper.getChildren().add(alustaKarttaWrapper());

        hakuValilehti.setContent(hakuWrapper);

        alustaReitinPaaLabelit();

        karttapohja.setOnMouseClicked(e -> valitseReitinPaat(e));

        Button piirraReittiNappi = alustaPiirraReittiNappi();

        Button pyyhiReittiNappi = alustaPyyhiReittiNappi();

        salliDiagonaaliSiirtymatCheckBox = new CheckBox("Salli diagonaalisiirtymät");
        salliDiagonaaliSiirtymatCheckBox.setDisable(true);

        HBox algoritmivalikko = new HBox(leveyshakuNappi, aStarNappi, jpsNappi, salliDiagonaaliSiirtymatCheckBox);
        algoritmivalikko.setSpacing(20);

        hakupalkki = new HBox(alkupisteLabel, loppupisteLabel, piirraReittiNappi, pyyhiReittiNappi);
        hakupalkki.setSpacing(20);

        valikko = new VBox(karttalista, algoritmivalikko, hakupalkki, hakutulosnakyma);
        valikko.setSpacing(20);
        valikko.setPadding(new Insets(0, 0, 0, 50));

        hakuWrapper.getChildren().add(valikko);

        return hakuValilehti;
    }

    private HBox alustaKarttaWrapper() {
        HBox karttaWrapper = new HBox();
        karttaWrapper.setMaxHeight(512);
        karttaWrapper.setMaxWidth(512);
        karttaWrapper.setStyle(BORDER_BLACK);
        karttaWrapper.getChildren().add(karttapohja);

        return karttaWrapper;
    }

    private Button alustaPiirraReittiNappi() {
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
                haku.setSalliDiagonaalisiirtymat(salliDiagonaaliSiirtymatCheckBox.isSelected());
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

        return piirraReittiNappi;
    }

    private Button alustaPyyhiReittiNappi() {
        Button pyyhiReittiNappi = new Button("Pyyhi reitti");

        pyyhiReittiNappi.setOnAction(e -> {
            piirtaja.piirraKartta();
            karttapohja = piirtaja.getKarttapohja();
            alkupiste = null;
            loppupiste = null;
            paivitaHakupalkki();
            hakutulosnakyma.setText("");
        });

        return pyyhiReittiNappi;
    }

    private void alustaReitinPaaLabelit() {
        alkupisteLabel = new Label("Alku: ");
        alkupisteLabel.setMinWidth(120);
        loppupisteLabel = new Label("Loppu: ");
        loppupisteLabel.setMinWidth(120);
    }

    private void valitseReitinPaat(MouseEvent e) {
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
    }

    private void alustaKarttalista() {
        File karttakansio = new File("kartat");

        FilenameFilter suodatin = (tiedosto, nimi) -> nimi.endsWith(".map");

        String[] karttatiedostot = karttakansio.list(suodatin);
        karttalista = new ComboBox<>();

        karttalista.getItems().addAll(karttatiedostot);
        karttalista.getSelectionModel().selectFirst();

        lueKartta(karttalista.getSelectionModel().getSelectedItem());

        piirtaja = new Kartanpiirtaja(kartta);

        karttalista.setOnAction(e -> {
            lueKartta(karttalista.getSelectionModel().getSelectedItem());
            piirtaja.setKartta(kartta);
            piirtaja.piirraKartta();
            karttapohja = piirtaja.getKarttapohja();
        });
    }

    private void lueKartta(String tiedostonimi) {
        try {
            kartta = lukija.lueKarttatiedosto(tiedostonimi);
        } catch (Tiedostonlukupoikkeus ex) {
            ilmoitus.setTitle("Virhe");
            ilmoitus.setHeaderText("Tiedoston lukeminen epäonnistui.");
            ilmoitus.setContentText(ex.getMessage());
            ilmoitus.show();
        }
    }

    private void alustaAlgoritmiValikko() {
        algoritmiToggleGroup = new ToggleGroup();
        leveyshakuNappi = new RadioButton("Leveyshaku");
        leveyshakuNappi.setToggleGroup(algoritmiToggleGroup);
        leveyshakuNappi.setSelected(true);
        leveyshakuNappi.setOnAction(e -> {
            salliDiagonaaliSiirtymatCheckBox.setSelected(false);
            salliDiagonaaliSiirtymatCheckBox.setDisable(true);
        });

        aStarNappi = new RadioButton("A*");
        aStarNappi.setToggleGroup(algoritmiToggleGroup);
        aStarNappi.setOnAction(e -> salliDiagonaaliSiirtymatCheckBox.setDisable(false));

        jpsNappi = new RadioButton("JPS");
        jpsNappi.setToggleGroup(algoritmiToggleGroup);
        jpsNappi.setOnAction(e -> {
            salliDiagonaaliSiirtymatCheckBox.setSelected(true);
            salliDiagonaaliSiirtymatCheckBox.setDisable(true);
        });
    }

    private void paivitaHakupalkki() {
        alkupisteLabel.setText(alkupiste == null ? "Alku: " : "Alku: " + alkupiste.toString());
        loppupisteLabel.setText(loppupiste == null ? "Loppu: " : "Loppu: " + loppupiste.toString());
    }

    private Tab alustaTestiValilehti() {
        Tab testiValilehti = new Tab("Suorituskykytestit");

        VBox testiWrapper = new VBox(20);
        testiWrapper.setPadding(new Insets(50, 50, 50, 50));

        HBox valikkoWrapper = new HBox(20);
        VBox tulosalue = new VBox(20);

        ComboBox<String> skenaariolista = alustaSkenaariolista();

        Button suoritaAlgoritmiTestitNappi = new Button("Suorita algoritmien suorituskykytestit");
        suoritaAlgoritmiTestitNappi.setOnAction(e -> {
            tulosalue.getChildren().clear();
            tulosalue.getChildren().add(new Label("Suoritetaan testejä..."));

            try {
                Map<String, Mittaustulos> tulokset = SuorituskykyTestaaja
                        .suoritaAlgoritmienTestit(skenaariolista.getSelectionModel().getSelectedItem());

                tulosalue.getChildren().clear();

                for (Map.Entry<String, Mittaustulos> tulos : tulokset.entrySet()) {
                    TextFlow tulosteksti = new TextFlow();
                    tulosteksti.setStyle(BORDER_BLACK);
                    tulosteksti.setPadding(new Insets(15, 15, 15, 15));
                    Text otsikko = new Text(tulos.getKey() + "\n");
                    otsikko.setStyle("-fx-font-weight: bold");
                    Text tuloskuvaus = new Text(tulos.getValue().toString());
                    tulosteksti.getChildren().addAll(otsikko, tuloskuvaus);
                    tulosalue.getChildren().add(tulosteksti);
                }

            } catch (Tiedostonlukupoikkeus ex) {
                tulosalue.getChildren().clear();
                ilmoitus.setTitle("Virhe!");
                ilmoitus.setHeaderText("Tiedoston lukeminen epäonnistui.");
                ilmoitus.setContentText(ex.getMessage());
                ilmoitus.show();
            }

        });

        Button suoritaTietorakenneTestitNappi = new Button("Suorita tietorakenteiden suorituskykytestit");
        suoritaTietorakenneTestitNappi.setOnAction(e -> {
            tulosalue.getChildren().clear();
            tulosalue.getChildren().add(new Label("Suoritetaan testejä..."));

            Map<String, List<Vertailutulos>> tulokset = SuorituskykyTestaaja.suoritaTietorakenteidenTestit();
            tulosalue.getChildren().clear();

            for (Map.Entry<String, List<Vertailutulos>> tulos : tulokset.entrySet()) {
                TextFlow tulosteksti = new TextFlow();
                tulosteksti.setStyle(BORDER_BLACK);
                tulosteksti.setPadding(new Insets(15, 15, 0 , 15));
                Text otsikko = new Text(tulos.getKey() + "\n");
                otsikko.setStyle("-fx-font-weight: bold");

                StringBuilder builderi = new StringBuilder();
                for (Vertailutulos vertailutulos : tulos.getValue()) {
                    builderi.append(vertailutulos.toString());
                }
                builderi.delete(builderi.length() - 2, builderi.length() - 1);

                Text tuloskuvaus = new Text(builderi.toString());
                tulosteksti.getChildren().addAll(otsikko, tuloskuvaus);
                tulosalue.getChildren().add(tulosteksti);
            }
        });

        valikkoWrapper.getChildren().addAll(new Label("Valitse testiskenaario:"), skenaariolista,
                suoritaAlgoritmiTestitNappi, suoritaTietorakenneTestitNappi);
        testiWrapper.getChildren().addAll(valikkoWrapper, tulosalue);
        testiValilehti.setContent(testiWrapper);

        return testiValilehti;
    }

    private ComboBox<String> alustaSkenaariolista() {
        File skenaariokansio = new File("kartat/testikartat/skenaariot");

        FilenameFilter suodatin = (tiedosto, nimi) -> nimi.endsWith(".map.scen");

        String[] skenaariotiedostot = skenaariokansio.list(suodatin);
        ComboBox<String> skenaariolista = new ComboBox<>();

        skenaariolista.getItems().addAll(skenaariotiedostot);
        skenaariolista.getSelectionModel().selectFirst();

        return skenaariolista;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
