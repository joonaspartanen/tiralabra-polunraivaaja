# Toteutusdokumentti

## Ohjelman yleisrakenne

Sovellus koostuu seuraavista pakkauksista:

- algoritmit: Sisältää varsinaiset reitinhakualgoritmit ja niihin liittyvän rajapinnan sekä algoritmien käyttämät heuristiset funktiot.
- io: Sisältää ennen kaikkea karttatiedostojen lukemisesta vastaavan Kartanlukija-luokan.
- mallit: Sisältää sovelluksen käyttämät tietomallit (Kartta, Hakutulos, Ruutu, Suunta).
- suorituskykytestit: Sisältää algoritmien ja tietorakenteiden suorituskykytesteihin liittyvät luokat.
- tietorakenteet: Sisältää itse toteuttamani tietorakenteet (lista, jono, keko).
- tyokalut: Sisältää apuluokkia, joita muut luokat hyödyntävät (Laskin, Taulukonkasittelija, RuutuKomparaattori).
- ui: Sisältää käyttöliittymään liittyvät luokat (GUI ja Kartanpiirtaja).

Lyhyesti kuvattuna sovellus hyödyntää Kartanlukija-luokkaa tekstitiedostoina tallennetun karttadatan muuntamiseksi Kartta-olioiksi. Karttaruudut ja kartalla liikkumista kuvaavat suunnat on mallinnettu Ruutu- ja Suunta-luokkina.

Haku-rajapinnan toteuttavat reitinhakualgoritmit käyttävät Kartta-olioita optimaalisen reitin etsimiseksi kahden kartalla olevan pisteen välillä. Algoritmit muodostavat reitinhaun tulosta kuvaavan Hakutulos-olion, jota Kartanpiirtaja-luokka käyttää reitin piirtämiseksi kartalle graafisessa käyttöliittymässä.

## Saavutetut aika- ja tilavaativuudet

Nostan seuraavaksi esiin eräitä keskeisiä huomioita toteuttamieni tietorakenteiden ja reitinhakualgoritmien aika- ja tilavaativuuksiin liittyen.

### Tietorakenteet

#### RuutuLista

Javan ArrayListin korvaava RuutuLista käyttää pohjanaan taulukkoa ja tärkeimmät operaatiot toteutuvat selvästi odotussa ajassa (alkion lisääminen _O(1)_ ja hakeminen indeksistä _O(1)_).

Myös RuutuListan tilavaativuus on odotetusti _O(n)_ – pohjana olevan taulukon koko kasvaa tarvittaessa taulukon täyttyessä.

Toteutin RuutuListalle myös ajassa _O(n)_ toimivan poisto-operaation, mutta sitä ei itse asiassa tällä hetkellä käytetä ollenkaan ohjelmassa. Muistinkäytön kannalta on myös syytä huomauttaa, ettei pohjana olevan taulukon koko kutistu, vaikka listalta poistettaisiin paljonkin alkoita.

### RuutuJono

Toteuttamani jono-rakenne mahdollistaa alkioiden lisäämisen jonon loppuun sekä jonon etummaisen (= vanhimman) alkion poistamisen tehokkaasti ajassa _O(1)_, sillä jono käyttää pohjana RuutuLista-rakennetta ja poistaminen on toteutettu yksinkertaisesti päivittämällä etummaisen alkion sijaintia merkitsevää indeksiä.

Yksinkertaisesta poistomekanismista johtuu, ettei RuutuJono ole muistinkäytöltää erityisen tehokas. Jono kyllä kasvaa RuutuListan tavoin tarvittaessa, mutta muistia ei todellisuudessa vapauteta vaikka jonosta poistetaan alkioita.

### RuutuKeko

A\*- ja JPS-algoritmien käyttämä keko/prioriteettijono on toteutettu binäärikekona, joten keon päällimmäinen alkio voidaan hakea tehokkaasti ajassa _O(1)_.

Alkioiden lisääminen ja poistaminen tapahtuu puolestaan ajassa _O(log n)_, sillä kekoa täytyy järjestää, kunnes sen kekoehto on jälleen voimassa.

Kuten muutkin tietorakenteet, myös RuutuKeko perustuu taulukkoon, jonka koko kasvaa muttei kutistu tarvittaessa.

### Algoritmit

#### Leveyshaku

Leveyshaun odotettu aikavaativuus on tunnetusti _O(\|V\|+\|E\|)_, missä |V| on verkon solmujen ja |E| kaarien määrä.

Kuten testausdokumentissa tarkemmin esitellään, osoittautui leveyshaku käytännössä varsin tehokkaaksi algoritmiksi A\*:iin verrattuna. Leveyshaun suhteellista tehokkuutta selittänee ainakin toteutuksen yksinkertaisuus ja käyttämieni karttojen melko pieni koko: koska karttojen koko on korkeintaan 512x512 ruutua, ei leveyshaku ehdi "harhailemaan" kovin kauaa vääriin suuntiin ennen maalin löytymistä.

