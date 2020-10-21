package tiralabra.polunraivaaja.suorituskykytestit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tiralabra.polunraivaaja.algoritmit.AStar;
import tiralabra.polunraivaaja.algoritmit.Haku;
import tiralabra.polunraivaaja.algoritmit.JPS;
import tiralabra.polunraivaaja.algoritmit.Leveyshaku;
import tiralabra.polunraivaaja.io.Kartanlukija;
import tiralabra.polunraivaaja.io.Tiedostonlukupoikkeus;
import tiralabra.polunraivaaja.suorituskykytestit.algoritmit.AlgoritmiSuorituskykytesti;
import tiralabra.polunraivaaja.suorituskykytestit.algoritmit.Mittaustulos;
import tiralabra.polunraivaaja.suorituskykytestit.algoritmit.Reittikuvaus;
import tiralabra.polunraivaaja.suorituskykytestit.algoritmit.Skenaario;
import tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet.RuutuJonoSuorituskykytesti;
import tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet.RuutuKekoSuorituskykytesti;
import tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet.RuutuListaSuorituskykytesti;
import tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet.Vertailutulos;
import tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet.TietorakenneSuorituskykytesti;

/**
 * Ajaa sekä tietorakenteiden että reitinhakualgoritmien suorituskykytestit.
 *
 * @author Joonas Partanen <joonas.partanen@helsinki.fi>
 */
public class SuorituskykyTestaaja {

    /**
     * Suorittaa tietorakenteiden suorituskykytestit.
     *
     * @return HashMap, jonka avaimina ovat testattujen tietorakenteiden nimet ja
     *         arvoina listoja eri iteraatiomäärillä saaduista vertailutuloksista.
     */
    public static Map<String, List<Vertailutulos>> suoritaTietorakenteidenTestit() {

        List<Integer> operaatioMaarat = Arrays.asList(1000, 10000, 100000, 1000000, 10000000);

        Map<String, List<Vertailutulos>> tulokset = new HashMap<>();

        tulokset.put("RuutuLista", new ArrayList<>());
        tulokset.put("RuutuJono", new ArrayList<>());
        tulokset.put("RuutuKeko", new ArrayList<>());

        for (int operaatioita : operaatioMaarat) {
            tulokset.get("RuutuLista").add(suoritaTietorakennetesti(new RuutuListaSuorituskykytesti(), operaatioita));
            tulokset.get("RuutuJono").add(suoritaTietorakennetesti(new RuutuJonoSuorituskykytesti(), operaatioita));
            tulokset.get("RuutuKeko").add(suoritaTietorakennetesti(new RuutuKekoSuorituskykytesti(), operaatioita));
        }

        return tulokset;
    }

    /**
     * Suorittaa tietorakenteiden suorituskykytestit.
     *
     * @return HashMap, jossa avaimina algoritmien nimet ja arvoina
     *         suorituskykytestien tulokset.
     */
    public static Map<String, Mittaustulos> suoritaAlgoritmienTestit(String skenaariotiedosto)
            throws Tiedostonlukupoikkeus {
        int suorituskerrat = 10;

        Map<String, Mittaustulos> tulokset = new HashMap<>();

        Kartanlukija lukija = new Kartanlukija("kartat");

        Skenaario skenaario = lukija.lueSkenaario(skenaariotiedosto);

        System.out.println("Aloitetaan testi 1/4");
        tulokset.put("Leveyshaku",
                suoritaAlgoritmitesti(new Leveyshaku(skenaario.getKartta()), skenaario, suorituskerrat));

        System.out.println("Aloitetaan testi 2/4");
        tulokset.put("A* (ilman diagonaalisiirtymiä)",
                suoritaAlgoritmitesti(new AStar(skenaario.getKartta(), false), skenaario, suorituskerrat));

        System.out.println("Aloitetaan testi 3/4");
        tulokset.put("A* (diagonaalisiirtymillä)",
                suoritaAlgoritmitesti(new AStar(skenaario.getKartta(), true), skenaario, suorituskerrat));

        System.out.println("Aloitetaan testi 4/4");
        tulokset.put("JPS", suoritaAlgoritmitesti(new JPS(skenaario.getKartta()), skenaario, suorituskerrat));

        return tulokset;
    }

    private static Vertailutulos suoritaTietorakennetesti(TietorakenneSuorituskykytesti testi, int operaatioita) {
        int suorituskerrat = 100;

        long[] javaRakenteenAjat = new long[suorituskerrat];
        long[] omanRakenteenAjat = new long[suorituskerrat];

        for (int i = 0; i < suorituskerrat; i++) {
            javaRakenteenAjat[i] = testi.suoritaJavaRakenteella(operaatioita);
        }

        for (int i = 0; i < suorituskerrat; i++) {
            omanRakenteenAjat[i] = testi.suoritaOmallaRakenteella(operaatioita);
        }

        Arrays.sort(javaRakenteenAjat);
        Arrays.sort(omanRakenteenAjat);

        long javaRakenteenMediaani = javaRakenteenAjat[javaRakenteenAjat.length / 2];
        long omanRakenteenMediaani = omanRakenteenAjat[omanRakenteenAjat.length / 2];

        return new Vertailutulos(omanRakenteenMediaani, javaRakenteenMediaani, operaatioita);
    }

    private static Mittaustulos suoritaAlgoritmitesti(Haku haku, Skenaario skenaario, int suorituskerrat) {
        AlgoritmiSuorituskykytesti testi = new AlgoritmiSuorituskykytesti(haku);
        long kokonaisaika = 0;

        for (Reittikuvaus reittikuvaus : skenaario.getReittikuvaukset()) {
            long[] ajat = new long[suorituskerrat];

            // Hylätään ensimmäinen suoritus.
            testi.suorita(reittikuvaus);

            for (int i = 0; i < suorituskerrat; i++) {
                ajat[i] = testi.suorita(reittikuvaus);
            }

            Arrays.sort(ajat);

            long mediaani = ajat[ajat.length / 2];
            kokonaisaika += mediaani;
        }

        return new Mittaustulos(kokonaisaika, skenaario.getReittikuvaukset().size(), suorituskerrat);
    }

}
