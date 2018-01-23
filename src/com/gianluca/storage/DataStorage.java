package com.gianluca.storage;

import java.io.*;

public class DataStorage {

    public void save(String json) {
        File file = new File("network.json");
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String load() {
        BufferedReader bufferedReader = null;
        File file = new File("network.json");
        StringBuilder sb = new StringBuilder();

        try {
            bufferedReader = new BufferedReader(new FileReader(file));


            String line = bufferedReader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
