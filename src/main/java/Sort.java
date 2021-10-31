package main.java.Sort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sort {
    private int[] quickSort(int[] unsorted, int low, int high, int recDepth) {
        if (high - low <= 1) return unsorted;
        int pivot;
        int a = unsorted[low];
        int b = unsorted[high - 1];
        int c = unsorted[low + (high - low) / 2];

        if ((a < b && b < c) || (c < b && b < a))
            pivot = b;
        else if ((b < a && a < c) || (c < a && a < b))
            pivot = a;
        else
            pivot = c;

        int lowIndex = low;
        int highIndex = high - 1;
        int[] temp = unsorted.clone();
        for (int i = low; i < high; i++) {
            int current = temp[i];
            if (current < pivot) {
                unsorted[lowIndex] = current;
                lowIndex++;
            } else if (current > pivot) {
                unsorted[highIndex] = current;
                highIndex--;
            }
        }
        int pivotIndex = lowIndex;
        for (int i = lowIndex; i <= highIndex; i++) {
            unsorted[i] = pivot;
        }
        if (lowIndex == low && highIndex + 1 == high) {
            return unsorted;
        }
        if (pivotIndex == low) pivotIndex++;
        quickSort(unsorted, low, pivotIndex, recDepth + 1);
        quickSort(unsorted, pivotIndex, high, recDepth + 1);
        return unsorted;
    }
    private int[] mergeSort(int[] unsorted, int low, int high, int recDepth) {
        if (high - low <= 1) return unsorted;
        int split = low + (high - low) / 2;
        int[] temp = unsorted.clone();
        mergeSort(temp, low, split, recDepth + 1);
        mergeSort(temp, split, high, recDepth + 1);
        int a = low;
        int b = split;
        int i = low;
        while (a < split && b < high) {
            if (temp[a] <= temp[b]) {
                unsorted[i] = temp[a];
                a++;
            } else {
                unsorted[i] = temp[b];
                b++;
            }
            i++;
        }
        if (a < split) {
            System.arraycopy(temp, a, unsorted, i, split - a);
        } else if (b < high) {
            System.arraycopy(temp, b, unsorted, i, high - b);
        }
        return unsorted;
    }
    private int[] naturalMergeSort(int[] unsorted) {
        ArrayList<Integer> runs = new ArrayList<>();
        runs.add(0);
        boolean reverse = false;
        System.out.println(Arrays.toString(unsorted));
        int lastRun = 0;
        for (int i = 0; i < unsorted.length - 1; i++) {
            System.out.println(unsorted[i] + ":" + reverse);
            if (i + 1 == unsorted.length - 1 && (i - lastRun == 0) &&  unsorted[i] > unsorted[i + 1]) {
                System.out.println(i - lastRun);
                System.out.println("Set to reverse");
                reverse = true;
                int temp = unsorted[i];
                unsorted[i] = unsorted[i + 1];
                unsorted[i + 1] = temp;
            }else if ((i - lastRun == 0) &&  unsorted[i] > unsorted[i + 1]) {
                    System.out.println(i - lastRun);
                    System.out.println("Set to reverse");
                    reverse = true;
            } else if (!reverse && unsorted[i] > unsorted[i + 1]) {
                runs.add(i + 1);
                lastRun = i + 1;
                System.out.println("terminated run at " + (i + 1));
            } else if (reverse && unsorted[i] < unsorted[i + 1]) {
                reverse = false;
                System.out.println((i+1) / 2);
                for (int j = lastRun; j < (i + 1) / 2; j++) {
                    System.out.println(j);
                    int temp = unsorted[j];
                    unsorted[j] = unsorted[i - j];
                    unsorted[i - j] = temp;
                    System.out.println(Arrays.toString(unsorted));
                }
                runs.add(i + 1);
                lastRun = i + 1;
                System.out.println("terminated reverse run at " + (i + 1));
            }
        }
        System.out.println(Arrays.toString(unsorted));
        System.out.println(runs);
        if (runs.size() == 0 || runs.get(runs.size() - 1) != unsorted.length) runs.add(unsorted.length);
        natMerge(unsorted, runs, 0, runs.size() - 1);
        return unsorted;
    }
    private int[] natMerge(int[] unsorted, ArrayList<Integer> runs, int low, int high) {
        if (high - low <= 1) return unsorted;
        int split = low + (high - low) / 2;
        natMerge(unsorted, runs, low, split);
        natMerge(unsorted, runs, split, high);
        natMerge(unsorted, runs.get(low), runs.get(split), runs.get(high));
        return unsorted;
    }
    private int[] natMerge(int[] unsorted, int low, int split, int high) {
        int[] temp = unsorted.clone();
        int a = low;
        int b = split;
        int i = low;
        while (a < split && b < high) {
            if (temp[a] <= temp[b]) {
                unsorted[i] = temp[a];
                a++;
            } else {
                unsorted[i] = temp[b];
                b++;
            }
            i++;
        }
        if (a < split) {
            System.arraycopy(temp, a, unsorted, i, split - a);
        } else if (b < high) {
            System.arraycopy(temp, b, unsorted, i, high - b);
        }
        return unsorted;
    }
    private int[] radixSort(int[] unsorted) {
        int max = 0;
        for (int i = 0; i < unsorted.length; i++) {
            int oneIndex = Integer.numberOfTrailingZeros(Integer.highestOneBit(unsorted[i]));
            if (unsorted[i] <= 0) oneIndex = -1;
            if (oneIndex > max) max = oneIndex;
        }
        return radixSort(unsorted, max, 0, unsorted.length, 0);
    }
    private int[] radixSort(int[] unsorted, int radix, int low, int high, int recDepth) {
        int[] temp = unsorted.clone();
        int lowIndex = low;
        int highIndex = high - 1;
        for (int j = low; j < high; j++) {
            if ((temp[j] & (1L << radix)) != 0) {
                unsorted[highIndex] = temp[j];
                highIndex--;
            } else {
                unsorted[lowIndex] = temp[j];
                lowIndex++;
            }
        }
        highIndex++;
        if (radix == 0) return unsorted;
        radixSort(unsorted, radix - 1, low, highIndex, recDepth + 1);
        radixSort(unsorted, radix - 1, highIndex, high, recDepth + 1);
        return unsorted;
    }

    private void shuffle(int[] unshuffled) {
        for (int i = unshuffled.length - 1; i > 1; i--) {
            int j = (int) (Math.random() * (unshuffled.length));
            int temp = unshuffled[i];
            unshuffled[i] = unshuffled[j];
            unshuffled[j] = temp;
        }
    }
    private int[] generateArray(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = i;
        }
        return res;
    }
    private int[] generateRandomArray(int n, int max) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = (int) (Math.random() * (max));
        }
        return res;
    }
    private boolean isSorted(int[] test) {
        if (test == null) return false;
        boolean sorted = true;
        for (int i = 0; i < test.length - 1; i++) {
            if (test[i] > test[i + 1]) {
                sorted = false;
                break;
            }
        }
        return sorted;
    }
    private void print(int[] array, int low, int high) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            if (i >= low && i < high) {
                System.out.print(array[i]);
            } else {
                System.out.print("-");
            }
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    public static void main(String[] args) {
        Sort sort = new Sort();
        int n = 10;
        int k = 1;
        boolean print = true;

        int minLen = 4;

        long start;
        int[] unsorted;
        int[] sorted;
        long mergesort = 0;
        long naturalmergesort = 0;
        long quicksort = 0;
        long radixSort = 0;
        long builtIn = 0;

        for (int i = 0; i < k; i++) {
            //unsorted = sort.generateRandomArray(n, n / 4);
            unsorted = sort.generateArray(n);
            sort.shuffle(unsorted);
            if (print) System.out.println("Unsorted:  " + Arrays.toString(unsorted));
            start = System.currentTimeMillis();
            sorted = sort.mergeSort(unsorted.clone(), 0, unsorted.length, 0);
            mergesort += System.currentTimeMillis() - start;
            if (print) System.out.println("Mergesort: " + Arrays.toString(sorted));
            start = System.currentTimeMillis();
            sorted = sort.quickSort(unsorted.clone(), 0, unsorted.length, 0);
            quicksort += System.currentTimeMillis() - start;
            if (print) System.out.println("Quicksort: " + Arrays.toString(sorted));
            start = System.currentTimeMillis();
            sorted = sort.naturalMergeSort(unsorted.clone());
            naturalmergesort += System.currentTimeMillis() - start;
            if (print) System.out.println("NatMerge:  " + Arrays.toString(sorted));
            start = System.currentTimeMillis();
            sorted = sort.radixSort(unsorted.clone());
            radixSort += System.currentTimeMillis() - start;
            if (print) System.out.println("RadixSort: " + Arrays.toString(sorted));
            start = System.currentTimeMillis();
            sorted = unsorted.clone();
            Arrays.sort(sorted);
            builtIn += System.currentTimeMillis() - start;
            if (print) System.out.println("Built-in:  " + Arrays.toString(sorted));
        }
        if (print) System.out.println();

        System.out.println("Mergesort");
        System.out.println("Time:      " + (mergesort / (double)k) + "ms");

        System.out.println("\nNatural Mergesort");
        System.out.println("Time:      " + (naturalmergesort / (double)k) + "ms");

        System.out.println("\nQuicksort");
        System.out.println("Time:      " + (quicksort / (double)k) + "ms");

        System.out.println("\nRadixSort");
        System.out.println("Time:      " + (radixSort / (double)k) + "ms");

        System.out.println("\nBuilt-in");
        System.out.println("Time:      " + (builtIn / (double)k) + "ms");
    }
}
