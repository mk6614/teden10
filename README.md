# Naloge v desetem tednu

V desetem tednu se bomo lotili implementacije domensko specifičnega jezika za delo s [CSV](https://en.wikipedia.org/wiki/Comma-separated_values) datotekami.


### Primer programa v tem jeziku
Implementirani domensko specifični jezik bo še vedno povsem legalna scala koda, sintaktično pa bo zgledala precej drugače.
Za prvi občutek implementiranega jezika  si poglejmo manjši primer.

```scala
("dat1.csv", '\t') >>> (
"dat2.csv" -->
revrow -->
(drop,0) -->
(swap, 0, 1) -->
screen -->
(out, "out.csv") )
```

Razlaga primera:
  1. Najprej odpremo podatke iz datoteke "dat1.csv" (ločilo je tabulator). Ta datoteka je naš glavni vir podatkov.
  2. Te podatke pošljemo (operator >>>) v zaporedje akcij, ki jih bodo ustrezno spremenile.
  3. Prva akcija dobljene podatke združi s podatki iz "dat2.csv" (pripne istoležne vrstice)
  3. Druga akcija v združenih podatkih obrne vse vrstice (revrow)
  4. Nato odstranimo stolpec 0 (prvi stolpec) (drop)
  5. Zamenjamo novi prvi in drugi stolpec (swap)
  6. Izpišemo tabelo na zaslon (screen)
  7. Izpišemo v datoteko "out.csv" (out)

Z operatorjem --> združimo različne akcije v neke vrste cevovod, s katerim obdelujemo posamezne vrstice.



## Naloga 1.

V prvi nalogi boste implementirali dva razreda
```scala
case class CsvData
case class CsvAction
```
in ustrezne implicitne pretvorbe, ki bodo omogočile uporabo teh razredov v domensko specifičnem jeziku. Vse implicitne pretvorbe implementirajte znotraj objekta
```scala
object CSVLang
```
zato, da boste domensko specifičen jezik aktivirali z
```scala
import CSVLang._
```


### Viri podatkov
Viri podatkov bodo datoteke, ki bodo imele za privzeto ločilo vejico, lahko pa jim podamo tudi drugačno ločilo.

Pogledali si bomo 4 načine kako lahko podamo vire podatkov.
```scala
"datoteka.csv" //samo ime datoteke (privzeto ločilo ,)
```
ali
```scala
("datoteka.csv". ';') //ime datoteke in ločilo, ki se uporablja
```
ali
```scala
// neposredno kot niz (tu naj se uporablja samo vejica)
"""
a,1,2,3
b,2,3,4
c,3,4,5
"""
```
ali
```scala
ask // vprasaj uporabnika po csv datoteki (zopet samo za datoteke ločene z vejico)
```

Za zadnjo možnost (ask) uporabite FileChooser, primere uporabe pa najdene na:
http://www.cis.upenn.edu/~matuszek/cis554-2011/Pages/scala-io-code-samples.html

Vir podatkov bo predstavljen z razredom
```scala
case class CsvData(data:Stream[Stream[String]], separator:Char){
  def >>>(action: CsvAction): CsvData
}
```
kjer je *data* seznam vrstic, vsaka je predstavljena kot seznam nizov.

### CSV akcije

Akcije nad  CSV podatki bomo predstavili z razredom

```scala
trait CsvAction{
    def apply(data:CsvData):CsvData
    def -->(action:CsvAction):CsvAction
}
```
Funkcionalnost akcije je zajeta v funkciji apply, ki sprejme objekt tipa CsvData in vrne spremenjene podatke.

Funkcija *->* je zadolžena za združevanje dveh akcij v novo akcijo.

Akcije, ki jih podprite naj bodo naslednje:
  1. revrows - akcija brez argumnetov, obrne vse vrstice.
  2. drop - akcija z enim argumentom, indeksom stolpca, ki naj ga ta akcija odstrani iz csv-ja.
  3. swap - akcija z dvema argumentoma, indeksoma stolpcev, ki ju zamenjamo.
  4. screen - akcija, ki podatke izpiše na zaslon, vrne pa nespremenjene podatke.
  5. out - akcija, ki shrani csv v datoteko - podamo ime datoteke, vrne nespremenjene podatke.

Vsi CSV podatki (v formatih kot smo jih definirali zgoraj), naj bodo tudi CSV akcije, ki podane podatke združi s podatki dobljenimi kot vhod te akcije (doda jih kot stolpce na koncu). Če je katera tabela daljša, naj jo odreže.

Npr. spodnji primer doda stolpce iz bla2.csv k stolpcem iz bla.csv in vse skupaj izpiše na zaslon.
```scala
"bla.csv" >>> ("bla2.csv",';') --> screen
```

## Naloga 2.:crown:

Pri drugi nalogi boste razširili jezik s še dvema funkcionalnostima:
  1. Preverjanje formata
  2. Računanje izrazov

### Preverjanje formata

Implementirali boste novo akcijo, ki bo preverila format podatkov v vsaki vrstici. Če podatki v nekem stolpcu ne bodo ustrezali željenemu formatu, jih bo nadomestila z neko privzeto vrednostjo.

Format bo predstavljen z razredom
```scala
trait CsvFormat {

  def |(other: CsvFormat): CsvFormat
  def checkAndMap(l:Stream[String]):Stream[String]
}
```

kjer bo operator | uporabljen za združevanje dveh formatov v nov format, metoda checkAndMap pa bo vzela eno vrstico tabele in vrnila novo vrstico, ki bo ustrezala željenemu formatu.

Podprli boste tri vrste formatov:
 1. celo število (ključna beseda int)
 2. realno število (ključna beseda dbl)
 3. poljuben niz (ključna beseda ign)

Celim številom in realnim številom podamo privzeto vrednost, npr. int(5) pomeni, da mora biti vrednost celo število, če temu ni tako, naj se zapiše v stolpec vrednost 5.

 Poleg privzete vrednosti lahko podamo tudi funkcijo Int=>Boolean (Double=>Boolean), s katero opišemo katera števila so na tem mestu dovoljena. Npr. dbl(x=> x>20.3, 20.3) preveri če je v stolpcu realno število, obenem pa še če je število večje od 20.3. Če temu ni tako, potem to vrednost nadomesti z 20.3.

Za lažje razumevanje si oglejmo še krajši primer izraza.
```scala
int(_>5,5)|dbl(7.0)|ign|int(3)
```
  1. Prvi stolpec mora biti celo število, večje od 5. Če format ni pravilen ali če je število manjše kot 5, potem se nadomesti s 5.
  2. Drugi stolpec mora biti realno število (double), če format ni pravilen naj se nadomesti s 7.0.
  3. Tretji stolpec je lahko poljuben (preverjanje formata ga pusti nedotaknjenega)
  4. Četrti stolpec mora biti celo število, sicer se nadomesti s 3.
  5. Ostali stolpci ostanejo nedotaknjeni, če pa je stolpcev manj kot 4, naj pride do izjeme (exception).

Ta izraz se pretvori v ustrezno CSV akcijo in se uporabi v zaporedju akcij za zagotovitev pravega formata stolpcev.



### Izrazi
Kot zadnjo funkcionalnost implementirajte akcijo, s katero boste lahko računali izraze znotraj ene vrstice.
Ta akcija naj doda vhodni tabeli nov stolpec (na konec) in v njega zapiše rezultat podanega izraza.

V izrazih lahko nastopajo realne konstante in spremenljivke, ki podajo vrednost kot zaporedno številko stolpca v katerem le-ta nastopa (npr. col(4) predstavlja vrednost v četrtem stolpcu). V izrazu lahko nastopa tudi posebna vrednost (akumulator), ki ima ime acc, predstavlja pa prenešeno vrednost iz predhodne vrstice (podobno kot vrednost pri foldLeft). Ta vrednost naj ima vedno na začetku vrednost 0.

Izrazi naj bodo predstavljeni (oz. izpeljani) iz razreda
```scala
trait CsvExpr {
  def |+(other: CsvExpr): CsvExpr
  def |*(other: CsvExpr): CsvExpr
  def |/(other: CsvExpr): CsvExpr
  def eval(line: Stream[String], acc:Double): Double
}
```
Podpirajo naj tri operacije (seštevanje, odštevanje, deljenje), funkcija eval pa naj za podano vrstico in podano trenutno vrednost akumulatorja - tako boste lahko prenašali vrednost acc med posameznimi vrsticami.

Enostaven primer izraza:
```scala
3.0 |+ col(1) |/ col(2) |* acc
```

### Primer
Primer programa s katerim lahko dobimo povprečno vrednost nekega stolpca.

```scala
"bla.csv" >>>
ign|ign|dbl(0) -->
1 + acc -->
col(3)+acc -->
col(5) |\ col(4) -->
(out, "result.csv")
```
Denimo, da so v bla.csv zgolj tri vrstice
 1. najprej s preverjanjem formata definiramo tretji stolpec kot realno število
 2. nato preštejemo število vrstic, ki se zapisuje v četrti stolpec
 3. v peti stolpec se shrani vsota tretjega stolpca
 4. v šesti stolpec se nazadnje shrani povprečna vrednost tretjega stolpca.
 Vse skupaj se shrani v result.csv.
