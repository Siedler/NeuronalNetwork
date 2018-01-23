package com.gianluca.layers;

import com.gianluca.activation.ActivationFunction;
import com.gianluca.neurons.Neuron;

public class InputLayer extends Layer {

    // Recommended to use this way of creating the neurons
    public void initInputLayer(Neuron[] neurons) {
        this.neurons = neurons;
    }

    // Initializes the inputLayer with the given
    public void initInputLayer(int numOfNeurons, double[] biases, ActivationFunction[] activationFunctions) {
        neurons = new Neuron[numOfNeurons];

        if(numOfNeurons == biases.length && numOfNeurons == activationFunctions.length) {
            for(int i = 0; i < numOfNeurons; i++) {
                Neuron n = new Neuron(new double[0], biases[i], activationFunctions[i]);
                neurons[i] = n;
            }
        } else {
            // Throws an error message if the number of neurons doesn't match with the other given values.
            String errorMessage;
            if(numOfNeurons != biases.length) {
                errorMessage = "The number of biases doesn't match with the number of neurons";
            } else {
                errorMessage = "The number of activation functions doesn't match with the number of neurons";
            }

            throw new IllegalArgumentException(errorMessage);
        }
    }

    public void setActivationOfNthNeuron(int nthNeuron, double activationState) {
        if(nthNeuron <= neurons.length) {
            // Set the activation state of the given neuron
            neurons[nthNeuron].setActivation(activationState);
        } else {
            // Throw exception if the nth neuron does not exist.
            throw new IllegalArgumentException("The nthNeuron doesn't exist. There are only " + neurons.length + " neurons.");
        }
    }

    // This function sets the activation state of all the neurons in the input layer
    public void setActivationOfAllNeurons(double[] activationState) {
        if(neurons.length == activationState.length) {
            // Set the activation state of all neurons
            for(int i = 0; i < neurons.length; i++) {
                neurons[i].setActivation(activationState[i]);
            }
        } else {
            throw new IllegalArgumentException("The number of given activation states is not equal to the number of neurons in the layer");
        }

    }
}
