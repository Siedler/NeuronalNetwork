package com.gianluca.activation;

public class Sigmoid implements ActivationFunction {

    @Override
    public double computeActivationFunction(double x) {
        return 1/(1+Math.pow(Math.E, -x));
    }

    @Override
    public double derivativeActivationFunction(double x) {
        return computeActivationFunction(x) * (1 - computeActivationFunction(x));
    }

}
