package tiralabra.polunraivaaja.apurakenteet;

public class RuutuKomparaattori {

    private double[][] vertailuperuste;

    public RuutuKomparaattori(double[][] vertailuperuste) {
        this.vertailuperuste = vertailuperuste;
    }

    public int vertaaRuutuja(Ruutu a, Ruutu b) {
        System.out.println(a + ", " + b);

        if (a == null || b == null) {
            return 0;
        }
        return vertailuperuste[a.y][a.x] - vertailuperuste[b.y][b.x] < 0 ? -1 : 1;
    }

}
