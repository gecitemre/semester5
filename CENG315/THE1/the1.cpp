#include <iostream>
#include <climits>
using namespace std;
/*If the size of the array is less than K, then sort the array by using insertion sort.(You can use the insertion sort algorithm given to you in THE0.)
Otherwise, split the array into K sub-arrays and do K recursive calls to sort the partitions.
Then, merge K sorted arrays.
When merging K sorted-arrays, you should use a Binary Min Heap to select the minimum element between the minimum elements of K partition arrays.
When creating the array of the heap,
Firstly, generate a linear array whose elements are the minimum elements of the K partition arrays. At the beginning, the position of the each element is determined by the belonging partition. For example, the element coming from partition 0 is placed to heap_array[0] and the element coming from partition 1 is placed to heap_array[1] etc.
Then, heapify the initial array.   
After finding the minimum element, you should insert a new element from the related partition to the Min Heap.
Read the minimum element in the heap and record it.
Then, replace the minimum element with a new element from the partition that has the last minimum element.(New element insertion is not a swap operation. Swap has to be counted only inside the heap or insertion sort.)
Then, heapify the current array.*/

void insertion_sort(int* arr, long &comparison, long & swap, int size) 
{
    int i, j, key;
    for (i = 1; i < size; i++)
    {
        key = arr[i];
        j = i-1;
        while (j >= 0 && arr[j] > key)
        {
            arr[j+1] = arr[j];
            j = j-1;
            swap++;
            comparison++;
        }
        arr[j+1] = key;
        comparison++;
    }
}

void heapify(int* arr, int size, int i, long &comparison, long &swap)
{
    int smallest = i;
    int left = 2*i + 1;
    int right = 2*i + 2;
    if (left < size && arr[left] < arr[smallest])
    {
        smallest = left;
    }
    if (right < size && arr[right] < arr[smallest])
    {
        smallest = right;
    }
    comparison+=2;
    if (smallest != i)
    {
        swap++;
        int temp = arr[i];
        arr[i] = arr[smallest];
        arr[smallest] = temp;
        heapify(arr, size, smallest, comparison, swap);
    }
}

void build_heap(int* arr, int size, long &comparison, long &swap)
{
    for (int i = size/2 - 1; i >= 0; i--)
    {
        heapify(arr, size, i, comparison, swap);
    }
}

int kWayMergeSortWithHeap(int* arr, int K, int size, long& comparison, long& swap){
    int number_of_calls = 1;
    if (size < K) {
        insertion_sort(arr, comparison, swap, size);
        return number_of_calls;
    }
    int copy[size];
    for (int i = 0; i < size; i++)
    {
        copy[i] = arr[i];
    }
    for (int i = 0; i < K; i++) {
        number_of_calls += kWayMergeSortWithHeap(copy + i * (size / K), K, size / K, comparison, swap);
    }
    int* heap = new int[K], *indices = new int[K];
    for (int i = 0; i < K; i++) {
        heap[i] = copy[i * (size / K)];
        indices[i] = 1;
    }
    build_heap(heap, K, comparison, swap);
    int sorted_length = 0;

    while (true) {
        int min2 = INT_MAX, min2_index = -1;
        for (int i = 0; i < K; i++) {
            if (indices[i] < size / K && copy[i * (size / K) + indices[i]] <= min2) {
                min2 = copy[i * (size / K) + indices[i]];
                min2_index = i;
            }
        }
        if (min2_index == -1) {
            break;
        }
        int min = heap[0];
        arr[sorted_length++] = min;
        heap[0] = min2;
        indices[min2_index]++;
        heapify(heap, K, 0, comparison, swap);
    }
    while (sorted_length < size) {
        arr[sorted_length++] = heap[0];
        heap[0] = INT_MAX;
        heapify(heap, K, 0, comparison, swap);
    }
    delete[] heap;
    delete[] indices;
    return number_of_calls;
}



int main()
{
    int size, K;
    size = 12;
    K = 3;
    int arr[size] = { 15, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
    long comparison = 0, swap = 0;
    kWayMergeSortWithHeap(arr, K, size, comparison, swap);
    for (int i = 0; i < size; i++)
    {
        cout << arr[i] << " ";
    }
    cout << endl;
}