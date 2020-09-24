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

![Ohjelman käyttöliittymä](https://raw.githubusercontent.com/joonaspartanen/tiralabra-polunraivaaja/master/dokumentaatio/kuvat/kayttoliittyma.png)

Ohjelman käyttöliittymä tarjoaa mahdollisuuden karttapohjan valitsemiseen pudotusvalikosta.

Kun karttapohja on valittu, käyttäjä voi valita hiirellä klikkaamalla haluamansa reitin alku- ja loppupisteet kartalta.

Kartan oikealla puolella on valikko, josta valitaan haluttu reitinhakualgoritmi. Joillain algoritmeilla on mahdollista valita erikseen, sallitaanko myös kulmittainen liikkuminen ruudusta toiseen ("Salli diagonaalisiirtymät").

Reitinhaku käynnistetään "Piirrä reitti" -painikkeesta. Haun päätyttyä käyttöliittymään tulostuu tietoa haun tuloksista ja algoritmin löytämä reitti piirtyy kartalle punaisella. Solmut, joissa algoritmi vieraili reitinhaun aikana, piirtyvät kartalle vihreällä. Tämä auttaa hahmottamaan algoritmien toimintaa ja eroja.

Reitin voi pyyhkiä "Pyyhi reitti" -painikkeesta.
