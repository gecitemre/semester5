#include "the5.h"

/* 
    in the5.h "struct Room" is defined as below:
    
    struct Room {
        int id;
        char content;
        vector<Room*> neighbors;
    };

*/
bool maze_trace_helper(Room* room, vector<int> &path, vector<bool> &visited) { 
    visited[room->id] = true;
    path.push_back(room->id);
    if (room->content == '*') {
        return true;
    }
    for (Room* neighbor: room->neighbors) {
        bool is_treasure_found = false;
        if (!visited[neighbor->id]) {
            is_treasure_found = maze_trace_helper(neighbor, path, visited);
            path.push_back(room->id);
            if (is_treasure_found) {
                return true;
            }
        }
    }

    return false;
}

vector<int> maze_trace(vector<Room*> maze) { 

    vector<int> path;
    vector<bool> visited(maze.size(), false);
    maze_trace_helper(maze[0], path, visited);
    cout << "test";

    return path;
}


