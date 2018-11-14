package me.cardtable.serverapp.connectionHandling.messageThreads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class MessageReceiveThread extends Thread {

    Socket client;

    public MessageReceiveThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try{
            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            while(!interrupted()){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Received "+Arrays.toString(dataInputStream.readAllBytes()));

            }
            dataInputStream.close();
            dataOutputStream.close();
        }catch (SocketException se){
            System.err.println("The client is not reachable!");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        super.run();
    }
}
