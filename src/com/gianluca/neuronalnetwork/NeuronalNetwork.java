package com.gianluca.neuronalnetwork;

import com.gianluca.activation.ActivationFunction;
import com.gianluca.activation.Sigmoid;
import com.gianluca.cost.CostFunction;
import com.gianluca.layers.HiddenLayer;
import com.gianluca.layers.InputLayer;
import com.gianluca.layers.Layer;
import com.gianluca.layers.OutputLayer;
import com.gianluca.learning.LearningAlgorithm;
import com.gianluca.neurons.Neuron;


import java.util.Random;

public abstract class NeuronalNetwork {

    private InputLayer inputLayer;
    private HiddenLayer[] hiddenLayers;
    private OutputLayer outputLayer;

    private LearningAlgorithm learningAlgorithm;

    // Initializes the neuronal network with given Layers
    void initNeuronalNetwork(InputLayer inputLayer, HiddenLayer[] hiddenLayers, OutputLayer outputLayer, CostFunction costFunction, LearningAlgorithm learningAlgorithm, double learningRate) {
        this.inputLayer = inputLayer;
        this.hiddenLayers = hiddenLayers;
        this.outputLayer = outputLayer;

        this.learningAlgorithm = learningAlgorithm;

        learningAlgorithm.initLearningAlgorithm(inputLayer, hiddenLayers, outputLayer, costFunction, learningRate);
    }

    // Initializes a neuronal network with given number of neurons of each layer with random biases and weights.
    void initNeuronalNetwork(int numOfInputNeurons, int numOfHiddenLayers, int[] numOfHiddenLayerNeurons, int numOfOutputLayerNeurons, CostFunction costFunction, LearningAlgorithm learningAlgorithm, double learningRate) {
        Random r = new Random(); // Initializes a random class (For creating the random weights and biases)

        if(numOfHiddenLayerNeurons.length != numOfHiddenLayers) {
            throw new IllegalArgumentException("The given array of hidden layer neurons (num of hidden layers in the array) doesn't correspond with the number of hidden layer variable");
        }

        double[][][] weightsHiddenLayer = new double[numOfHiddenLayers][][]; // Initializes the weight array for the weights of each hidden layer
        for(int i = 0; i < numOfHiddenLayers; i++) { // For each hidden layer
            weightsHiddenLayer[i] = new double[numOfHiddenLayerNeurons[i]][]; // Initializes the array for the given hidden layer
            for(int y = 0; y < numOfHiddenLayerNeurons[i]; y++) { // For each neuron in the given hidden layer
                if(i == 0) { // If it is the first hidden layer
                    weightsHiddenLayer[i][y] = new double[numOfInputNeurons]; // Create array with number of input layer neurons
                    for(int z = 0; z < numOfInputNeurons; z++) { // For each neuron in the input layer
                        weightsHiddenLayer[i][y][z] = r.nextDouble(); // Set random double
                    }
                } else { // If it isn't the first hidden layer
                    weightsHiddenLayer[i][y] = new double[numOfHiddenLayerNeurons[i-1]]; // Create array with num of neurons in the last hidden layer
                    for(int z = 0; z < numOfHiddenLayerNeurons[i-1]; z++) { // For each neuron in the previous hidden layer
                        weightsHiddenLayer[i][y][z] = r.nextDouble(); // Set random double
                    }
                }
            }
        }

        double[][] weightsOutputLayer = new double[numOfOutputLayerNeurons][]; // Initializes the weight array for the output layer
        for(int i = 0; i < numOfOutputLayerNeurons; i++) { // For each neuron in the output layer
            weightsOutputLayer[i] = new double[numOfHiddenLayerNeurons[numOfHiddenLayers-1]]; // Create the Array of the output layer with the amount of neurons that are in the last hidden layer
            for(int y = 0; y < numOfHiddenLayerNeurons[numOfHiddenLayers-1]; y++) { // For each neuron in the last hidden layer
                weightsOutputLayer[i][y] = r.nextDouble(); // Set random double
            }
        }

        double[][] biasesHiddenLayer = new double[numOfHiddenLayers][]; // Create the biases array of the hidden layers
        for(int i = 0; i < numOfHiddenLayers; i++) { // For each hidden layer
            biasesHiddenLayer[i] = new double[numOfHiddenLayerNeurons[i]]; // Create new double array for each neuron in the given hidden layer
            for(int y = 0; y < numOfHiddenLayerNeurons[i]; y++) { // For each neuron in the ith hidden layer
                biasesHiddenLayer[i][y] = r.nextDouble(); // Set random double
            }
        }

        double[] biasesOutputLayer = new double[numOfOutputLayerNeurons]; // Create array for the biases of the output layer
        for(int i = 0; i < numOfOutputLayerNeurons; i++) { // For each neuron in the output layer
            biasesOutputLayer[i] = r.nextDouble(); // Set random double
        }

        ActivationFunction[][] activationFunctionsHiddenLayer = new ActivationFunction[numOfHiddenLayers][]; // Create array of activation functions for every hidden layer
        for(int i = 0; i < numOfHiddenLayers; i++) { // For each hidden layer
            activationFunctionsHiddenLayer[i] = new ActivationFunction[numOfHiddenLayerNeurons[i]]; // Create array of doubles for each neuron in the given hidden layer
            for(int y = 0; y < numOfHiddenLayerNeurons[i]; y++) { // For each neuron in the hidden layer
                activationFunctionsHiddenLayer[i][y] = new Sigmoid(); // Add Sigmoid function as activation function
            }
        }

        ActivationFunction[] activationFunctionsOutputLayer = new ActivationFunction[numOfOutputLayerNeurons]; // Create activation function array for each neuron in the output layer
        for(int i = 0; i < numOfOutputLayerNeurons; i++) { // For each neuron in the output layer
            activationFunctionsOutputLayer[i] = new Sigmoid(); // Add Sigmoid function as activation function
        }

        initNeuronalNetwork(numOfInputNeurons, numOfHiddenLayers, numOfHiddenLayerNeurons, numOfOutputLayerNeurons, weightsHiddenLayer, weightsOutputLayer, biasesHiddenLayer, biasesOutputLayer, activationFunctionsHiddenLayer, activationFunctionsOutputLayer, costFunction, learningAlgorithm, learningRate); // Calls init function
    }

