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

int ancestor(int p)
{
    if (parent[p] == p)
        return p;
    return parent[p] = ancestor(parent[p]);
}

int furnace_cost_f(int p)
{
    if (ancestor(p) == p)
        return furnace_cost[p];
    return furnace_cost_f(parent[p]);
}

void merge(int p, int q)
{
    if (furnace_cost_f(p) < furnace_cost_f(q))
    {
        parent[ancestor(q)] = ancestor(p);
    }
    else
    {
        parent[ancestor(p)] = ancestor(q);
    }
}

bool connected(int p, int q)
{
    return ancestor(p) == ancestor(q);
}

void quick_sort(vector<Pipeline *> &pipelines, int left, int right)
{
    int i = left, j = right;
    Pipeline *tmp;
    Pipeline *pivot = pipelines[(left + right) / 2];

    /* partition */
    while (i <= j)
    {
        while (pipelines[i]->cost_of_consumption < pivot->cost_of_consumption)
            i++;
        while (pipelines[j]->cost_of_consumption > pivot->cost_of_consumption)
            j--;
        if (i <= j)
        {
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

vector<int> plan_min_cost_pipeline_usage(vector<Pipeline *> pipelines, int num_of_buildings)
{
    vector<int> solution;
    for (int i = 0; i < num_of_buildings; i++)
    {
        parent[i] = i;
        furnace_cost[i] = INT_MAX;
    }

    quick_sort(pipelines, 0, pipelines.size() - 1);
    for (int i = 0; i < pipelines.size(); i++)
    {
        if (pipelines[i]->end1 == pipelines[i]->end2)
        {
            if (pipelines[i]->cost_of_consumption < furnace_cost_f(pipelines[i]->end1))
            {
                furnace_cost[parent[pipelines[i]->end1]] = pipelines[i]->cost_of_consumption;
                solution.push_back(pipelines[i]->id);
            }
        }
        else if (!connected(pipelines[i]->end1, pipelines[i]->end2))
        {
            if (furnace_cost_f(pipelines[i]->end1) == INT_MAX || furnace_cost_f(pipelines[i]->end2) == INT_MAX)
            {
                merge(pipelines[i]->end1, pipelines[i]->end2);
                solution.push_back(pipelines[i]->id);
            }
        }
    }

    unordered_set<int> components;

    for (int i = 0; i < num_of_buildings; i++)
    {
        if (components.find(ancestor(i)) == components.end())
        {
            components.insert(ancestor(i));
        }
    }

    vector<int> component_min(num_of_buildings, INT_MAX);
    for (int i = 0; i < num_of_buildings; i++)
    {
        if (furnace_cost[i] < component_min[ancestor(i)])
        {
            component_min[ancestor(i)] = furnace_cost[i];
        }
    }

    return solution; // this is a dummy return value. YOU SHOULD CHANGE THIS!
}

Pipeline* dfs(vector<vector<Pipeline *>> &graph, bool *visited, int current, int aim)
{
    visited[current] = true;
    for (Pipeline *p : graph[current])
    {
        if (p->end1 == p->end2)
            continue;
        int neighbor = p->end1 + p->end2 - current;
        if (neighbor == aim)
        {
            return p;
        }
        if (!visited[neighbor])
        {
            Pipeline* result = dfs(graph, visited, neighbor, aim);
            if (result == NULL)
            {
                return p;
            }
            else {
                if (result->cost_of_consumption > p->cost_of_consumption)
                    return result;
                else
                    return p;
            }
        }
    }
    return NULL;
}

vector<int> optimize_plan(vector<Pipeline *> current_solution, Pipeline *new_pipeline)
{
    bool visited[current_solution.size()];
    vector<vector<Pipeline *>> graph(current_solution.size());
    for (int i = 0; i < current_solution.size(); i++)
    {
        visited[i] = false;
        graph[current_solution[i]->end1].push_back(current_solution[i]);
        graph[current_solution[i]->end2].push_back(current_solution[i]);
    }

    Pipeline* max_edge = dfs(graph, visited, new_pipeline->end1, new_pipeline->end2);
    if (max_edge == NULL)
    {
        int maximum = furnace_cost_f(new_pipeline->end1) > furnace_cost_f(new_pipeline->end2) ? ancestor(new_pipeline->end1) : ancestor(new_pipeline->end2);

        if (new_pipeline->cost_of_consumption < furnace_cost_f(maximum))
        {
            int max_cost = 0, max_index = -1;
            for (int i = 0; i < current_solution.size(); i++)
            {
                if (current_solution[i]->end1 == maximum && current_solution[i]->end2 == maximum)
                {
                    current_solution[i] = new_pipeline;
                    break;
                }
            }
        }
    }
    else
    {
        int max_index = -1;
        for (int i = 0; i < current_solution.size(); i++)
        {
            if (current_solution[i] == max_edge)
            {
                max_index = i;
                break;
            }
        }
        if (max_edge->cost_of_consumption > new_pipeline->cost_of_consumption)
        {
            current_solution[max_index] = new_pipeline;
        }
    }

    vector<int> solution;
    for (Pipeline *p : current_solution)
    {
        solution.push_back(p->id);
    }
    return solution; // this is a dummy return value. YOU SHOULD CHANGE THIS!
}
