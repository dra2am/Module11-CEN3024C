package com.example.module11;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //displays content
        TextArea ta = new TextArea();
        //create scene, place in stage
        Scene scene = new Scene(new ScrollPane(), 320, 240);
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();

        new Thread(() ->
        {
            //create a server socket
            try {
                ServerSocket serverSocket = new ServerSocket(8000);

                Platform.runLater(()-> ta.appendText("Server started at port 8000 \n"));

                System.out.println("Server is started at port 8000");

                System.out.println("Awaiting Connection...");

                //listen for a connection request
                Socket socket = serverSocket.accept();

                //create input and output streams
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                while(true){
                    System.out.println("Awaiting input...");

                    //receive number from client
                    double num = inputStream.readDouble();

                    //is prime?
                    NumberChecker numberChecker = new NumberChecker(num);

                    boolean prime = numberChecker.isPrimeNumber();

                    //send back response
                    outputStream.writeBoolean(prime);

                    //display text
                    Platform.runLater(()->
                    {
                        System.out.println("Number receieved is: "+ num);
                        System.out.println("Is this a prime number? "+prime);

                        ta.appendText("Number receieved is: "+ num);
                        ta.appendText("Is this a prime number? "+prime);
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static void main(String[] args) {
        launch();
    }
}