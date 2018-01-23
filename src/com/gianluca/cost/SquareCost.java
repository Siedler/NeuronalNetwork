package com.gianluca.cost;

public class SquareCost implements CostFunction {

    @Override
    public double cost(double result, double expected) {
        return Math.pow(result-expected, 2);
    }

    @Override
    public double derivativeCost(double result, double expected) {
        return 2 * (result - expected);
    }

    @Override
    public double totalCost(double[] results, double[] expected) {
        double cost = 0;

        for(int i = 0; i < results.length; i++) {
            cost += cost(results[i], expected[i]);
        }

        return cost;
    }
}
