#include <iostream>
using namespace std;

void insertionSort(int* arr, long &comparison, long & swap, int size) 
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
