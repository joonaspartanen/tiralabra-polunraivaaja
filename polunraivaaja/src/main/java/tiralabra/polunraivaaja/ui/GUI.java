package tiralabra.polunraivaaja.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tiralabra.polunraivaaja.algoritmit.Haku;
import tiralabra.polunraivaaja.algoritmit.Leveyshaku;
import tiralabra.polunraivaaja.apurakenteet.Koordinaatti;
import tiralabra.polunraivaaja.io.Kartanlukija;
import tiralabra.polunraivaaja.kartta.GraafinenKartanpiirtaja;
import tiralabra.polunraivaaja.kartta.Kartta;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Polunraivaaja");

        Kartanlukija lukija = new Kartanlukija("kartat");
        Kartta kartta = lukija.lueKarttatiedosto("Berlin_0_512.map");
        GraafinenKartanpiirtaja piirtaja = new GraafinenKartanpiirtaja(kartta);

        Haku haku = new Leveyshaku(kartta);
        haku.etsiReitti(new Koordinaatti(0, 0), new Koordinaatti(511, 511));
        piirtaja.piirraKartta(haku.getReitti());

        VBox mainContainer = new VBox();
        mainContainer.getChildren().add(piirtaja.getKarttaruudukko());
        primaryStage.setScene(new Scene(mainContainer, 1600, 1000));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
