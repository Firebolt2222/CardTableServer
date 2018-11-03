package me.cardtable.serverapp.connectionHandling.messageThreads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageSendThread extends Thread {

    Socket client;


    public MessageSendThread(Socket client) {
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
                dataOutputStream.writeUTF("Hallo Client");

            }
            dataInputStream.close();
            dataOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        super.run();
    }

}
