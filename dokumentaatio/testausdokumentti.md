# Testaus

## Yksikkötestit

Ohjelmassa on jonkin verran yksikkötestejä, jotka testaavat hakualgoritmien toimintaa erilaisissa skenaarioissa. Edelleen olisi syytä testata enemmän ohjelman toimintaa esimerkiksi väärillä syötteillä, mutta tämä kytkeytyy virheidenkäsittelyyn, jota en ole vielä ehtinyt kunnolla aloittaa. Kuitenkin jo olemassaolevat yksikkötestit ovat erittäin hyödyllisiä, sillä niiden avulla on helppo varmistua siitä, etteivät algoritmeihin tehdyt muutokset vaikuta niiden toimintaan.

Käyttöliittymä ja kartan piirtävät luokat ovat tällä hetkellä testauksen ulkopuolella. Kartanpiirtäjille saatan kirjoittaa myöhemmin joitain testejä.

![Testikattavuus viikolla 4](https://raw.githubusercontent.com/joonaspartanen/tiralabra-polunraivaaja/master/dokumentaatio/kuvat/testikattavuus_vk4.png)

## Suorituskykytestit

Ohjelmaan on tarkoitus tehdä suorituskykytestejä, jotka mittaavat algoritmien ja itse toteuttamieni tietorakenteiden toimintaa useilla eri skenaarioilla. Tarkoituksena on mitata ainakin algoritmien vaatimaa suoritusaikaa että niiden tarkastelemien solmujen määrää.
 