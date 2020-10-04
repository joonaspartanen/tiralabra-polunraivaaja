package tiralabra.polunraivaaja.apurakenteet;

public class RuutuJono {

    private RuutuLista lista;

    public RuutuJono() {
        this.lista = new RuutuLista();
    }

    public void lisaaJonoon(Ruutu ruutu) {
        lista.lisaaRuutu(ruutu);
    }

    public Ruutu otaJonosta() {
        return lista.haePituus() > 0 ? lista.poistaRuutuIndeksissa(0) : null;
    }

    public int haePituus() {
        return lista.haePituus();
    }

    public boolean onTyhja() {
        return haePituus() == 0;
    }
}
