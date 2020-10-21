# Testaus

## Yksikkötestit

Ohjelman yksikkötestit testaavat varsin kattavasti sekä reitinhakualgoritmien että omien tietorakenteiden toimintaa. Yksikkötestien pääasiallisena tarkoituksena oli varmistaa, että algoritmit ja tietorakenteet toimivat oikein sekä kehitys- että refaktorointivaiheessa. Tämä tavoite täyttyi testeillä oikein hyvin.

Sovelluksen testit voi ajaa komennolla `mvn test`.

Testikattavuusraportin voi puolestaan generoida komennolla `mvn test jacoco:report`. Raportti löytyy polusta `/target/site/jacoco/index.html`.

Testikattavuusraportista nähdään, että yksikkötestien rivi- ja haaraumakattavuus on varsin korkea:

![Lopullinen testikattavuus](https://raw.githubusercontent.com/joonaspartanen/tiralabra-polunraivaaja/master/dokumentaatio/kuvat/testikattavuus_vk6.png)

Sovelluksen käyttöliittymä ja karttojen piirtämisestä vastaava Kartanpiirtaja-luokka on jätetty testien ulkopuolelle.

## Suorituskykytestit

Suorituskykytestien tarkoituksena on vertailla sekä omien tietorakenteita Javan valmiisiin tietorakenteisiin että reitinhakualgoritmien keskinäistä suorituskykyä. Keskeisenä mittarina on molemmissa tapauksissa suoritukseen kulunut aika.

Suorituskykytestit voi ajaa käyttöliittymän Suorituskykytestit-välilehdeltä.

### Testikokoonpano

Lenovo ThinkPad T490

- prosessori 4-ytiminen, 8-säikeinen Intel Core i5-8265U, 1,6-3,9 GHz
- keskusmuisti 16 Gt
- SSD-levy 512 Gt

### Tietorakenteiden suorituskykytestit

Tietorakenteiden suorituskykytestit vertaavat itse toteuttamiani tietorakenteita Javan vastaaviin rakenteisiin eri määrillä suoritettuja operaatioita (kuten alkioiden lisäämisiä listalle/jonoon/kekoon).

Luotettavien tulosten saamiseksi kukin suorituskykytesti ajetaan 100 kertaa, ja lopullisen vertailutuloksen ajat muodostetaan laskemalla suorituskertojen kestojen mediaani.

Tuloksista nähdään, että omat tietorakenteeni ovat kutakuinkin yhtä tehokkaita kuin Javan valmiit tietorakenteet. Ainoastaan oma binäärikekototeutukseni näyttäisi jäävän jälkeen Javan PriorityQueue-rakenteesta suurilla operaatiomäärillä.

| Operaatioita | RuutuLista (ms) | ArrayList (ms) |
| :----------: | :-------------: | :------------: |
|     1000     |      0.089      |     0.082      |
|    10000     |      0.253      |     0.234      |
|    100000    |      2.229      |     2.177      |
|   1000000    |     26.625      |     16.964     |
|   10000000   |     483.086     |    395.031     |

| Operaatioita | RuutuJono (ms) | ArrayDeque (ms) |
| :----------: | :------------: | :-------------: |
|     1000     |     0.029      |      0.040      |
|    10000     |     0.076      |      0.114      |
|    100000    |     0.687      |      0.983      |
|   1000000    |     17.487     |     12.487      |
|   10000000   |    162.196     |     395.795     |

| Operaatioita | RuutuKeko (ms) | PriorityQueue (ms) |
| :----------: | :------------: | :----------------: |
|     1000     |     0.591      |       0.514        |
|    10000     |     3.017      |       2.399        |
|    100000    |     24.230     |       26.500       |
|   1000000    |    644.870     |      270.734       |
|   10000000   |    8475.998    |      3445.525      |

Alla olevissa kuvaajissa tietorakenteiden suoritusaika on suhteutettu toisiinsa niin, että Javan valmiin tietorakenteen suoritusaika on 1 (siis lyhyempi palkki on parempi):

![RuutuListan testin tulos](https://raw.githubusercontent.com/joonaspartanen/tiralabra-polunraivaaja/master/dokumentaatio/kuvat/ruutulista.svg)

![RuutuJonon testin tulos](https://raw.githubusercontent.com/joonaspartanen/tiralabra-polunraivaaja/master/dokumentaatio/kuvat/ruutujono.svg)

![RuutuKeon testin tulos](https://raw.githubusercontent.com/joonaspartanen/tiralabra-polunraivaaja/master/dokumentaatio/kuvat/ruutukeko.svg)

### Algoritmien suorituskykytestit


Reitinhakualgoritmien suorituskykytestit testaavat kunkin algoritmin suoritusnopeutta skenaarioilla, joihin kuuluu satoja reittikuvauksia tietyllä kartalla.

Suorituskykytestin aikana kukin skenaarioon kuuluva reitti haetaan kullakin algoritmilla 41 kertaa (ensimmäinen suorituskerta hylätään). Kunkin suorituskerran kesto otetaan muistiin, minkä jälkeen lasketaan kestojen mediaani. Lopullisessa mittaustuloksessa raportoidaan näiden mediaanien yhteenlaskettu kesto.

Testit voi ajaa sovelluksen Suorituskykytestit-välilehdeltä useilla eri testiskenaarioilla. Dokumentoin tähän tiedostoon tulokset, jotka saavutin skenaarioilla _Berlin_0_512.map.scen_ (512x512 ruutua, 1870 reittikuvausta) ja _Berlin_0_1024.map.scen_ (1024x1024 ruutua, xxx reittikuvausta). Skenaariot käyttävät siis datana kartan _Berlin_0_ eri resoluutioisia versioita.

Analysoin algoritmien eroja tarkemmin [toteutusdokumentissa](https://github.com/joonaspartanen/tiralabra-polunraivaaja/blob/master/dokumentaatio/toteutusdokumentti.md), mutta tässäkin yhteydessä voidaan nostaa esiin joitain keskeisiä havaintoja.

Tulokset vastaavat varsin hyvin ennakko-odotuksiani: suurella kartalla leveyshaku jää hitaammaksi kuin vastaavan reitin löytävä A\*-implementaatio, ja JPS toimii selvästi nopeimmin. Kuitenkin pienemmällä kartalla leveyshaku on jopa A\*:a nopeampi, mikä selittynee ennen kaikkea sillä, että algoritmi-implementaatio on A\*:a yksinkertaisempi ja kevyempi. Lisäksi testiskenaariot sisältävät useita hyvin lyhyitä reittejä, joiden uskoisin suosivan kevyempää leveyshakua.

|          Skenaario           | Leveyshaku | A\* (ilman diagonaalisiirtymiä) | A\* (diagonaalisiirtymillä) |  JPS   |
| :--------------------------: | :--------: | :-----------------------------: | :-------------------------: | :----: |
| Berlin_0_512 (1870 reittiä)  |  10,24 s   |             14,50 s             |           22,10 s           | 3,52 s |
| Berlin_0_1024 (1202 reittiä) |  45,65 s   |             42,31 s             |           67,99 s           | 9,63 s |

![Algoritmien testin tulos](https://raw.githubusercontent.com/joonaspartanen/tiralabra-polunraivaaja/master/dokumentaatio/kuvat/algoritmit.svg)
