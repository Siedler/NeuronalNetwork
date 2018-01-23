package com.gianluca.cost;

public interface CostFunction {

    double cost(double result, double expected);

    double derivativeCost(double result, double expected);

    double totalCost(double[] results, double[] expected);
}
