#include "the3.h"
using namespace std;

#define start(x) (arr[x][0])
#define end(x) (arr[x][1])
#define encode(x) ((x) == 'I' ? -1 : (x) == 'O' ? 1 \
                                                : 0)
#define MATCHES(x, y) (encode(x) + encode(y) == 0)
#define index(x) ((x) == 'I' ? 0 : (x) == 'O' ? 1 \
                                              : 2)
#define inverse_value(x) ((x) == 2 ? 2 : 1 - (x))

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
    number_of_calls += 1;
    if (N == 0)
        return len[0];
    int maximum;

    if (number_of_calls == 1)
    {
        maximum = recursive_sln(N - 1, arr, len, number_of_calls);
        for (int i = N - 1; i >= 0; i--)
        {
            if (MATCHES(start(N), end(i)))
            {
                maximum = max(maximum, recursive_sln(i, arr, len, number_of_calls) + len[N]);
                break;
            }
        }
    }
    else
    {
        maximum = len[N];
        for (int i = N - 1; i >= 0; i--)
        {
            if (MATCHES(start(N), end(i)))
            {
                maximum = max(maximum, recursive_sln(i, arr, len, number_of_calls) + len[N]);
                break;
            }
        }
        for (int i = N - 1; i >= 0; i--)
        {
            if (end(i) == end(N))
            {
                maximum = max(maximum, recursive_sln(i, arr, len, number_of_calls));
                break;
            }
        }
    }
    return maximum;
}

int memoization_sln(int N, char **&arr, int *&len, int **&mem)
{ // memoization
    int start_value = index(start(N)), end_value = index(end(N));
    if (N == 0)
    {
        for (int i = 0; i < 3; i++)
        {
            mem[N][i] = 0;
        }
        mem[N][end_value] = len[N];
        return mem[N][start_value];
    }

    if (*mem[N - 1] == -1)
        memoization_sln(N - 1, arr, len, mem);

    mem[N][0] = mem[N][1] = mem[N][2] = 0;
    mem[N][end_value] = len[N];

    for (int i = 0; i < 3; i++)
    {
        mem[N][i] = max(mem[N][i], mem[N - 1][i]);
    }
    mem[N][end_value] = max(mem[N][end_value], mem[N - 1][inverse_value(start_value)] + len[N]);

    int maximum = mem[N][0];

    for (int i = 1; i < 3; i++)
    {
        maximum = max(maximum, mem[N][i]);
    }
    return maximum;
}

int dp_sln(int size, char **&arr, int *&len, int **&mem)
{ // dynamic programming
    for (int i = 0; i < 3; i++)
    {
        mem[0][i] = 0;
    }
    mem[0][index(end(0))] = len[0];

    for (int i = 1; i < size; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            mem[i][j] = max(mem[i - 1][j], mem[i][j]);
        }
        mem[i][index(end(i))] = max(mem[i][index(end(i))], mem[i - 1][inverse_value(index(start(i)))] + len[i]);
    }

    int maximum = mem[size - 1][0];
    for (int i = 1; i < 3; i++)
    {
        maximum = max(maximum, mem[size - 1][i]);
    }
    return maximum;
}
