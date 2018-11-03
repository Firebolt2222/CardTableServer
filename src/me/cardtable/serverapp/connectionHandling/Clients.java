package me.cardtable.serverapp.connectionHandling;


import me.cardtable.serverapp.connectionHandling.messageThreads.MessageReceiveThread;
import me.cardtable.serverapp.connectionHandling.messageThreads.MessageSendThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Clients {

    private static List<Socket> clients=new ArrayList<>();
    private static HashMap<Socket, InputStream> clientInputStream=new HashMap<>();
    private static HashMap<Socket, OutputStream> clientOutputStream=new HashMap<>();
    private static HashMap<Socket, MessageReceiveThread> messageReceiveThreads=new HashMap<>();
    private static HashMap<Socket, MessageSendThread> messageSendThreads=new HashMap<>();

    public static boolean addClient(Socket client, MessageReceiveThread mrt, MessageSendThread mst){
        if(!clients.contains(client)){
            clients.add(client);
            try {
                clientInputStream.put(client,client.getInputStream());
                clientOutputStream.put(client,client.getOutputStream());
                messageReceiveThreads.put(client,mrt);
                messageSendThreads.put(client,mst);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }else{
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public static boolean removeClient(Socket client){
        if(clients.contains(client)){
            clients.remove(client);
            try {
                clientInputStream.remove(client);
                clientOutputStream.remove(client);
                messageSendThreads.get(client).interrupt();
                messageReceiveThreads.get(client).interrupt();
                messageSendThreads.remove(client);
                messageReceiveThreads.remove(client);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }else{
            return false;
        }
    }

    public static List<Socket> getClients() {
        return clients;
    }

    public static HashMap<Socket, InputStream> getClientInputStream() {
        return clientInputStream;
    }

    public static HashMap<Socket, OutputStream> getClientOutputStream() {
        return clientOutputStream;
    }

    public static InputStream getClientInputStream(Socket client) {
        return clientInputStream.get(client);
    }

    public static OutputStream getClientOutputStream(Socket client) {
        return clientOutputStream.get(client);
    }

    public static HashMap<Socket, MessageReceiveThread> getMessageReceiveThreads() {
        return messageReceiveThreads;
    }

    public static HashMap<Socket, MessageSendThread> getMessageSendThreads() {
        return messageSendThreads;
    }
    public static MessageReceiveThread getMessageReceiveThread(Socket client) {
        return messageReceiveThreads.get(client);
    }

    public static MessageSendThread getMessageSendThread(Socket client) {
        return messageSendThreads.get(client);
    }
}