Samalla on muistettava, että leveyshaku on hyvin rajoittunut algoritmi, sillä se toimii vain painottamattomissa verkoissa. Omassa sovelluksessani tämä näkyy siinä, ettei leveyshaku mahdollista lainkaan diagonaalisia siirtymiä – näin ollen se ei useimmiten kykene löytämään yhtä lyhyttä reittiä kuin sovelluksen muut algoritmit.

#### A\*

A\*-algoritmin odotettu aikavaativuus on _O(\|E\|)_. A\* osoittautui kuitenkin käytännössä sovelluksen hitaimmaksi algoritmiksi, vaikka tehostettua algoritmin toimintaa viimeisellä viikolla poistamalla esimerkiksi turhia tarkistuksia.

Etsin suorituskykytestien avulla A\*-implementaatiostani mahdollisia pullonkauloja, mutta ainakaan oman kekorakenteeni vaihtaminen takaisin Javan PriorityQueueksi tai liukulukuetäisyyksien vaihtaminen likimääräisiksi kokonaisluvuiksi ei vaikuttanut tuloksiin merkittävästi.

Uskoisin, että algoritmin suhteellista hitautta selittää etenkin se, että kaikki käyttämäni kartat muodostavat melko pieniä ja erittäin tiheitä painottamattomia verkkoja, jotka eivät millään muotoa suosi A\*:a. Siksi en ole ollenkaan varma, onko algoritmia ylipäänsä mielekästä verrata yksinkertaiseen mutta rajalliseen leveyshakuun.

Toisen mielenkiintoisen näkökulman algoritmien vertailuun tarjoaa kuitenkin sovellukseni käyttöliittymän reittihakutoiminto, piirtää kartalle myös solmut, joissa algoritmi on reitinhaun aikana "vieraillut". Näin on helppo havaita leveyshaun ja A\*:n perustava ero: siinä missä leveyshaku laajenee tasaisesti kaikkiin suuntiin, osaa A\* laajentaa hakua ensisijaisesti kohti maalisolmua – siis suuntaan, jossa optimaalinen reitti luultavimmin sijaitsee.

#### JPS

Myös JPS:n odotettu aikavaativuus on _O(\|E\|)_, ja se osoittautuikin sovelluksen selvästi nopeimmaksi algoritmiksi. Nopeuden lisäksi reitinhaun visuaalinen tarkastelu paljastaa selvästi, että JPS osaa ohittaa reitinhaun kannalta epäoleelliset solmut ja "vierailee" vain harvoissa optimaalisen reitin ulkopuolelle jäävissä solmuissa. Tämän "hyppimisen" hintana on erilaisten tarkistusten suuri määrä, joka ei kuitenkaan suorituskykyvertailun perusteella hidasta algoritmia merkittävästi.

## Työn puutteet

Yleisesti ottaen olen varsin tyytyväinen sovellukseni rakenteeseen ja koodin laatuun. Käyttöliittymäkoodi jäi melko huonolaatuiseksi, mutta panostin tietoisesti siihen vähemmän kuin sovelluksen muihin osiin.

Koodi varmasti hyötyisi vielä refaktoroinnista ja joidenkin muuttujien ja metodien nimiä voisi käydä ajatuksen kanssa uudelleen lävitse. Lisäksi reitinhakualgoritmeihin jäi edelleen joitain varsin pitkiä metodeja, mutta tätä selittää pitkälti erilaisten tarkistusten suuri määrä.

Alkuperäisenä ajatuksenani oli vertailla jollain tapaa myös algoritmien muistinkäyttöä. Tämä vertailu jäi varsinaisesti toteuttamatta, joskin reitinhakuvisualisoinnin merkitsemät algoritmin tarkastelemat ruudut antavat jonkinlaisen mielikuvan siitä, kuinka paljon muistia algoritmit saattavat (pahimmassa tapauksessa) vaatia.

Toteuttamistani algoritmeista ja tietorakenteista löytyisi epäilemättä myös optimointimahdollisuuksia. Suorituskyky- ja yksikkötestien ansiosta optimointityöhön olisikin nyt varsin helppo ryhtyä.

## Lähteet

- https://en.wikipedia.org/wiki/A*_search_algorithm

- https://en.wikipedia.org/wiki/Breadth-first_search

- https://en.wikipedia.org/wiki/Jump_point_search

- http://theory.stanford.edu/~amitp/GameProgramming/AStarComparison.html

- Harabor, Daniel & Grastien, Alban (2011). [Online Graph Pruning for Pathfinding on Grid Maps](http://users.cecs.anu.edu.au/~dharabor/data/papers/harabor-grastien-aaai11.pdf). NICTA and The Australian National University.

- Moving AI Lab: https://movingai.com/benchmarks/grids.html
  - Kartat ja testiskenaariot: Konstantin Yakovlev & Anton Andreychuk (https://movingai.com/benchmarks/street/index.html)