#ifndef THE6_H
#define THE6_H

#include <vector>
#include <iostream>
#include <climits>
#include <vector>
#include <string>
#include <stack>
#include <queue>
#include <unordered_set>

using namespace std;

struct Pipeline
{
    int id;
    int end1;
    int end2;
    int cost_of_consumption;
};

vector<int> plan_min_cost_pipeline_usage(vector<Pipeline*> pipelines, int num_of_buildings);
vector<int> optimize_plan(vector<Pipeline*> current_solution, Pipeline* new_pipeline);

#endif