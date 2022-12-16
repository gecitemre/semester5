#include "the6.h"
using namespace std;
/* 
    in the6.h "struct Pipeline" is defined as below:
    
    struct Pipeline {
        int id;                     // pipeline id
        int end1;                   // id of the building-1
        int end2;                   // id of the building-2
        int cost_of_consumption;    // amount/cost of consumption for pipeline
    };

*/

int parent[0b1 << 18];
int furnace_cost[0b1 << 18];

int ancestor(int p) {
    if (parent[p] == p) return p;
    return parent[p] = ancestor(parent[p]);
}


int furnace_cost_f(int p) {
    if (ancestor(p) == p) return furnace_cost[p];
    return furnace_cost_f(parent[p]);
}

void merge(int p, int q) {
    if (furnace_cost_f(p) < furnace_cost_f(q)) {
        parent[ancestor(q)] = ancestor(p);
    }
    else {
        parent[ancestor(p)] = ancestor(q);
    }
}

bool connected(int p, int q) {
    return ancestor(p) == ancestor(q);
}


void quick_sort(vector<Pipeline*> &pipelines, int left, int right) {
    int i = left, j = right;
    Pipeline* tmp;
    Pipeline* pivot = pipelines[(left + right) / 2];
    
    /* partition */
    while (i <= j) {
        while (pipelines[i]->cost_of_consumption < pivot->cost_of_consumption)
            i++;
        while (pipelines[j]->cost_of_consumption > pivot->cost_of_consumption)
            j--;
        if (i <= j) {
            tmp = pipelines[i];
            pipelines[i] = pipelines[j];
            pipelines[j] = tmp;
            i++;
            j--;
        }
    };
    
    /* recursion */
    if (left < j)
        quick_sort(pipelines, left, j);
    if (i < right)
        quick_sort(pipelines, i, right);
}

vector<int> plan_min_cost_pipeline_usage(vector<Pipeline*> pipelines, int num_of_buildings) {
    
    vector<int> solution;
    for (int i = 0; i < num_of_buildings; i++){
        parent[i] = i;
        furnace_cost[i] = INT_MAX;
    }

    quick_sort(pipelines, 0, pipelines.size() - 1);
    cout << "sorted: ";
    for (int i = 0; i < pipelines.size(); i++) {
        if (pipelines[i]->end1 == pipelines[i]->end2) {
            if (pipelines[i]->cost_of_consumption < furnace_cost_f(pipelines[i]->end1)) {
                furnace_cost[parent[pipelines[i]->end1]] = pipelines[i]->cost_of_consumption;
                solution.push_back(pipelines[i]->id);
                cout << "connected: " << pipelines[i]->id << " " << pipelines[i]->end1 << " " << pipelines[i]->end2 << endl;
            }
        }
        else if (!connected(pipelines[i]->end1, pipelines[i]->end2)) {
            if (furnace_cost_f(pipelines[i]->end1) == INT_MAX || furnace_cost_f(pipelines[i]->end2) == INT_MAX) {
                merge(pipelines[i]->end1, pipelines[i]->end2);
                solution.push_back(pipelines[i]->id);
                cout << "connected: " << pipelines[i]->id << " " << pipelines[i]->end1 << " " << pipelines[i]->end2 << endl;
            }
        }
        else {
            cout << "skipped: " << pipelines[i]->id << " " << pipelines[i]->end1 << " " << pipelines[i]->end2 << endl;
        }
    }
    cout << endl;

    unordered_set<int> components;

    cout << "ancestors: ";
    for (int i = 0; i < num_of_buildings; i++) {
        cout << ancestor(i) << " ";
    }
    cout << endl;
    for (int i = 0; i < num_of_buildings; i++) {
        if (components.find(ancestor(i)) == components.end()) {
            components.insert(ancestor(i));
        }
    }

    cout << "components: ";
    for (int i: components) {
        cout << i << " ";
    }
    cout << endl;
    vector<int> component_min(num_of_buildings, INT_MAX);
    for (int i = 0; i < num_of_buildings; i++) {
        if (furnace_cost[i] < component_min[ancestor(i)]) {
            component_min[ancestor(i)] = furnace_cost[i];
        }
    }

    return solution; // this is a dummy return value. YOU SHOULD CHANGE THIS!
    
}

vector<int> optimize_plan(vector<Pipeline*> current_solution, Pipeline* new_pipeline) {
 
    vector<int> solution;
    
    //your code here
    

    return solution; // this is a dummy return value. YOU SHOULD CHANGE THIS!
    
}

