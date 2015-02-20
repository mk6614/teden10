# Naloge v prvem tednu


Naloge za prvi teden tehničnega predmeta Scala na FRI.
V prvem tednu se bomo predvsem spoznali s tehničnimi podrobnostmi in osnovnimi konstrukti Scale. Videli bomo, da so zanke povsem nepotrebni konstrukti v programskih jezikih :smile:, zato bomo odslej programirali brez njih.

Izdelava vaše domače naloge bo potekala na sledeči način

 1. Z orodjem `git` ustvarite lokalno kopijo tega repozitorija.

        git clone http://github.com/scalaFRI/teden1
 2. Postavite se v delovni direktorij tega repozitorija in poženite `sbt`.
 3. V urejevalniku odprite datoteki `Main.scala` in `MainTests.scala`. Prva bo vsebovala izvorno kodo vaših rešitev, druga pa izvorno kodo vaših testov. Pred pisanjem vsake funkcije vam svetujemo, da si najprej zapišete enega ali več testov, ki bodo preverjali pravilnost te funkcije. V prvem tednu imate nekaj testov že zapisanih, sami pa dodajte še nove.
 4. V `sbt` lupini med pisanjem rešitev testirajte, če vaša koda že deluje. Uporabite ukaze `compile`, `test` in `console`.
 5. Ko boste vse naloge rešili (ali pa bo konec vaj), na učilnici oddajte datoteki `Main.scala` in `MainTests.scala`.

V nadaljevanju so podana navodila nalog. Naloge označene z :crown: (t.i. carske naloge) bodo načeloma malo težje in se jih lotite šele, ko končate vse ostalo.  
*N.B.* v rešitvah nikoli ne uporabite spremenljivk (var) ali kakršnihkoli zank. Vse implementirajte zgolj z vrednostmi (val) in rekurzijo.

## Naloga 1.
  Napiši funkcijo `pyramid(n:Int):String`, ki vrne smrekico z n vrsticami
  Npr. `pyramid(5)` vrne niz:
```
    *
   ***
  *****
 *******
*********
```

## Naloga 2.
Napišite funkcijo `uniq(s:String): String`, ki za podani niz vrne isti niz, kjer v zaporednem nizu istih znakov ohrani zgolj enega - npr. pri vhodu `aabbba`,  dobimo rezultat `aba`.


## Naloga 3.:crown:
Napišite funkcijo `isLycherel(n: BigInt): Boolean`, ki za podano število (števila so lahko zelo velika, zato se uporablja `BigInt`) ugotovi, ali je to verjetno *Lycherelovo število*.
Več podrobnosti o teh številih in postopku preverjanja teh števil lahko najdete v nalogi iz projekta Euler.
 https://projecteuler.net/problem=55
