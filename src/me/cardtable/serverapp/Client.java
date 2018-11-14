package me.cardtable.serverapp;


import me.cardtable.serverapp.connectionHandling.Message;
import me.cardtable.serverapp.connectionHandling.messageThreads.MessageReceiveThread;
import me.cardtable.serverapp.connectionHandling.messageThreads.MessageSendThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;
    private MessageSendThread mst;
    private MessageReceiveThread mrt;
    private DataOutputStream out;
    private DataInputStream in;

    public Client(Socket socket, MessageSendThread mst, MessageReceiveThread mrt) {
        this.socket = socket;
        this.mst = mst;
        this.mrt = mrt;
        try {
            out=new DataOutputStream(socket.getOutputStream());
            in=new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataOutputStream getDataOutputStream(){
        return out;
    }

    public DataInputStream getDataInputStream(){
        return in;
    }

    public void sendMessage(Message msg){
        mst.setMessage(msg);
    }

    public void disconnect() throws IOException {
        mst.interrupt();
        mrt.interrupt();
        socket.close();
    }

}