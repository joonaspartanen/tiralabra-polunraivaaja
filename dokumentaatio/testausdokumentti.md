# Testaus

## Yksikkötestit

Ohjelmassa on jonkin verran yksikkötestejä, jotka testaavat hakualgoritmien toimintaa erilaisissa skenaarioissa. Edelleen olisi syytä testata enemmän ohjelman toimintaa esimerkiksi väärillä syötteillä, mutta tämä kytkeytyy virheidenkäsittelyyn, jota en ole vielä ehtinyt kunnolla aloittaa. Kuitenkin jo olemassaolevat yksikkötestit ovat erittäin hyödyllisiä, sillä niiden avulla on helppo varmistua siitä, etteivät algoritmeihin tehdyt muutokset vaikuta niiden toimintaan.

Käyttöliittymä ja kartan piirtävät luokat ovat tällä hetkellä testauksen ulkopuolella. Kartanpiirtäjille saatan kirjoittaa myöhemmin joitain testejä.

![Testikattavuus viikolla 6](https://raw.githubusercontent.com/joonaspartanen/tiralabra-polunraivaaja/master/dokumentaatio/kuvat/testikattavuus_vk6.png)

## Suorituskykytestit

Suorituskykytestien tarkoituksena on vertailla sekä omien tietorakenteita Javan valmiisiin tietorakenteisiin että reitinhakualgoritmien keskinäistä suorituskykyä. Keskeisenä mittarina on molemmissa tapauksissa suoritukseen kulunut aika. Algoritmien kohdalla tarkastellaan myös sitä, kuinka monia kartan ruutuja ne joutuvat käsittelemään suorituksen aikana.

Aloitin suorituskykytestien laatimisen viikolla 5 alustavalla toteutuksella, joka vertailee oman RuutuLista-luokan suoriutumista verrattuna Javan ArrayList-luokkaan.
