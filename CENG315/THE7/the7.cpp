#include "the7.h"
using namespace std;

void PrintPath(int s, int d, vector<vector<int>> &next)
{
    while (s != d)
    {
        cout << s << "->";
        s = next[s][d];
    }
}

void CanCatch(int n, vector<Road> roads, int s, int d, int x, int y, int l, int printPath)
{

    // floyd warshall
    vector<vector<int>> dist(n, vector<int>(n, INT_MAX));
    vector<vector<int>> next(n, vector<int>(n, -1));
    for (int i = 0; i < n; i++)
    {
        dist[i][i] = 0;
    }
    for (Road r : roads)
    {
        dist[r.buildings.first][r.buildings.second] = r.time;
        dist[r.buildings.second][r.buildings.first] = r.time;
        next[r.buildings.first][r.buildings.second] = r.buildings.second;
        next[r.buildings.second][r.buildings.first] = r.buildings.first;
    }
    for (int k = 0; k < n; k++)
    {
        for (int i = 0; i < n; i++)
        {
            for (int j = i + 1; j < n; j++)
            {
                if (dist[i][k] != INT_MAX && dist[k][j] != INT_MAX && dist[i][k] + dist[k][j] < dist[i][j])
                {
                    dist[j][i] = dist[i][j] = dist[i][k] + dist[k][j];
                    next[i][j] = next[i][k];
                    next[j][i] = next[j][k];
                }
            }
        }
    }

    int cost_sx = dist[s][x];
    int cost_sy = dist[s][y];
    int cost_xy = dist[x][y];
    int cost_xd = dist[x][d];
    int cost_yd = dist[y][d];

    int cost_sxyd = cost_sx + cost_xy + cost_yd;
    int cost_syxd = cost_sy + cost_xy + cost_xd;

    if (cost_sxyd <= cost_syxd && cost_sxyd <= l)
    {
        cout << "YES BOTH " << cost_sxyd << "MINUTES" << endl;
        if (printPath)
        {
            PrintPath(s, x, next);
            PrintPath(x, y, next);
            PrintPath(y, d, next);
            cout << d << endl;
        }
        return;
    }
    else if (cost_syxd <= l)
    {
        cout << "YES BOTH " << cost_syxd << "MINUTES" << endl;
        if (printPath)
        {
            PrintPath(s, y, next);
            PrintPath(y, x, next);
            PrintPath(x, d, next);
            cout << d << endl;
        }
        return;
    }

    int cost_sxd = cost_sx + cost_xd;
    int cost_syd = cost_sy + cost_yd;
    if (cost_sxd <= cost_syd && cost_sxd <= l)
    {
        cout << "YES PRINTER " << cost_sxd << "MINUTES" << endl;
        if (printPath)
        {
            PrintPath(s, x, next);
            PrintPath(x, d, next);
            cout << d << endl;
        }
        return;
    }
    else if (cost_syd <= l)
    {
        cout << "YES DORM " << cost_syd << "MINUTES" << endl;
        if (printPath)
        {
            PrintPath(s, y, next);
            PrintPath(y, d, next);
            cout << d << endl;
        }
        return;
    }

    int cost_sd = dist[s][d];
    if (cost_sd <= l)
    {
        cout << "YES DIRECTLY " << cost_sd << "MINUTES" << endl;
        if (printPath)
        {
            PrintPath(s, d, next);
            cout << d << endl;
        }
        return;
    }

    cout << "IMPOSSIBLE" << endl;
}