    // Initializes a neuronal network with given number of neurons and weights, biases and activation functions
    void initNeuronalNetwork(int numOfInputNeurons, int numOfHiddenLayers, int[] numOfHiddenLayerNeurons, int numOfOutputLayerNeurons, double[][][] weightsHiddenLayer, double[][] weightsOutputLayer, double[][] biasesHiddenLayer, double[] biasesOutputLayer, ActivationFunction[][] activationFunctionsHiddenLayer, ActivationFunction[] activationFunctionsOutputLayer, CostFunction costFunction, LearningAlgorithm learningAlgorithm, double learningRate) {
        Neuron[] tempNeurons; // This array is used to store temporally the neuron arrays

        InputLayer inputLayer = new InputLayer(); // Create a new input layer

        tempNeurons = new Neuron[numOfInputNeurons]; // Array is set up with the number of input neurons
        for(int i = 0; i < numOfInputNeurons; i++) {
            Neuron n = new Neuron(new double[0], 0, null); // Create neuron
            tempNeurons[i] = n; // Add neuron to array
        }
        inputLayer.initInputLayer(tempNeurons); // Init input layer

        HiddenLayer[] hiddenLayers = new HiddenLayer[numOfHiddenLayers]; // Create a new hidden layers array
        for(int i = 0; i < numOfHiddenLayers; i++) {
            HiddenLayer hiddenLayer = new HiddenLayer(); // Create a new hidden layer

            tempNeurons = new Neuron[numOfHiddenLayerNeurons[i]]; // Create a new temporary array for the neurons of the hidden layer
            for(int y = 0; y < numOfHiddenLayerNeurons[i]; y++) {
                Neuron n = new Neuron(weightsHiddenLayer[i][y], biasesHiddenLayer[i][y], activationFunctionsHiddenLayer[i][y]); // Creates the neurons of the hidden layer (Of the ith)
                tempNeurons[y] = n; // Add neuron to the array
            }

            Layer previousLayer;
            // Check if the hidden layer is the first one or not (to create the previous layer)
            if(i == 0) previousLayer = inputLayer;
            else previousLayer = hiddenLayers[i-1];

            hiddenLayer.initHiddenLayer(tempNeurons, previousLayer); // Init the hidden layer

            hiddenLayers[i] = hiddenLayer;
        }

        OutputLayer outputLayer = new OutputLayer(); // Creates the output layer

        tempNeurons = new Neuron[numOfOutputLayerNeurons]; // Creates the temporary array
        for(int i = 0; i < numOfOutputLayerNeurons; i++) {
            Neuron n = new Neuron(weightsOutputLayer[i], biasesOutputLayer[i], activationFunctionsOutputLayer[i]);// Creates the neuron of the output layer
            tempNeurons[i] = n;
        }

        outputLayer.initOutputLayer(tempNeurons, hiddenLayers[hiddenLayers.length-1]); // Initializes the output layer with the last hidden layer as previous layer

        initNeuronalNetwork(inputLayer, hiddenLayers, outputLayer, costFunction, learningAlgorithm, learningRate);
    }

    // This function calculates the outputs for a given input value array
    double[] calculateOutput(double[] inputValues) {
        // Set all input values
        for (int i = 0; i < inputValues.length; i++) {
            inputLayer.getNeurons()[i].setActivation(inputValues[i]);
        }

        // Calculate the result of each hidden layer
        for (HiddenLayer hiddenLayer : hiddenLayers) {
            hiddenLayer.computeAllActivationStates();
        }

        // Calculate the result of the output layer
        outputLayer.computeAllActivationStates();

        // Return the output
        return outputLayer.getOutputs();
    }

    private void learnWithTestData(double[] trainingData, double[] expectedResult) {
        double[] result = calculateOutput(trainingData);

        learningAlgorithm.learn(result, expectedResult);
    }

    void learnWithTrainingDataInterwall(double[][] trainingData, double[][] expectedResult) {
        for(int i = 0; i < trainingData.length; i++) {
            System.out.println("Learning set: " + i);
            learnWithTestData(trainingData[i], expectedResult[i]);
        }
        learningAlgorithm.updateWeightsAndBias();
    }

    void learnWithTrainingData(double[][] trainingData, double[][] expectedResult) {
        for(int i = 0; i < trainingData.length; i++) {
            System.out.println("Learning set: " + i);
            learnWithTestData(trainingData[i], expectedResult[i]);
            learningAlgorithm.updateWeightsAndBias();
        }
    }
}