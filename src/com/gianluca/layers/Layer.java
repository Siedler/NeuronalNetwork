package com.gianluca.layers;

import com.gianluca.neurons.Neuron;
import org.json.JSONArray;

public abstract class Layer {

    Neuron[] neurons;

    // Return the neurons that are in the layer
    public Neuron[] getNeurons() {
        return neurons;
    }

    /* Setter */
    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
    }

    /* Getter */
    public int getNumberOfNeuronsInLayer() {
        return neurons.length;
    }

    public JSONArray getJson() {
        JSONArray array = new JSONArray();

        for(int i = 0; i < neurons.length; i++) {
            array.put(neurons[i].getJson());
        }

        return array;
    }
}
