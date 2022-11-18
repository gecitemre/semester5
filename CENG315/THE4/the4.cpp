#include "the4.h"
using namespace std;

int dp_sln(char**& arr1, char**& arr2, int nrow, int ncol1, int ncol2, int**& mem){ //dynamic programming
    auto column_cost = [](char**& arr, int j, int nrow) {
        int cost = 0;
        for (int i=0; i < nrow; i++) {
            cost += arr[i][j] != '-';
        }
        return cost;
    };
    auto columns_cost = [](int i, int j, int nrow, char**& arr1, char**& arr2) {
        int count1[128] = {}, count2[128] = {}, cost = 0;
        bool replacement_flag = false;
        for (int r=0; r < nrow; r++) {
            if (arr1[r][i] != arr2[r][j]) {
                count1[arr1[r][i]]++;
                count2[arr2[r][j]]++;
            }
        }
        for (int r=0; r < 128; r++) {
            if (count1[r] != count2[r]){
                replacement_flag = true;
            }
            cost += count1[r] + count2[r];
        }

        if (replacement_flag) {
            cost += 2 * (count1['-'] + count2['-']);
        }

        cost /= 2;
        return cost;
    };
    mem[0][0] = 0;
    for (int i=0; i < ncol1; i++) {
        mem[i+1][0] = mem[i][0];
        mem[i+1][0] += column_cost(arr1, i, nrow);
    }
    for (int i=0; i < ncol2; i++) {
        mem[0][i+1] = mem[0][i];
        mem[0][i+1] += column_cost(arr2, i, nrow);
    }

    for (int i=1; i < ncol1 + 1; i++) {
        for (int j=1; j < ncol2 + 1; j++) {
            int m = min(mem[i-1][j] + column_cost(arr1, i-1, nrow), mem[i][j-1] + column_cost(arr2, j-1, nrow));
            mem[i][j] = min(m, mem[i-1][j-1] + columns_cost(i-1, j-1, nrow, arr1, arr2));
        }
    }
    return mem[ncol1][ncol2];
}