package tiralabra.polunraivaaja.apurakenteet;

import java.util.ArrayList;
import java.util.List;

public class Hakutulos {

  private boolean onnistui;
  private String viesti;
  private List<Koordinaatti> reitti;
  private int solmujaTarkasteltu;
  private boolean[][] vierailtu;

  public Hakutulos(boolean onnistui, String viesti, int tarkasteltujaSolmuja, boolean[][] vierailtu) {
    this.onnistui = onnistui;
    this.viesti = viesti;
    this.reitti = new ArrayList<>();
    this.solmujaTarkasteltu = tarkasteltujaSolmuja;
    this.vierailtu = vierailtu;
  }

  public Hakutulos(boolean onnistui, String viesti, int tarkasteltujaSolmuja, List<Koordinaatti> reitti,
      boolean[][] vierailtu) {
    this.onnistui = onnistui;
    this.viesti = viesti;
    this.reitti = reitti;
    this.solmujaTarkasteltu = tarkasteltujaSolmuja;
    this.vierailtu = vierailtu;
  }

  public boolean isOnnistui() {
    return onnistui;
  }

  public void setOnnistui(boolean onnistui) {
    this.onnistui = onnistui;
  }

  public String getViesti() {
    return viesti;
  }

  public void setViesti(String viesti) {
    this.viesti = viesti;
  }

  public List<Koordinaatti> getReitti() {
    return reitti;
  }

  public void setReitti(List<Koordinaatti> reitti) {
    this.reitti = reitti;
  }

  public int getTarkasteltujaSolmuja() {
    return solmujaTarkasteltu;
  }

  public void setTarkasteltujaSolmuja(int tarkasteltujaSolmuja) {
    this.solmujaTarkasteltu = tarkasteltujaSolmuja;
  }

  public boolean[][] getVierailtu() {
    return vierailtu;
  }

  @Override
  public String toString() {
    return viesti + "\n" + "Solmuja tarkasteltu: " + solmujaTarkasteltu + "\n" + "Reitin pituus: " + reitti.size();
  }

}
