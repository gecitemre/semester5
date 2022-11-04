#include "the2.h"
#include <cmath>
#include <iostream>

long multiDigitRadixSort(int *arr, bool ascending, int arraySize, int groupSize, int maxDigitLength)
{
    long numberOfIterations = 0;
    int base = (int)pow(10, groupSize);
    int count[base];
    int temp[arraySize];

    for (int start = 1; start < pow(10, maxDigitLength); start *= base)
    {
        for (int i = 0; i < base; i++)
        {
            count[i] = 0;
        }

        for (int i = 0; i < arraySize; i++)
        {
            int digit = ascending ? (arr[i] / start) % base : base - 1 - (arr[i] / start) % base;
            count[digit]++;
            numberOfIterations++;
        }

        for (int i = 1; i < base; i++)
        {
            count[i] += count[i - 1];
            numberOfIterations++;
        }

        for (int i = arraySize - 1; i >= 0; i--)
        {
            int digit = ascending ? (arr[i] / start) % base : base - 1 - (arr[i] / start) % base;
            temp[--count[digit]] = arr[i];
            numberOfIterations++;
        }

        for (int i = 0; i < arraySize; i++)
        {
            arr[i] = temp[i];
            numberOfIterations++;
        }
    }
    return numberOfIterations;
}