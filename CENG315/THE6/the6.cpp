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

int ancestor(int p) {
    if (parent[p] == p) return p;
    return parent[p] = ancestor(parent[p]);
}

void merge(int p, int q) {
    parent[ancestor(p)] = ancestor(q);
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
    
    for (int i = 0; i < num_of_buildings; i++) parent[i] = i;

    quick_sort(pipelines, 0, pipelines.size() - 1);

    for (int i = 0; i < pipelines.size(); i++) {
        if (!connected(pipelines[i]->end1, pipelines[i]->end2)) {
            merge(pipelines[i]->end1, pipelines[i]->end2);
            solution.push_back(pipelines[i]->id);
        }
    }

    unordered_set<int> components;
    
    for (int i = 0; i < num_of_buildings; i++) {
        if (components.find(ancestor(i)) == components.end()) {
            components.insert(ancestor(i));
        }
    }

    vector<pair<int, int>> component_min(components.size(), {INT_MAX, -1});
    for (int i = 0; i < num_of_buildings; i++) {
        if (component_min[ancestor(i)].first > pipelines[i]->cost_of_consumption) {
            component_min[ancestor(i)].first = pipelines[i]->cost_of_consumption;
            component_min[ancestor(i)].second = i;
        }
    }

    for (int i = 0; i < component_min.size(); i++) {
        if (component_min[i].second != -1) {
            solution.push_back(pipelines[component_min[i].second]->id);
        }
    }
    
    return solution; // this is a dummy return value. YOU SHOULD CHANGE THIS!
    
}

vector<int> optimize_plan(vector<Pipeline*> current_solution, Pipeline* new_pipeline) {
 
    vector<int> solution;
    
    //your code here
    

    return solution; // this is a dummy return value. YOU SHOULD CHANGE THIS!
    
}

