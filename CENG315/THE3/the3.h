#include <cmath>
#include <climits>
#include <cstdlib>
#include <iostream>

int recursive_sln(int N, char **&arr, int *&len, int &number_of_calls);
int memoization_sln(int N, char **&arr, int *&len, int **&mem);
int dp_sln(int size, char **&arr, int *&len, int **&mem);