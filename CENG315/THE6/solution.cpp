#include "the6.h"

/* This is the solution shared by course staff.*/

/*
    For PART-I: 
    If you consider each furnace room as a separate graph node,
    then you get disjoint set of subgraphs and it would not be possible to
    solve it with a minimum spanning tree (MST) algorithm. 
    
    Instead, consider each furnace room as THE SAME NODE, in other words; 
    make a single graph node and name it as the common furnace room. Let's
    call this node as S. Now, for each pipeline connecting a building to
    its own furnace room (namely the ones whose end ids are equal), replace it
    with a pipeline connecting the building to S. For instance, if p is a
    pipeline connecting building-5 to its own furnace room, now it will be
    replaced with a pipeline p' connecting building-5 to S.
    
    After reaarenging your graph as above, just apply one of the MST algorithms;
    Kruskal's or Prim's. The below implementation applies Kruskal's algorithm.
    
    FOR PART-II:
    If you see the proof of Kruskal's algorithm for instance, you could
    understand the idea below better. Here is the idea: If you add a new pipeline
    to the existing MST, then it causes a LOOP. For instance, if the new pipeline
    is between building-5 and building-17, then adding it onto the MST causes to
    occur a loop between building-5 and building-17. Then, you should trace the 
    loop and find the maximum-costly pipeline, call Pmax. If its cost is lower 
    than the cost of the new pipeline, then there is nothing extra to do, you can 
    safely return the old MST as the updated one also. However, if the new pipeline
    is less costly, then you should remove the Pmax, this preserves the tree-ness
    property. And adding new pipeline onto the tree preserves the connectedness
    of the tree. Now, you can return this updated MST.
    
    Complexity of PART-II.
    Tracing operation could be handled by BFS or DFS or any other similar O(N)
    algorithm. Also, to be more efficient, you can hold the adjenceny matrix
    as a collection of lists where each list contains the neighbor ids of a node.
    The below algorithm uses this data structure, and could handle with a graph
    of the size 2^20 nodes on the VPL platform of OdtüClass.
    
*/


// The merge sort which you know
void merge_sort(vector<Pipeline*>& pipelines, int from, int to) {

    int size = to - from;
    int left_end = (to-from)/2 + from;
    
    if (size == 1)
        return;
    if (size == 2) {
        if (pipelines[from]->cost_of_consumption > pipelines[from + 1]->cost_of_consumption) {
            Pipeline* temp = pipelines[from];
            pipelines[from] = pipelines[from + 1];
            pipelines[from + 1] = temp; 
        }
        return;
    }
    
    merge_sort(pipelines, from, left_end);
    merge_sort(pipelines, left_end, to);
    // merge

    vector<Pipeline*> temp;
    for (int i = from, j = left_end; ; ) {
        if (temp.size() == size)
            break;
        if (i == left_end) {
            temp.push_back(pipelines[j]);
            j++;
        }
        else if (j == to) {
            temp.push_back(pipelines[i]);
            i++;
        }
        else if (pipelines[i]->cost_of_consumption > pipelines[j]->cost_of_consumption) {
            temp.push_back(pipelines[j]);
            j++;
        }
        else {
            temp.push_back(pipelines[i]);
            i++;
        }
    }

    for (int i = 0, p = from; i < temp.size(); i++, p++)
        pipelines[p] = temp[i];
 
}


// This tries to find the loop between the nodes <first> and <final> with DFS.
vector<int> findLoopByDFS(int first, int final, vector<vector<int>>& efficient_adj_matrix) {
    
    // if there occurs some way connecting first to final, then it means there occurs a lop
        // in that case, do not add the edge between first and final.
    
    vector<int> parents(efficient_adj_matrix.size());
    vector<bool> visited(efficient_adj_matrix.size());
    for (int i = 0; i < efficient_adj_matrix.size(); i++) {
        visited[i] = false;
        parents[i] = -1;
    }
    
    stack<int> stack;          // holds the nodes to be visited
    stack.push(first);
    
    while(!stack.empty()) {
        
        int next = stack.top();
        stack.pop();
        
        if (visited[next])
            continue;
        visited[next] = true;
        vector<int> neighbors = efficient_adj_matrix[next];
        
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors[i] == final) { // we found the loop
                
                // just to satisfy linearity, find the size of the loop
                int path_size = 2;
                int node_ind = next;
                while (node_ind != first) {
                    node_ind = parents[node_ind];
                    path_size++;
                }
                
                // now allocate a vector of that much size 
                vector<int> path(path_size);
                path[0] = final;
                path[1] = next;
                int path_ind = 2;
                node_ind = next;
                while (node_ind != first) {
                    node_ind = parents[node_ind];
                    path[path_ind++] = node_ind;
                }
                return path;
            }
            
            if (visited[neighbors[i]])
                continue;
                
            parents[neighbors[i]] = next;
            stack.push(neighbors[i]);
        }
        
    }
    
    // no loop
    vector<int> empty_result;
    return empty_result;
    
}


