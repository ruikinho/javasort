import java.util.*;
import java.util.function.Consumer;

public class SortTest {

    // 1. Insertion Sort
    public static void insertionSort(int[] data) {
        int n = data.length;
        for (int i = 1; i < n; ++i) {
            int key = data[i];
            int j = i - 1;
            while (j >= 0 && data[j] > key) {
                data[j + 1] = data[j];
                j = j - 1;
            }
            data[j + 1] = key;
        }
    }

    // 2. Bubble Sort
    public static void bubbleSort(int[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
    }

    // 3. Shellsort
    public static void shellSort(int[] data) {
        int n = data.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = data[i];
                int j = i;
                while (j >= gap && data[j - gap] > temp) {
                    data[j] = data[j - gap];
                    j -= gap;
                }
                data[j] = temp;
            }
        }
    }

    // 4. Quicksort
    public static void quickSort(int[] data) {
        quickSortRecursive(data, 0, data.length - 1);
    }

    private static void quickSortRecursive(int[] data, int low, int high) {
        if (low >= high) return;
        int pivot = data[low + (high - low) / 2];
        int i = low, j = high;
        while (i <= j) {
            while (data[i] < pivot) i++;
            while (data[j] > pivot) j--;
            if (i <= j) {
                int temp = data[i];
                data[i] = data[j];
                data[j] = temp;
                i++;
                j--;
            }
        }
        if (low < j) quickSortRecursive(data, low, j);
        if (i < high) quickSortRecursive(data, i, high);
    }

    //Apumetodit taulukoiden luontiin

    public static int[] createAscendingTable(int n) {
        int[] data = new int[n];
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }
        return data;
    }

    public static int[] createDescendingTable(int n) {
        int[] data = new int[n];
        for (int i = 0; i < data.length; i++) {
            data[i] = n - 1 - i;
        }
        return data;
    }

    public static int[] createRandomData(int n) {
        int[] data = new int[n];
        Random gen = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = gen.nextInt(n);
        }
        return data;
    }

    //Modulaarinen suoritusajan testaja

    public static void sortMethodTester(String name, Consumer<int[]> sortMethod, int[] originalData) {
        int[] copy = originalData.clone();
        long start = System.nanoTime();
        sortMethod.accept(copy);
        long stop = System.nanoTime();
        System.out.printf("%-16s %.6f s%n", name + ":", (stop - start) / 1_000_000_000.0);
    }

    public static void runAllTests(String testName, int[] data) {
        System.out.println("\n=== " + testName + " (N = " + data.length + ") ===");
        sortMethodTester("Insertion sort", SortTest::insertionSort, data);
        sortMethodTester("Bubble sort", SortTest::bubbleSort, data);
        sortMethodTester("Shellsort", SortTest::shellSort, data);
        sortMethodTester("Quicksort", SortTest::quickSort, data);
        sortMethodTester("Arrays.sort", Arrays::sort, data);
    }

    public static void main(String[] args) {
        int size = 1000000;

        runAllTests("Satunnainen järjestys", createRandomData(size));
        runAllTests("Nouseva järjestys", createAscendingTable(size));
        runAllTests("Laskeva järjestys", createDescendingTable(size));
    }
}