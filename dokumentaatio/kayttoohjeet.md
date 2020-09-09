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