vector<int> plan_min_cost_pipeline_usage(vector<Pipeline*> pipelines, int num_of_buildings) {

    // Construct adjecency matrix
    vector<vector<int>> efficient_adj_matrix;
    for (int i = 0; i <= num_of_buildings; i++) {
        vector<int> neighbors;
        efficient_adj_matrix.push_back(neighbors);
    }

    // Kruskal's Algorithm
    merge_sort(pipelines, 0, pipelines.size());

    vector<Pipeline*> current_solution;
    vector<int> min_spanning_tree;
    
    for (int i = 0; i < pipelines.size(); i++) {
        
        Pipeline* p = pipelines[i];
        
        if (p->end1 == p->end2)
            p->end2 = num_of_buildings;     // temporarily make node id num_of_buildings
        
        // check for loop
        int next = p->end1;
        int current_edge_index = -1;        // we have not added this edge into the tree yet
        if (findLoopByDFS(p->end1, p->end2, efficient_adj_matrix).size() == 0) { // no loop
            efficient_adj_matrix[p->end1].push_back(p->end2);
            efficient_adj_matrix[p->end2].push_back(p->end1);
            min_spanning_tree.push_back(p->id);
        }
        
        if (min_spanning_tree.size() == num_of_buildings)
            return min_spanning_tree;
    }
    
    return min_spanning_tree;               // not used, put to prevent warning
}


vector<int> optimize_plan(vector<Pipeline*> current_solution, Pipeline* new_pipeline) {

    int num_of_buildings = current_solution.size();    
    if (new_pipeline->end1 == new_pipeline->end2)
        new_pipeline->end2 = num_of_buildings;   // temporarily make node id num_of_buildings
    
    // Construct adjecency matrix
    vector<vector<int>> efficient_adj_matrix(num_of_buildings+1);
    vector<vector<int>> costs(num_of_buildings+1);  // holds corresponding costs
    
    for (int i = 0; i <= num_of_buildings; i++) {
        vector<int> neighbors;
        efficient_adj_matrix[i] = neighbors;
        
        vector<int> edge_costs;
        costs[i] = edge_costs;    
    }
    
    // initialize adj and costs matrix
    for (int i = 0; i < current_solution.size(); i++) {
        if (current_solution[i]->end1 == current_solution[i]->end2)
            current_solution[i]->end2 = num_of_buildings;
        
        efficient_adj_matrix[current_solution[i]->end1].push_back(current_solution[i]->end2);
        costs[current_solution[i]->end1].push_back(current_solution[i]->cost_of_consumption);
        
        efficient_adj_matrix[current_solution[i]->end2].push_back(current_solution[i]->end1);
        costs[current_solution[i]->end2].push_back(current_solution[i]->cost_of_consumption);
    }
    
    // initialize the new solution to the old version for now
    vector<int> new_solution(num_of_buildings);
    for (int i = 0;  i < current_solution.size(); i++)
        new_solution[i] = current_solution[i]->id;
        
    // find the loop
    vector<int> loop = findLoopByDFS(new_pipeline->end1, new_pipeline->end2, efficient_adj_matrix);
    int max_weight_owner = -1;
    int max_weight_of_loop = -1;
    for (int i = 1; i < loop.size(); i++) { // wee will remove the max-weight-edge from the ring(loop)
        
        int node1_ind = loop[i-1];
        int node2_ind = loop[i];
        int current_cost;

        for (int j = 0; j < efficient_adj_matrix[node1_ind].size(); j++)
            if (efficient_adj_matrix[node1_ind][j] == node2_ind) {
                current_cost = costs[node1_ind][j];
                break;
            }
            
    
        // detect the maximum-costly edge in the graph
        if (max_weight_of_loop < current_cost) {
            max_weight_of_loop = current_cost;
            max_weight_owner = i;
        }
        
    }
    
    // if the new pipeline has lower weight, then replace with the max-weight-pipeline
    if (new_pipeline->cost_of_consumption < max_weight_of_loop) {
        int node1_ind = loop[max_weight_owner-1];
        int node2_ind = loop[max_weight_owner];
        
        //find the edge between node1_ind and node2_ind
        for (int i = 0; i < current_solution.size(); i++) {
            if (current_solution[i]->end1 == node1_ind && current_solution[i]->end2 == node2_ind ||
                current_solution[i]->end1 == node2_ind && current_solution[i]->end2 == node1_ind) { 
                new_solution[i] = new_pipeline->id;
                break;    
            }
        }
    }

    return new_solution;
    
}