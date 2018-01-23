package com.gianluca.activation;

public class Linear implements ActivationFunction {

    @Override
    public double computeActivationFunction(double x) {
        return x;
    }

    @Override
    public double derivativeActivationFunction(double x) {
        return 1.0;
    }

}
