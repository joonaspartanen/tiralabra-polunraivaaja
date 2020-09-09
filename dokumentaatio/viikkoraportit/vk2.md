# Viikkoraportti 2

Toisella viikolla sain monta ohjelman osa-aluetta hyvään alkuun: toteutin luokan, joka lukee tekstitiedostona tallennetun kartan taulukoksi, ensimmäisen version leveyshausta sekä kartan konsoliin tulostavan luokan, jonka tosin ehdin jo korvata aivan alustavalla graafisella käyttöliittymällä. Sain myös testauksen hyvään alkuun, tosin testejä täytyy laajentaa vielä merkittävästi. Aloitin JavaDocin kirjoittamista, mutta taidan jatkaa sitä vasta myöhemmin, kunhan metodit ja luokat alkavat asettua lopulliseen muotoonsa.

Ensi viikolla aion keskittyä sovelluksen ydinsisältöön eli muiden reitinhakualgoritmien toteuttamiseen. Aloitan A*-algoritmista, jonka toimintaan ehdin jo perehtyä tällä viikolla varsin hyvin.

Tällä hetkellä muutan kartat ruudukoksi, mikä toimii leveyshaun kanssa hyvin. Olen kuitenkin pohtinut, pitäisikö minun pyrkiä muuttamaan kartat painotetuiksi verkoiksi niin että ainoastaan jollain tavalla valitut avainpisteet olisivat solmuja. Pienemmällä määrällä solmuja reitinhaku olisi varmasti nopeampaa.

Tällä viikolla ohjelmassa on vielä joitain Javan valmiita tietorakenteita (jono, lista), jotka ajattelin korjata pian omilla toteutuksilla. Ohjelmassa on myös jonkin verran hätäisesti tiettyyn tarpeeseen kirjoitettuja metodeita, jotka kaipaisivat refaktorointia. Myös ohjelman arkkitehtuuria pitäisi vielä pysähtyä miettimään ja lisätä siihen virheittenkäsittelyä.

Viikolla käytetty tuntimäärä: 12 h
