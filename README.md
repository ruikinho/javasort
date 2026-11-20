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

---

## 2. Teoreettiset nopeusluokat (miten aika kasvaa, kun data lisääntyy)

Kun lukujen määrä (n) **10-kertaistuu** (esim. 100 000 $\rightarrow$ 1 000 000):
* **Lineaarinen (O(n)):** Aika **10-kertaistuu** (erittäin nopea).
* **Logaritminen (O(n log n)):** Aika kasvaa vain **noin 12-kertaiseksi** (tehokas).
* **Neliöllinen (O(n^2)):** Aika **100-kertaistuu** ($10 \times 10 = 100$, erittäin hidas).

| Algoritmi | Valmiiksi järjestyksessä | Satunnainen järjestys | Täysin väärinpäin | Käytännön selitys |
| :--- | :--- | :--- | :--- | :--- |
| **Bubble Sort** | Hidas ($100\times$) | Hidas ($100\times$) | Hidas ($100\times$) | Käy listan läpi aina kokonaan, vaikka se olisi jo valmis |
| **Insertion Sort** | **Supernopea ($10\times$)** | Hidas ($100\times$) | Hidas ($100\times$) | Valmiilla listalla vain tarkistaa järjestyksen yhdellä läpikäynnillä |
| **Shellsort** | Tehokas | Tehokas | Hidas ($100\times$) | Siirtää lukuja aluksi pitkillä hypyillä, mikä nopeuttaa loppua |
| **Quicksort** | Tehokas | Tehokas | Tehokas | Jakaa listan aina keskeltä kahtia, joten se pysyy nopeana kaikilla listoilla |
| **Arrays.sort** | Tehokas | Tehokas | Tehokas | Javan oma huippuunsa viritetty valmis lajittelu |

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
Tulokset olivat järkeviä ja aikavaativuuksien mukaisia, kun lukujen määrä (N) 10-kertaistui (100 000 -> 1 000 000):

* **Hitaat algoritmit (O(n^2)):** Bubble sortilla ja Insertion sortilla aika kasvoi suunnilleen 100-kertaiseksi, kun lukuja oli 10 kertaa enemmän. Esimerkiksi Bubble sortilla satunnaisella listalla aika nousi noin 21 sekunnista yli 2086 sekuntiin (melkein 35 minuuttiin). Laskevassa järjestyksessä Insertion sortilla aika nousi 9 sekunnista 839 sekuntiin. Tämä vastaa hyvin O(n^2)-aikavaativuutta.
* **Nouseva järjestys ja O(n):** Valmiiksi järjestyksessä olevalla listalla Insertion sort oli tosi nopea (0,001 s -> 0,012 s), koska sen tarvitsi vain käydä lista kerran läpi ja todeta että se on jo kunnossa (O(n)). Bubble sortilla meni silti melkein 5 minuuttia miljoonalla luvulla, koska siinä ei ollut mitään tarkistusta joka lopettaisi sen ajoissa, vaan se kävi kaikki kierrokset läpi turhaan.
* **Nopeat algoritmit (O(n log n)):** Shellsort, Quicksort ja Javan oma Arrays.sort olivat kaikki todella nopeita jopa miljoonalla luvulla (alle sekunnin). Niillä aika kasvoi vain noin 6–10-kertaiseksi, kun lukujen määrä 10-kertaistui.

### Muita huomioita
* **Insertion sortin ero satunnaisella ja laskevalla listalla:** Satunnaisella listalla Insertion sort oli noin tuplasti nopeampi (446 s) kuin laskevalla listalla (839 s). Se käy järkeen, koska väärinpäin olevalla listalla jokainen luku joudutaan siirtämään ihan alkuun asti, kun taas satunnaisella listalla niitä siirretään keskimäärin vain puoliväliin.
* **Quicksortin optimointi:** Kun Quicksortissa valittiin jakoalkio (pivot) keskeltä eikä reunasta, se ei kaatunut (`StackOverflowError`) valmiiksi järjestetyillä listoilla, vaan pysyi tosi nopeana (n. 0,039 s).
