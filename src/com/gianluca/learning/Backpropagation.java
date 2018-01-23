package com.gianluca.learning;

import com.gianluca.activation.ActivationFunction;
import com.gianluca.cost.CostFunction;
import com.gianluca.layers.HiddenLayer;
import com.gianluca.layers.InputLayer;
import com.gianluca.layers.Layer;
import com.gianluca.layers.OutputLayer;
import com.gianluca.neurons.Neuron;

public class Backpropagation extends LearningAlgorithm{

    private int totalWeights = 0;
    private int totalBiases = 0;

    @Override
    public void initLearningAlgorithm(InputLayer inputLayer, HiddenLayer[] hiddenLayers, OutputLayer outputLayer, CostFunction costFunction, double learningRate) {
        super.initLearningAlgorithm(inputLayer, hiddenLayers, outputLayer, costFunction, learningRate);

        countAllWeightsAndBiases();
    }

    @Override
    public void learn(double[] results, double[] expectedResults) {
        double[] weightChanges = new double[totalWeights];
        double[] biasChanges = new double[totalBiases];

        int posInWeights = 0;
        int posInBias = 0;

        for(int i = 0; i < outputLayer.getNumberOfNeuronsInLayer(); i++) {
            Neuron n = outputLayer.getNeurons()[i];
            ActivationFunction ac = n.getActivationFunction();

            double sensitivity = costFunction.derivativeCost(results[i], expectedResults[i]);
            n.setSensitivity(sensitivity);

            double calculatedBias = ac.derivativeActivationFunction(n.getRawActivation()) * sensitivity;
            biasChanges[posInBias] = calculatedBias;
            posInBias++;

            for(int y = 0; y < n.getInputWeights().length; y++) {
                Neuron hn = hiddenLayers[hiddenLayers.length-1].getNeurons()[i];

                double calculatedWeight = hn.getActivation() * calculatedBias;
                weightChanges[i] = calculatedWeight;
                posInWeights++;
            }
        }

        for(int i = hiddenLayers.length-1; i >= 0; i--) {
            HiddenLayer h = hiddenLayers[i];

            for(int y = 0; y < h.getNumberOfNeuronsInLayer(); y++) {
                Neuron n = h.getNeurons()[y];
                ActivationFunction ac = n.getActivationFunction();

                Layer previousLayer;
                if(i == hiddenLayers.length-1) {
                    previousLayer = outputLayer;
                } else {
                    previousLayer = hiddenLayers[i+1];
                }

                double sensitivity = 0;
                for(int z = 0; z < previousLayer.getNumberOfNeuronsInLayer(); z++) {
                    Neuron pn = previousLayer.getNeurons()[z];
                    ActivationFunction pac = pn.getActivationFunction();

                    sensitivity += pn.getInputWeights()[y] * pac.derivativeActivationFunction(pn.getRawActivation()) * pn.getSensitivity();
                }
                n.setSensitivity(sensitivity);

                double calculatedBias = ac.derivativeActivationFunction(n.getRawActivation()) * sensitivity;
                biasChanges[posInBias] = calculatedBias;
                posInBias++;

                Layer nextLayer;
                if(i == 0) nextLayer = inputLayer;
                else nextLayer = hiddenLayers[i-1];

                for(int z = 0; z < n.getInputWeights().length; z++) {
                    Neuron nn = nextLayer.getNeurons()[z];

                    double calculatedWeight = nn.getActivation() * calculatedBias;
                    weightChanges[posInWeights] = calculatedWeight;
                    posInWeights++;
                }
            }
        }

        posInWeights = 0;
        posInBias = 0;

        for(int i = 0; i < outputLayer.getNumberOfNeuronsInLayer(); i++) {
            Neuron n = outputLayer.getNeurons()[i];

            double newBias = n.getBias() - (learningRate * biasChanges[posInBias]);
            posInBias++;

            double[] newWeights = new double[n.getInputWeights().length];
            for(int y = 0; y < n.getInputWeights().length; y++) {
                double oldWeight = n.getInputWeights()[y];

                double newWeight = oldWeight - (learningRate * weightChanges[posInWeights]);
                posInWeights++;

                newWeights[y] = newWeight;
            }

            n.setInputWeights(newWeights);
            n.setBias(newBias);
        }

        for(int i = hiddenLayers.length-1; i >= 0; i--) {
            HiddenLayer h = hiddenLayers[i];

            for(int y = 0; y < h.getNumberOfNeuronsInLayer(); y++) {
                Neuron n = h.getNeurons()[y];

                double newBias = n.getBias() - (learningRate * biasChanges[posInBias]);
                posInBias++;

                double[] newWeights = new double[n.getInputWeights().length];
                for(int z = 0; z < n.getInputWeights().length; z++) {
                    double oldWeight = n.getInputWeights()[z];

                    double newWeight = oldWeight - (learningRate * weightChanges[posInWeights]);
                    posInWeights++;

                    newWeights[z] = newWeight;
                }

                n.setInputWeights(newWeights);
                n.setBias(newBias);
            }
        }
    }

    // This function computes the number of total weights and biases
    private void countAllWeightsAndBiases() {
        if(hiddenLayers.length == 1) {
            totalWeights += inputLayer.getNumberOfNeuronsInLayer() * hiddenLayers[0].getNumberOfNeuronsInLayer();
            totalWeights += outputLayer.getNumberOfNeuronsInLayer() * hiddenLayers[0].getNumberOfNeuronsInLayer();
        } else {
            totalWeights += inputLayer.getNumberOfNeuronsInLayer() * hiddenLayers[0].getNumberOfNeuronsInLayer();
            totalWeights += outputLayer.getNumberOfNeuronsInLayer() * hiddenLayers[hiddenLayers.length-1].getNumberOfNeuronsInLayer();

            for(int i = 0; i < hiddenLayers.length-1; i++) {
                totalWeights += hiddenLayers[i].getNumberOfNeuronsInLayer() * hiddenLayers[i+1].getNumberOfNeuronsInLayer();
            }
        }

        totalBiases += outputLayer.getNumberOfNeuronsInLayer();
        for(int i = 0; i < hiddenLayers.length; i++) {
            totalBiases += hiddenLayers[i].getNumberOfNeuronsInLayer();
        }
    }
}
