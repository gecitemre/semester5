// define the5.h

#include <vector>
#include <iostream>
using namespace std;

struct Room {
    int id;
    char content;
    vector<Room*> neighbors;
};

vector<int> maze_trace(vector<Room*> maze);