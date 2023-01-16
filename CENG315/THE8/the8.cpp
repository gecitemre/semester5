#include "the8.h"

using namespace std;
// DO NOT ADD OTHER LIBRARIES

char map(char c, const pair<string, string>& mapping)
{
    if (c == mapping.first[0])
    {
        c = mapping.second[0];
    }
    return c;
}

int mapAscii(char c, const pair<string, string>& mapping)
{
    if (c + 'A' == mapping.first[0])
    {
        c = mapping.second[0] - 'A';
    }
    return c;
}

void matchPattern(const std::string &sequence, const std::pair<std::string, std::string> mapping, const std::string &pattern)
{

    /*****************
     *
     *
     * YOU SHOULD PRINT THE STARTING INDEX OF EACH MATCH
     * IN THE SAME LINE, SEPARATED BY A SINGLE SPACE
     *
     * IF THERE IS NO MATCH, YOU SHOULD PRINT "NONE"
     *
     * *************/
    int m = pattern.length();
    string mappedPattern = pattern;
    for (int i = 0; i < m; i++)
    {
        mappedPattern[i] = map(pattern[i], mapping);
    }
    int transitionFunction[m + 1][26];
    for (int q = 0; q < m + 1; q++)
    {
        cout << q << endl;
        for (int a = 0; a < 26; a++)
        {
            // while last k elements of pattern are not equal to k elements of pattern
            int k;
            for (k = min(m, q + 1); k > 0; k-- )
            {
                int i;
                for (i = 0; i < k - 1; i++) {
                    if (mappedPattern[i] != mappedPattern[q - k + 1 + i]) {
                        break;
                    }
                }
                if (i == k - 1 && mappedPattern[k - 1] == map(a + 'A', mapping)) {
                    break;
                }
            }
            transitionFunction[q][a] = k;
            transitionFunction[q][mapAscii(a, mapping)] = k;
        }
    }

    int q = 0;
    bool match = false;
    for (int i = 0; i < (int)sequence.length(); i++)
    {

        q = transitionFunction[q][sequence[i] - 'A'];
        if (q == m)
        {
            if (match)
            {
                cout << " ";
            }
            cout << i - m + 1;
            match = true;
        }
    }
    if (!match)
    {
        cout << "NONE";
    }
}
