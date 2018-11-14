package me.cardtable.serverapp.connectionHandling.messageThreads;

import me.cardtable.serverapp.connectionHandling.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Random;

public class MessageSendThread extends Thread {

    Socket client;
    Message msg=null;


    public MessageSendThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try{
            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            Random rd=new Random();
            int a=rd.nextInt();
            while(!interrupted()){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(msg!=null){
                    dataOutputStream.write(msg.message);
                    System.out.println("Sending:");
                    msg.readMessage();
                    msg=null;
                }
            }
            dataInputStream.close();
            dataOutputStream.close();
        }catch (SocketException ex){
            System.err.println("The Client has been closed!");
            this.interrupt();
        } catch (IOException e){
            e.printStackTrace();
        }
        super.run();
    }

    public void setMessage(Message msg) {
        this.msg=msg;
    }
}
