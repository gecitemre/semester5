#include "the3.h"

#define start(x) (arr[x][0])
#define end(x) (arr[x][1])
#define MATCHES(x, y) ((x) == 'I' && (y) == 'O' || (x) == 'O' && (y) == 'I' || (x) == 'S' && (y) == 'S')

using namespace std;

int recursive_sln(int N, char **&arr, int *&len, int &number_of_calls)
{ // direct recursive

    /*
    IF  N == size-1
        M(N) = max{ M(N-1), M(i)+len(N) IF start(N) MATCHES end(i) }
    ELSE
        M(N) = max{ M(j) IF end(N) equals to end(j), M(i)+len(N) IF start(N) MATCHES end(i) }
    where
        i <= N-1  &&  i > t FOR ALL t start(N) matches end(t)
        j <= N-1  &&  j > t FOR ALL end(N) matches end(t)
    start( x ) MATCHES end( y ) IFF {{start( x ) =='I' && end( y ) =='O} OR {start( x )=='O' && end( y )=='I'} OR {start( x )=='S' && end( y ) =='S'}}
   */
    if (N == 0)
        return len[0];
    bool firstCall = number_of_calls == 0;
    number_of_calls += 1;
    int maximum = len[N];

    if (firstCall)
    {
        for (int i = 0; i < N; i++) {
            if (MATCHES(start(N), end(i))) {
                maximum = max(maximum, recursive_sln(i, arr, len, number_of_calls) + len[N]);
            }
            else {
                maximum = max(maximum, recursive_sln(i, arr, len, number_of_calls));
            }
        }
    }
    else
    {
        for (int i = 0; i < N; i++) {
            if (MATCHES(start(N), end(i))) {
                maximum = max(maximum, recursive_sln(i, arr, len, number_of_calls) + len[N]);
            }
        }
    }
    return maximum;
}

int memoization_sln(int N, char **&arr, int *&len, int **&mem)
{ // memoization
    if (N == 0) return len[N];
    int index;
    switch (start(N))
    {
    case 'I':
        index = 0;
        break;
    case 'O':
        index = 1;
        break;
    case 'S':
        index = 2;
        break;
    }
    int maximum = len[N];
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < 3; j++) {
            if (mem[i][j] == -1) mem[i][j] = memoization_sln(i, arr, len, mem);
            if (index == j)
                maximum = max(maximum, mem[i][j] + len[N]);
            else
                maximum = max(maximum, mem[i][j]);
        }
    }
    return maximum;
}

int dp_sln(int size, char **&arr, int *&len, int **&mem)
{ // dynamic programming

    // your code here

    return 0; // this is a dummy return value. YOU SHOULD CHANGE THIS!
}
