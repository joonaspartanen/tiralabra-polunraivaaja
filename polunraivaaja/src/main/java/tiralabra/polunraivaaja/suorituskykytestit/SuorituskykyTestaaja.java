package tiralabra.polunraivaaja.suorituskykytestit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet.RuutuJonoSuorituskykytesti;
import tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet.RuutuKekoSuorituskykytesti;
import tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet.RuutuListaSuorituskykytesti;
import tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet.Vertailutulos;
import tiralabra.polunraivaaja.suorituskykytestit.tietorakenteet.TietorakenneSuorituskykytesti;

public class SuorituskykyTestaaja {

    public static Map<String, List<Vertailutulos>> suoritaTietorakenteidenTestit() {

        List<Integer> iteraatioMaarat = Arrays.asList(1000, 10000, 100000);

        Map<String, List<Vertailutulos>> tulokset = new HashMap<>();

        tulokset.put("RuutuLista", new ArrayList<>());
        tulokset.put("RuutuJono", new ArrayList<>());
        tulokset.put("RuutuKeko", new ArrayList<>());

        for (int iteraatiot : iteraatioMaarat) {
            tulokset.get("RuutuLista").add(suoritaSuorituskykytesti(new RuutuListaSuorituskykytesti(), iteraatiot));
            tulokset.get("RuutuJono").add(suoritaSuorituskykytesti(new RuutuJonoSuorituskykytesti(), iteraatiot));
            tulokset.get("RuutuKeko").add(suoritaSuorituskykytesti(new RuutuKekoSuorituskykytesti(), iteraatiot));
        }

        System.out.println(tulokset.toString());

        return tulokset;
    }

    private static Vertailutulos suoritaSuorituskykytesti(TietorakenneSuorituskykytesti testi, int iteraatiot) {
        int suorituskerrat = 10;

        long[] javaRakenteenAjat = new long[suorituskerrat];
        long[] omanRakenteenAjat = new long[suorituskerrat];

        for (int i = 0; i < suorituskerrat; i++) {
            javaRakenteenAjat[i] = testi.suoritaJavaRakenteella(iteraatiot);
        }

        for (int i = 0; i < suorituskerrat; i++) {
            omanRakenteenAjat[i] = testi.suoritaOmallaRakenteella(iteraatiot);
        }

        Arrays.sort(javaRakenteenAjat);
        Arrays.sort(omanRakenteenAjat);

        long javaRakenteenMediaani = javaRakenteenAjat[javaRakenteenAjat.length / 2];
        long omanRakenteenMediaani = omanRakenteenAjat[omanRakenteenAjat.length / 2];

        return new Vertailutulos(omanRakenteenMediaani, javaRakenteenMediaani, iteraatiot);
    }

}
