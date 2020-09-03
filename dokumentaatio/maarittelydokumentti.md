# Määrittelydokumentti

## Projektin kuvaus

Toteutan projektissa ohjelman, joka vertailee erilaisia reitinhakualgoritmeja. Ohjelmalle annetaan syötteenä kartta sekä reitin alku- ja loppupisteet. Ohjelma etsii pisteiden välisen lyhimmän reitin eri reitinhakualgoritmeja käyttäen ja tulostaa tietoa esimerkiksi algoritmien vaatimasta ajasta ja muistista.

## Toteutettavat algoritmit

Aion toteuttaa aluksi leveyshakua (BFS) sekä A\*- ja _Jump point search_ algoritmeja käyttäen. Myöhemmin saatan lisätä ohjelmaan vielä jonkin muun reitinhakualgoritmin tai lisätä A\*-algoritmiin mahdollisuuden vaihtaa käytettävää heuristiikkafunktiota.

Ajatuksena on aloittaa toteutus yksinkertaisemmasta ja nopeasti itselleni jo tutusta leveyshausta ja mahdollistaa työn helppo laajentaminen.

## Syötteet

Ohjelma saa syötteenään kartan ja haettavan reitin alku- ja loppupisteet. Todennäköisesti ohjelmassa on valmiina valikoima karttoja, joista käyttäjä voi valita. Jos aika riittää, olisi hyvä lisätä ohjelmaan mahdollisuus lisätä myös uusia karttoja, mutta tämä ei ole algoritmien tarkastelun kannalta niin oleellista.

## Laskennallinen vaativuus

Taulukossa |V| = solmujen määrä ja |E| = kaarien määrä.

| Algoritmi | Aikavaativuus  | Tilavaativuus |
| :-------: | :------------: | :-----------: |
|    BFS    | O(\|V\|+\|E\|) |   O(\|V\|)    |
|    A\*    |    O(\|E\|)    |   O(\|V\|)    |
|    JPS    |       ?        |       ?       |

## Lähteet

Tässä vaiheessa olen ehtinyt tutustumaan algoritmeihin vain lyhyesti Wikipediassa:

- https://en.wikipedia.org/wiki/A*_search_algorithm
- https://en.wikipedia.org/wiki/Jump_point_search

## Kurssin hallintaan liittyviä tietoja

- Opinto-ohjelma: Tietojenkäsittelytieteen kandiohjelma
- Projektin kieli: suomi
- Käytettävä ohjelmointikieli: Java
