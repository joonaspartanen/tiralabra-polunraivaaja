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

