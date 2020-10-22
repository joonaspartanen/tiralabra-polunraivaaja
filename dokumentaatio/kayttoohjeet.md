# Käyttöohjeet

## Komentorivitoiminnot

Muista ensin siirtyä repositorion juuresta sovelluksen kansioon komennolla `cd polunraivaaja`.

### Ohjelman suorittaminen

Voit suorittaa ohjelman komentoriviltä komennolla:

`mvn compile exec:java -Dexec.mainClass=tiralabra.polunraivaaja.Main`

### Testien suorittaminen

Voit suorittaa testit komennolla

`mvn test`

ja generoida testikattavuusraportin komennolla

`mvn test jacoco:report`

Raportti löytyy polusta `/target/site/jacoco/index.html`.

### Checkstyle

Voit luoda Checkstyle-raportin komennolla

`mvn jxr:jxr checkstyle:checkstyle`

Raportti löytyy polusta `/target/site/checkstyle.html`.

## Ohjelman käyttäminen

Ohjelmassa on kaksi näkymää, joiden välillä voi siirtyä yläpalkin välilehdistä.

### Reitinhaku-näkymä

![Ohjelman reitinhakunäkymä](https://raw.githubusercontent.com/joonaspartanen/tiralabra-polunraivaaja/master/dokumentaatio/kuvat/reitinhakunakyma.png)

Reitinhakunäkymässä käyttäjä voi ensin valita pudotusvalikosta kartan, joka piirretään näytölle.

Tämän jälkeen käyttäjä voi valita hiirellä klikkaamalla haluamansa reitin alku- ja loppupisteet kartalta. Jo valitut pisteet voi poistaa klikkaamalla karttaa uudelleen.

Kartan oikealla puolella on valikko, josta valitaan haluttu reitinhakualgoritmi. A\*-algoritmin kohdalla on mahdollista valita erikseen, sallitaanko myös kulmittainen liikkuminen ruudusta toiseen ("Salli diagonaalisiirtymät").

Reitinhaku käynnistetään "Piirrä reitti" -painikkeesta. Haun päätyttyä käyttöliittymään tulostuu tietoa haun tuloksista ja algoritmin löytämä reitti piirtyy kartalle punaisella. Solmut, joissa algoritmi vieraili reitinhaun aikana, piirtyvät kartalle vihreällä. Tämä auttaa hahmottamaan algoritmien toimintaa ja eroja.

![Reitti reitinhakunäkymässä](https://raw.githubusercontent.com/joonaspartanen/tiralabra-polunraivaaja/master/dokumentaatio/kuvat/reitinhakunakyma_reitti.png)

Reitin voi pyyhkiä "Pyyhi reitti" -painikkeesta.

### Suorituskykytestit-näkymä

![Ohjelman testinäkymä](https://raw.githubusercontent.com/joonaspartanen/tiralabra-polunraivaaja/master/dokumentaatio/kuvat/testinakyma.png)

Suorituskykytestit-näkymässä käyttäjä voi ajaa suorituskykytestit sovelluksen reitinhakualgoritmeille ja tietorakenteille.

Algoritmitestejä varten käyttäjä voi valita pudotusvalikosta testiskenaarion, joka sisältää suuren joukon reittikuvauksia johonkin tiettyyn karttaan.

Käyttäjä voi käynnistää algoritmien ja tietorakenteiden testit käyttöliittymän painikkeista.

Testin päätyttyä saadut mittaustulokset tulostetaan käyttöliittymään.

Huom: Etenkin algoritmien suorituskykytestien suorittaminen saattaa kestää kauan. Testit blokkaavat suorittavan säikeen, joten käyttöliittymä ei ole käytettävissä testien suorituksen aikana.
