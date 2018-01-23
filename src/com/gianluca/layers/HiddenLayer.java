package com.gianluca.layers;

import com.gianluca.activation.ActivationFunction;
import com.gianluca.neurons.Neuron;

public class HiddenLayer extends Layer {

    private Layer previousLayer; // Reference to the layer that comes before the current Layer

    public void initHiddenLayer(Neuron[] neurons, Layer previousLayer) {
        this.previousLayer = previousLayer;

        this.neurons = neurons;
    }

    public void initHiddenLayer(int numberOfNeurons, double[][] weights, double[] biases, ActivationFunction[] activationFunctions, Layer previousLayer) {
        this.previousLayer = previousLayer;

        neurons = new Neuron[numberOfNeurons];

        if(numberOfNeurons == weights.length && numberOfNeurons == biases.length && numberOfNeurons == activationFunctions.length) {
            // Create the neurons
            for(int i = 0; i < neurons.length; i++) {
                Neuron n = new Neuron(weights[i], biases[i], activationFunctions[i]);
                neurons[i] = n;
            }

        } else {
            // Throws exception if one of the inputs is not correct
            String errorMessage;

            if(numberOfNeurons != weights.length) {
                errorMessage = "The number of weights doesn't match with the number if neurons";
            } else if(numberOfNeurons != biases.length) {
                errorMessage = "The number of biases doesn't match with the number of neurons";
            } else {
                errorMessage = "The number of activation functions doesn't match with the number of neurons";
            }

            throw new IllegalArgumentException(errorMessage);
        }
    }

    // Calculates the activation state of the neurons in the layer
    public void computeAllActivationStates() {
        for(Neuron n : neurons) {
            n.computeActivation(previousLayer.getNeurons());
        }
    }

}
