# Viikkoraportti 3

Viikon 3 suurin haaste oli odotetusti A\*-algoritmin toteuttaminen. Sain kuitenkin algoritmin (uskoakseni!) toimimaan aivan oikein.

Ensimmäisen viikon palautteessa oli hyvä huomio siitä, ettei leveyshaku tietenkään toimi jos diagonaaliset siirtymät sallitaan, joten korjasin toteutukseni tältä osin. A\*-toteutukseen lisäsin mahdollisuuden sallia diagonaalisiirtymät – tosin täytyy vielä miettiä, pitäisikö kulmittaisten esteiden välistä livahtaminen joka tapauksessa estää. Olen myös valmistaunut mahdollisuuteen antaa kartan solmuille eri painoja kuvaamaan erilaista maastoa ja esim. teitä. Leveyshaun kohdalla tämä ei toki onnistu.

Viikolle mahtui myös melko paljon refaktorointia. Käärin myös reitinhaun tulokset omaan luokkaansa ja muutin graafista käyttöliittymää niin, että algoritmin tarkastelemat solmut värjätään kartalle omalla värillään. Tämä osoittautui yllättävänkin hyväksi tavaksi havainnollistaa algoritmien eroja. Seuraavaksi ajattelin mitata myös algoritmien käyttämää aikaa ja täydentää käyttöliittymää niin että hakutulokset näytettäisiin kunnolla käyttäjälle.

Viikolla 4 päähaasteena on JPS-algoritmin toteuttaminen. Teoriapuoleen olenkin jo tutustunut jonkin verran. Mahdollisti aloitan myös muiden tietorakenteiden korvaamista omilla toteutuksilla.

Dokumentaatio ja JavaDoc ovat jääneet hieman jälkeen, mutta toisaalta moni palikka hakee vielä muotoaan. Vertaisarviovaihetta varten dokumentaatiota olisi kuitenkin syytä täydentää.

Viikolla käytetty tuntimäärä: 10 h
