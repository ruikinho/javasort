# Lajittelualgoritmien suorituskykyvertailu

Lajittelualgoritmien suorituskykyä vertaillaan kahdella eri syötekoolla (100 000) ja (1000 000). Satunnainen, valmiiksi nouseva ja laskeva järjestys
---

## 1. Testiympäristö ja toteutus

* **Ohjelmointikieli:** Java (`System.nanoTime()` suoritusajan mittaukseen)
* **Testatut algoritmit:**
  * **Insertion Sort** 
  * **Bubble Sort** 
  * **Shellsort** 
  * **Quicksort**
  * **Arrays.sort** 
* **Testauksen luotettavuus:** Jokaiselle algoritmille luotiin identtinen kopio alkuperäisestä taulukosta (`originalData.clone()`) ennen ajanoton käynnistämistä. Ajanmittaus toteutettiin modulaarisesti funktioreferenssien (`Consumer<int[]>`) avulla.

---

## 2. Teoreettiset aikavaativuudet ($O$-notaatio)

| Algoritmi | Paras tapaus | Keskimääräinen | Pahin tapaus | Huomioita toteutuksesta |
| :--- | :--- | :--- | :--- | :--- |
| **Bubble Sort** | $O(n^2)$ | $O(n^2)$ | $O(n^2)$ | Perusversio ilman `swapped`-keskeytystä; tekee aina täydet kierrokset |
| **Insertion Sort** | $O(n)$ | $O(n^2)$ | $O(n^2)$ | Erittäin nopea valmiiksi nousevassa järjestyksessä olevalle datalle |
| **Shellsort** | $O(n \log n)$ | $O(n^{1.3}) \dots O(n^{1.5})$ | $O(n^2)$ | Tehostaa lisäyslajittelua siirtämällä alkioita aluksi pitkillä askelilla |
| **Quicksort** | $O(n \log n)$ | $O(n \log n)$ | $O(n^2)$ | Keskimmäisen alkion valinta pivotiksi estää pahimman tapauksen |
| **Arrays.sort** | $O(n \log n)$ | $O(n \log n)$ | $O(n \log n)$ | Javan pitkälle optimoitu Dual-Pivot Quicksort |

---

## 3. Mittaustulokset

### Syötekoko $N = 100\,000$

| Algoritmi | Satunnainen (s) | Nouseva (s) | Laskeva (s) |
| :--- | :--- | :--- | :--- |
| **Insertion Sort** | 4,675154 s | 0,001095 s | 9,209929 s |
| **Bubble Sort** | 21,375165 s | 3,196984 s | 6,026677 s |
| **Shellsort** | 0,031299 s | 0,002854 s | 0,004594 s |
| **Quicksort** | 0,027370 s | 0,002806 s | 0,006331 s |
| **Arrays.sort** | 0,036678 s | 0,003587 s | 0,012323 s |

### Syötekoko $N = 1\,000\,000$

| Algoritmi | Satunnainen (s) | Nouseva (s) | Laskeva (s) |
| :--- | :--- | :--- | :--- |
| **Insertion Sort** | 446,618320 s *(~7,4 min)* | 0,012742 s | 839,624119 s *(~14,0 min)* |
| **Bubble Sort** | 2086,782080 s *(~34,8 min)* | 286,857173 s *(~4,8 min)* | 579,420044 s *(~9,7 min)* |
| **Shellsort** | 0,303566 s | 0,038361 s | 0,055449 s |
| **Quicksort** | 0,206795 s | 0,039215 s | 0,038532 s |
| **Arrays.sort** | 0,217143 s | 0,004355 s | 0,006849 s |

---

## 4. Tulosten analyysi ja pohdinta

### Ovatko tulokset järkeviä ja aikavaativuuksien mukaisia?
Kyllä. Mittaustulokset noudattavat teoreettisia aikavaativuuksia erittäin tarkasti, kun syötekoko $N$ kymmenkertaistuu ($100\,000 \rightarrow 1\,000\,000$):

1. **Neliölliset $O(n^2)$-algoritmit ($100\times$-kasvu):**
   Kun alkioiden määrä kymmenkertaistuu, $O(n^2)$-algoritmien suoritusajan pitäisi teoriassa satakertaistua ($10^2 = 100$). Mittauksissa kertoimet olivat lähes täsmälleen 100:
   * **Bubble Sort (satunnainen):** $21,38\text{ s} \rightarrow 2086,78\text{ s}$ (kerroin **97,6x**)
   * **Bubble Sort (laskeva):** $6,03\text{ s} \rightarrow 579,42\text{ s}$ (kerroin **96,1x**)
   * **Insertion Sort (satunnainen):** $4,68\text{ s} \rightarrow 446,62\text{ s}$ (kerroin **95,5x**)
   * **Insertion Sort (laskeva):** $9,21\text{ s} \rightarrow 839,62\text{ s}$ (kerroin **91,2x**)

2. **Lineaarinen $O(n)$-käyttäytyminen ($10\times$-kasvu):**
   Valmiiksi nousevassa järjestyksessä **Insertion Sort** tarkistaa vain kerran, että alkiot ovat paikallaan, jolloin sen aikavaativuus on $O(n)$. Kun $N$ kymmenkertaistui, aika kasvoi suhteessa $0,0011\text{ s} \rightarrow 0,0127\text{ s}$ (kerroin **11,6x**), mikä vastaa lineaarista skaalautumista. **Bubble Sort** (286,86 s) sen sijaan teki kaikki $n^2$ vertailua ilman vaihtoja, koska toteutuksessa ei ollut keskeytystä.

3. **Logaritmiset $O(n \log n)$-algoritmit:**
   **Quicksort** ($0,027\text{ s} \rightarrow 0,207\text{ s}$, kerroin **7,6x**), **Shellsort** ($0,031\text{ s} \rightarrow 0,304\text{ s}$, kerroin **9,7x**) ja **Arrays.sort** ($0,037\text{ s} \rightarrow 0,217\text{ s}$, kerroin **5,9x**) skaalautuivat erittäin tehokkaasti. Pieni suhteellinen nopeutuminen johtuu Javan JIT-kääntäjän optimoinneista pidemmän ajon aikana.

### Erityishuomioita algoritmien eroista
* **Insertion Sortin puolittuminen satunnaisella datalla:** Satunnaisessa järjestyksessä Insertion Sort on noin kaksi kertaa nopeampi (446,6 s) kuin laskevassa järjestyksessä (839,6 s), koska keskimäärin alkio siirtyy vain puoliväliin jo järjestettyä osaa ($\frac{n^2}{4}$ siirtoa) pahimman tapauksen ($\frac{n^2}{2}$ siirtoa) sijaan.
* **Quicksortin optimoinnin merkitys:** Valitsemalla jakoalkio (`pivot`) osataulukon keskeltä vältettiin rekursion epätasapaino ja `StackOverflowError`-virhe valmiiksi järjestetyillä (nouseva ja laskeva) taulukoilla.
* **Javan `Arrays.sort`-metodin ylivoima järjestetyllä datalla:** Miljoonan alkion nousevassa ja laskevassa järjestyksessä `Arrays.sort` oli selvästi nopein (0,004 s ja 0,007 s), koska sen Dual-Pivot Quicksort -toteutus tunnistaa valmiiksi järjestyksessä olevat alijonot suoraan.
