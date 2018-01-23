package com.gianluca.learning;

import com.gianluca.cost.CostFunction;
import com.gianluca.layers.HiddenLayer;
import com.gianluca.layers.InputLayer;
import com.gianluca.layers.OutputLayer;

public abstract class LearningAlgorithm {

    InputLayer inputLayer;
    HiddenLayer[] hiddenLayers;
    OutputLayer outputLayer;

    CostFunction costFunction;

    private double totalCost;

    protected double learningRate;

    public void initLearningAlgorithm(InputLayer inputLayer, HiddenLayer[] hiddenLayers, OutputLayer outputLayer, CostFunction costFunction, double learningRate) {
        this.inputLayer = inputLayer;
        this.hiddenLayers = hiddenLayers;
        this.outputLayer = outputLayer;

        this.costFunction = costFunction;

        this.learningRate = learningRate;
    }

    public abstract void learn(double[] results, double[] expectedResults);

    public abstract void updateWeightsAndBias();
}
