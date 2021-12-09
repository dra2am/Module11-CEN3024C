package com.example.module11;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Application {

    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //label and text field
        BorderPane textField = new BorderPane();

        textField.setPadding(new Insets(5,5,5,5));
        textField.setLeft(new Label("Please enter a number: "));

        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_CENTER);
        textField.setCenter(tf);

        BorderPane mainPane = new BorderPane();

        //Text area for content
        TextArea ta = new TextArea();

        mainPane.setCenter(ta);
        mainPane.setTop(textField);

        //create scene, place in stage
        Scene scene = new Scene(mainPane, 320, 240);
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        tf.setOnAction( e ->
        {
            try {
                //get num from input, parse to double, remove spaces
                double num = Double.parseDouble(tf.getText().trim());

                //send to server
                toServer.writeDouble(num);
                toServer.flush();

                //get bool from server
                boolean ans = fromServer.readBoolean();

                //display text
                ta.appendText("Number is "+ num + "\n");

                if (ans)
                {
                    ta.appendText(num +" is prime! \n");
                    return;
                }

                ta.appendText(num + "is not prime. \n");

            }
            catch (IOException ex){
                System.err.println(ex);
            }
        });

        try {
            //connect to server
            Socket socket = new Socket("localhost", 8000);

            //input stream
            fromServer = new DataInputStream(socket.getInputStream());

            //output stream
            toServer = new DataOutputStream(socket.getOutputStream());
        }

        catch (IOException ex){
            System.err.println(ex);
        }

    }
}
