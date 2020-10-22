# Määrittelydokumentti

## Projektin kuvaus

Toteutan projektissa ohjelman, joka vertailee erilaisia reitinhakualgoritmeja. Ohjelmalle annetaan syötteenä kartta sekä reitin alku- ja loppupisteet. Ohjelma etsii pisteiden välisen lyhimmän reitin eri reitinhakualgoritmeja käyttäen ja tulostaa tietoa esimerkiksi algoritmien vaatimasta ajasta ja muistista.

## Toteutettavat algoritmit

Aion toteuttaa ohjelmassani reitinhaun leveyshakua (BFS) sekä A\*- ja _Jump point search_ algoritmeja käyttäen. Myöhemmin saatan lisätä ohjelmaan vielä jonkin muun reitinhakualgoritmin tai lisätä A\*-algoritmiin mahdollisuuden vaihtaa käytettävää heuristiikkafunktiota.

### Leveyshaku

[Leveyshaku](https://en.wikipedia.org/wiki/Breadth-first_search) on algoritmi, joka löytää lyhimmän reitin painottamattomissa verkoissa. Algoritmi voidaan toteuttaa jonoa käyttämällä niin, että aluksi jonoon laitetaan reitin alkusolmu. Jonon solmuja aletaan sitten käydä yksi kerrallaan lävitse siten, että kaikki tarkasteltavan solmun naapurisolmut laitetaan vuorostaan jonon jatkoksi. Tätä jatketaan niin kauan kunnes loppu löytyy tai jono on tyhjä.

### A\*

[A\*](https://en.wikipedia.org/wiki/A*_search_algorithm) on Dijkstran algoritmin muunnelma, joka toimii myös painotetuissa verkoissa. Algoritmi tehostaa reitinhakua käyttämällä ns. heuristista funktiota, jonka avulla se arvioi käsiteltävien solmujen etäisyyttä reitin loppuun.

A\* voidaan toteuttaa prioriteettijonoa käyttäen niin, että jonosta valitaan käsittelyyn aina se solmu, jonka kokonaisetäisyysarvio (= tähän mennessä kuljettu etäisyys + etäisyysarvio loppuun) on pienin.

Algoritmi siis suosii niitä solmuja, jotka luultavimmin – heuristisen funktion mukaan – vievät kohti maalia, siinä missä leveyshaku etenee symmetrisesti kaikkiin mahdollisiin suuntiin.

### JPS

A\*-algoritmista on useita muunnelmia, jotka pyrkivät tehostamaan algoritmin toimintaa rajaamalla pois käsittelystä reitinhaun kannalta epäoleelliset solmut. Tällaiset muunnelmat eivät välttämättä löydä optimaalisinta reittiä, mutta ovat kuitenkin hyödyllisiä monissa käytännön sovelluksissa.

Myös Jump Point Search perustuu havaintoon siitä, että normaali A\* joutuu vierailemaan useissa sellaisissa solmuissa, jotka eivät ole reitinhaun kannalta oleellisia. JPS hyödyntää erityisesti sitä, että kahden verkon pisteen välillä on usein monia rinnakkaisia polkuja, joiden pituus on sama.

JPS pyrkiikin löytämään kartalta ns. hyppypisteitä (_jump point_), joiden tarkastelu on välttämätöntä lyhimmän reitin löytämiseksi. Tästä on hyötyä erityisesti sellaisissa tilanteissa, joissa optimaalinen reitti kulkee laajan, aukean alueen kautta. Tällöin JPS onnistuu ikäänkuin "hyppäämään" suoraan aukean ylitse tarkastelematta kaikki sen halki vieviä rinnakkaisia polkuja.

Erityisesti tällaisilla, avoimia alueita sisältävillä kartoilla JPS on A\*-algoritmia merkittävästi tehokkaampi ja, mikä kiinnostavinta, se onnistuu aina löytämään lyhimmän mahdollisen reitin.

## Toteutettavat tietorakenteet

Reitinhakualgoritmeja toteuttaessani tarvitsen ainakin jonoa (BFS) ja prioriteettijonoa (A\*).

Lisäksi tarvitsen jonkinlaista yksinkertaista listarakennetta kartan ruutuja kuvaavien Ruutu-olioiden kokoelmien tallettamiseen.

## Syötteet

Ohjelma saa syötteenään kartan ja haettavan reitin alku- ja loppupisteet. Ohjelmassa on valmiina valikoima karttoja, joista käyttäjä voi valita. Jos aika riittää, olisi hyvä lisätä ohjelmaan mahdollisuus lisätä myös uusia karttoja, mutta tämä ei ole algoritmien tarkastelun kannalta niin oleellista.

## Laskennallinen vaativuus

Taulukossa |V| = solmujen määrä ja |E| = kaarien määrä.

| Algoritmi | Aikavaativuus  | Tilavaativuus |
| :-------: | :------------: | :-----------: |
|    BFS    | O(\|V\|+\|E\|) |   O(\|V\|)    |
|    A\*    |    O(\|E\|)    |   O(\|V\|)    |
|    JPS    |    O(\|E\|)    |   O(\|V\|)    |

## Lähteet

- https://en.wikipedia.org/wiki/A*_search_algorithm

- https://en.wikipedia.org/wiki/Breadth-first_search

- https://en.wikipedia.org/wiki/Jump_point_search

- http://theory.stanford.edu/~amitp/GameProgramming/AStarComparison.html

- Harabor, Daniel & Grastien, Alban (2011). [Online Graph Pruning for Pathfinding on Grid Maps](http://users.cecs.anu.edu.au/~dharabor/data/papers/harabor-grastien-aaai11.pdf). NICTA and The Australian National University.

## Kurssin hallintaan liittyviä tietoja

- Opinto-ohjelma: Tietojenkäsittelytieteen kandiohjelma
- Projektin kieli: suomi
- Käytettävä ohjelmointikieli: Java
