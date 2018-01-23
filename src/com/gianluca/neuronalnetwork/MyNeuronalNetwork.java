package com.gianluca.neuronalnetwork;

import com.gianluca.cost.SquareCost;
import com.gianluca.learning.Backpropagation;

import java.util.Random;
import java.util.Scanner;

public class MyNeuronalNetwork extends NeuronalNetwork {

    Random r;

    public MyNeuronalNetwork() {
        initNeuronalNetwork(2, 2, new int[]{3,2}, 1, new SquareCost(), new Backpropagation(), 1);

        r = new Random();

        Scanner sc = new Scanner(System.in);

        test();

        sc.next();

        double[][][] learnSet = getTrainingData(10000);

        learnWithTrainingData(learnSet[0], learnSet[1]);

        sc.next();

        test();

        //test();
    }

    private void test() {
        double[] result = calculateOutput(new double[]{1,1});

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }

        result = calculateOutput(new double[]{1,0});
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }

        result = calculateOutput(new double[]{0,1});
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }

        result = calculateOutput(new double[]{0,0});
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

    private int getNumberFromBoolean(boolean b) {
        if(!b) {
            return 0;
        } else {
            return 1;
        }
    }

    private double[][][] getTrainingData(int numberOfData) {
        double[][][] container = new double[2][][];

        container[0] = new double[numberOfData][];
        container[1] = new double[numberOfData][];

        for (int i = 0; i < numberOfData; i++) {
            boolean one = r.nextBoolean();
            boolean two = r.nextBoolean();

            boolean output;

            if (one != two) {
                output = true;
            } else {
                output = false;
            }

            container[0][i] = new double[]{getNumberFromBoolean(one), getNumberFromBoolean(two)};
            container[1][i] = new double[]{getNumberFromBoolean(output)};
        }

        return container;
    }

}
