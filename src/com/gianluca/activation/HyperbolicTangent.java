package com.gianluca.activation;

public class HyperbolicTangent implements ActivationFunction {

    @Override
    public double computeActivationFunction(double x) {
        return (1-Math.pow(Math.E, -x))/(1 + Math.pow(Math.E, -x));
    }

    @Override
    public double derivativeActivationFunction(double x) {
        return (1.0 / Math.pow(Math.cosh(x), 2.0));
    }

}
