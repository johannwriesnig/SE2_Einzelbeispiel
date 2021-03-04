package com.example.se2_einzelbeispiel;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client implements Runnable {
    String usersInput;
    String serverCalculation;

    Client(String input) {
        usersInput = input;
    }

    @Override
    public void run() {
        String output="";
        try {
            Socket clientSocket = new Socket("se2-isys.aau.at", 53212);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.writeBytes(usersInput + '\n');

            output = inFromServer.readLine();

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverCalculation =output;
    }

    public String getServerCalculation(){
        return serverCalculation;
    }

}
