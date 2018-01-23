package com.gianluca.neurons;

import com.gianluca.activation.ActivationFunction;
import org.json.JSONArray;
import org.json.JSONObject;

public class Neuron {

    private double[] inputWeights; // All input weights
    private double bias; // The bias that is added before the value is put into the activation function

    private ActivationFunction activationFunction; // The activation function. This will set the activation state of the neuron.

    private double rawActivation; // The activation value of the neuron without the activation function
    private double activation; // The activation value of the neuron (is set after computeActivation)

    private double sensitivity; // The sensitivity of the neuron is used to save the value that is needed for the back propagation algorithm;

    // Initialize the neuron
    public Neuron(double[] inputWeights, double bias, ActivationFunction activationFunction) {
        this.inputWeights = inputWeights;
        this.bias = bias;
        this.activationFunction = activationFunction;

        this.activation = 0;
    }

    // Calculates the activation state of the neuron. First it adds up all the activations * weights of the previous layer and then it puts the value through the activation function.
    public void computeActivation(Neuron[] neurons) {
        if(neurons.length == inputWeights.length) {
            double sumOfNeurons = sumOfNeurons(neurons);

            rawActivation = sumOfNeurons + bias; // Sets the raw activation without the activation function

            // Calculate the activation state of the neuron
            activation = activationFunction.computeActivationFunction(rawActivation);
        } else {
            // Throws exception of the number of the input weights is not the same as the number of the given neurons.
            throw new IllegalArgumentException("The number of given neurons doesn't correspond with the number of input weights");
        }
    }

    public double sumOfNeurons(Neuron[] neurons) {
        double sumOfNeurons = 0;

        // Add up all activations * weight of all connected neurons
        for(int i = 0; i < neurons.length; i++) {
            sumOfNeurons += neurons[i].getActivation() * inputWeights[i];
        }

        return sumOfNeurons;
    }

    /* Getter */

    public double getRawActivation() {
        return rawActivation;
    }

    public double getActivation() {
        return activation;
    }

    public double getBias() {
        return bias;
    }

    public double[] getInputWeights() {
        return inputWeights;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public JSONObject getJson() {
        JSONObject object = new JSONObject();

        object.put("bias", bias);

        JSONArray weights = new JSONArray();

        for(int i = 0; i < inputWeights.length; i++) {
            weights.put(inputWeights[i]);
        }

        object.put("weights", weights);

        return object;
    }

    /* Setter */
    // Use this function for the inputLayer
    public void setActivation(double activation) {
        this.activation = activation;
    }

    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void setInputWeights(double[] inputWeights) {
        this.inputWeights = inputWeights;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }
}
