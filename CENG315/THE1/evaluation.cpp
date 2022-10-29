#include "the1.h"

int test(int *arr, int size, int K) {
    long comparison = 0;
    long swap = 0;
    int calls = kWayMergeSortWithHeap(arr, K, size, comparison, swap);
    std::cout << "Sorted array: ";
    for (int i = 0; i < size; i++) {
        std::cout << arr[i] << " ";
    }
    std::cout << std::endl;
    std::cout << "Number of comparisons: " << comparison << std::endl;
    std::cout << "Number of swaps: " << swap << std::endl;
    std::cout << "Number of calls: " << calls << std::endl;
    return 0;
}

int main() {
    int size = 7, K = 7;
    int arr[7] = {7, 6, 5, 4, 3, 2, 1};
    test(arr, size, K);

    size = 10, K = 15;
    int arr2[10] = {20, 45, 65, 78, 98, 65, 32, 74, 9, 1};
    test(arr2, size, K);

    size = 16, K = 4;
    int arr3[16] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    test(arr3, size, K);

    size = 20, K = 5;
    int arr4[20] = {79, 63, 21, 78, 52, 63, 45, 10, 0, 1, 22, 100, 89, 66, 2, 63, 89, 98, 99, 785};
    test(arr4, size, K);
}
